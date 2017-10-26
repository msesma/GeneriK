package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import android.os.SystemClock
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.model.Code
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.platform.CallbackFun
import com.paradigmadigital.repository.NetworkResultCode.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import okhttp3.Credentials
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Repository
@Inject
constructor(
        private val networkResultLiveData: NetworkResultLiveData,
        private val userDao: UserDao,
        private val securePreferences: SecurePreferences,
        private val loginMapper: LoginMapper,
        private val userMapper: UserMapper,
        retrofit: Retrofit
) {

    companion object {
        private val TIMEOUT = TimeUnit.MINUTES.toMillis(5)
        private val TRUE = 1
        private val HTTP_FORBIDDEN = HttpURLConnection.HTTP_FORBIDDEN.toString()
        private val HTTP_NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND.toString()
    }

    val loginRegisterService = retrofit.create(LoginRegisterService::class.java)

    fun getErrors(): LiveData<NetworkResult> = networkResultLiveData

    fun isLoggedIn(): Boolean {
        val user: User? = userDao.getUser()
        return !user?.token.isNullOrEmpty()
    }

    fun logout() {
        executeCall {
            userDao.logout()
            loginRegisterService.logout(userDao.getUser().email)
        }
    }

    fun getUser(): LiveData<User> {
        return userDao.get()
    }

    fun register(user: User, pass: String, id: Int) {
        executeCall(id) {
            val login = userMapper.map(user)
            val response = loginRegisterService.register(login).execute()
            if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            userDao.insert(user.copy(uid = (response.body() as Login).uid))
            securePreferences.password = pass
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
        }
    }

    fun setUser(email: String) {
        userDao.insert(User(email = email))
    }

    fun requestCode(id: Int) {
        executeCall(id) {
            val response = loginRegisterService.requestCode(userDao.getUser().email).execute()
            if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            val code = response.body() as Code
            userDao.setCode(code.code, Date(), code.email)
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
        }
    }

    fun updatePass(pass: String) {
        securePreferences.password = pass
    }

    fun setPass(code: String, id: Int) {
        val user = userDao.getUser()
        if (user.code != code || Date().time - user.codeDate.time > TIMEOUT) {
            networkResultLiveData.setNetworkResult(NetworkResult(FAIL, id))
            return
        }
        executeCall(id) {
            val response = loginRegisterService.setPass(Credentials.basic(user.email, securePreferences.password)).execute()
            if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
        }
    }

    fun login(email: String, pass: String, id: Int) {
        executeCall(id) {
            val response = loginRegisterService.login(Credentials.basic(email, pass)).execute()
            if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            val login = response.body() as Login
            if (login.token.isEmpty()) throw RuntimeException(HttpURLConnection.HTTP_FORBIDDEN.toString())
            userDao.insert(loginMapper.map(login))
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
        }
    }

    private fun executeCall(id: Int = 0, call: () -> Unit) = launch(CommonPool) { suspendExecuteCall(id, call) }

    suspend private fun suspendExecuteCall(id: Int = 0, call: () -> Unit) {
        try {
            SystemClock.sleep(1000) //TODO: remove, only to emulate network delay
            call()
        } catch (e: Throwable) {
            manageExceptions(e, id) { networkResultLiveData.setNetworkResult(it) }
        }
    }

    private fun manageExceptions(e: Throwable, id: Int, callback: CallbackFun<NetworkResult>) {
        when {
            e is UnknownHostException -> callback(NetworkResult(DISCONNECTED, id))
            e.message == HTTP_NOT_FOUND -> callback(NetworkResult(BAD_URL, id))
            e.message == HTTP_FORBIDDEN -> callback(NetworkResult(FORBIDDEN, id))
            else -> callback(NetworkResult(UNKNOWN, id))
        }
    }
}
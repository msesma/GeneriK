package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import android.os.SystemClock
import com.paradigmadigital.api.model.Code
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.platform.CallbackFun
import com.paradigmadigital.repository.NetworkResultCode.*
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Repository
@Inject
constructor(
        private val preferences: Preferences,
        private val networkResultLiveData: NetworkResultLiveData,
        private val userDao: UserDao,
        private val securePreferences: SecurePreferences,
        private val retrofit: Retrofit,
        private val executor: Executor
) {

    companion object {
        val TIMEOUT = TimeUnit.MINUTES.toMillis(5)
    }

    val loginRegisterService = retrofit.create(LoginRegisterService::class.java)

    fun getErrors(): LiveData<NetworkResult> = networkResultLiveData

    fun isLoggedIn() = preferences.isloggedIn

    fun setLoggedIn(logged: Boolean) {
        preferences.isloggedIn = logged
        if (!isLoggedIn()) executeCall {
            loginRegisterService.logout(userDao.getUser().email)
        }
    }

    fun getUser(): LiveData<User> {
        return userDao.get()
    }

    fun setUser(user: User, pass: String) {
        executeCall {
            //            val body = loginRegisterService.sendUserData(user).execute().body()
//            if (body == null) throw UnknownHostException()
//            userDao.insert(user.copy(oneRow = "1", uid = body.uid))
            userDao.insert(user.copy(oneRow = "1", uid = "1234"))
            securePreferences.password = pass
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, 0))
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
           // if (loginRegisterService.sendUserPass(email, pass).execute().body() == null) throw UnknownHostException()
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
        }
    }

    fun login(email: String, pass: String) {
        executeCall {
            //                val body = loginRegisterService.sendLogin(email, pass).execute().body()
//                if ( body == null) throw UnknownHostException()
//                userDao.insert(userMapper.map(body))
            if (email == userDao.getUser().email && pass == securePreferences.password) setLoggedIn(true)
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, 0))
        }
    }

    private fun executeCall(id: Int = 0, call: () -> Unit) {
        executor.execute {
            try {
                SystemClock.sleep(1000) //TODO: remove, only to emulate network delay
                call()
            } catch (e: Throwable) {
                manageExceptions(e, id) { networkResultLiveData.setNetworkResult(it) }
            }
        }
    }

    private fun manageExceptions(e: Throwable, id: Int, callback: CallbackFun<NetworkResult>) {
        when {
            e is UnknownHostException -> callback(NetworkResult(DISCONNECTED, id))
            e.message == "404" -> callback(NetworkResult(BAD_URL, id))
            else -> callback(NetworkResult(UNKNOWN, id))
        }
    }
}
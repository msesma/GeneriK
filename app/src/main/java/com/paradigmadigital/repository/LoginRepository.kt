package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.model.Code
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.platform.CallbackFun
import com.paradigmadigital.repository.NetworkResultCode.*
import com.paradigmadigital.repository.preferences.Preferences
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import okhttp3.Credentials
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LoginRepository
@Inject
constructor(
        val networkResultLiveData: NetworkResultLiveData,
        val userDao: UserDao,
        val preferences: Preferences,
        val accountManager: OauthAccountManager,
        val loginMapper: LoginMapper,
        retrofit: Retrofit
) {

    companion object {
        val TIMEOUT = TimeUnit.MINUTES.toMillis(5)
        private val HTTP_FORBIDDEN = HttpURLConnection.HTTP_FORBIDDEN.toString()
        private val HTTP_NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND.toString()
    }

    val loginRegisterService = retrofit.create(LoginRegisterService::class.java)

    fun getErrors(): LiveData<NetworkResult> = networkResultLiveData

    fun isLoggedIn(): Boolean {
        return accountManager.isLoggedIn()
    }

    fun getEmail(): String = accountManager.getEmail()

    fun getUser(): LiveData<User> {
        return userDao.get()
    }

    fun setUser(login: Login) {
        userDao.insert(loginMapper.map(login))
    }

    fun setCode(code: String, date: Date, email: String) {
        preferences.setCode(code, date, email)
    }

    fun getCode(email: String): String {
        if (preferences.codeEmail != email || Date().time - preferences.codeTime.time > TIMEOUT) {
            return ""
        }
        return preferences.code
    }

    fun timeoutRequireLoginCheck() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addAccount(user: Login) {
        accountManager.addAccount(user)
    }

    fun login(email: String, pass: String): DataResult {
        val response = loginRegisterService.login(Credentials.basic(email, pass)).execute()
        if (!response.isSuccessful) return DataResult.Failure(response.raw().code().toString())
        return DataResult.Success(response.body() as Login)
    }

    fun localLogout() {
        accountManager.logout()
    }

    fun remoteLogout() {
        loginRegisterService.logout(getEmail())
    }

    fun register(user: Login): DataResult {
        val response = loginRegisterService.register(user).execute()
        if (!response.isSuccessful) return DataResult.Failure(response.raw().code().toString())
        return DataResult.Success(response.body() as Login)
    }

    fun requestCode(): DataResult {
        val response = loginRegisterService.requestCode(getEmail()).execute()
        if (!response.isSuccessful) return DataResult.Failure(response.raw().code().toString())
        return DataResult.Success(response.body() as Code)
    }

    fun setPass(email: String, pass: String, code: String): DataResult {
        val response = loginRegisterService
                .setPass(Credentials.basic(email, pass), code)
                .execute()
        if (!response.isSuccessful) return DataResult.Failure(response.raw().code().toString())
        return DataResult.Success(Unit)
    }

    fun executeInteractor(id: Int = 0, call: () -> Unit) = launch(CommonPool) { suspendExecute(id, call) }

    suspend private fun suspendExecute(id: Int = 0, call: () -> Unit) {
        try {
            call()
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
        } catch (e: Throwable) {
            println(e.message + " " + e.hashCode())
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
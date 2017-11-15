package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.account.OauthAccountManager
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
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Repository
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

    val loginRegisterService: LoginRegisterService = retrofit.create(LoginRegisterService::class.java)

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
        if (preferences.codeEmail != email || preferences.codeTime.time - Date().time > TIMEOUT ) {
            return ""
        }
        return preferences.code
    }

    fun timeoutRequireLoginCheck() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.platform.CallbackFun
import com.paradigmadigital.repository.NetworkResultCode.*
import com.paradigmadigital.repository.preferences.Preferences
import com.paradigmadigital.repository.securepreferences.SecurePreferences
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
        val securePreferences: SecurePreferences,
        val preferences: Preferences,
        val loginMapper: LoginMapper,
        val userMapper: UserMapper,
        val accountManager: OauthAccountManager,
        retrofit: Retrofit
) {

    companion object {
        val TIMEOUT = TimeUnit.MINUTES.toMillis(5)
        private val HTTP_FORBIDDEN = HttpURLConnection.HTTP_FORBIDDEN.toString()
        private val HTTP_NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND.toString()
    }

    val loginRegisterService: LoginRegisterService = retrofit.create(LoginRegisterService::class.java)

//    val isFingerPrintAuthdataAvailable
//        get() = !securePreferences.password.isEmpty() && !getEmail().isEmpty()
//
//    val requireLogin
//        get() = preferences.requireLogin && preferences.timeout

    fun getErrors(): LiveData<NetworkResult> = networkResultLiveData

    fun isLoggedIn(): Boolean {
        return accountManager.isLoggedIn()
    }

    fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setUser(email: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setCode(code: String, date: Date, email: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCode(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCodeDate(): Date {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getPass() = securePreferences.password

//    fun getUser(): LiveData<User> {
//        return userDao.get()
//    }

//    fun setUser(email: String) {
//        userDao.insert(User(email = email))
//    }

    fun updatePass(pass: String) {
        securePreferences.password = pass
    }

//    fun timeoutRequireLoginCheck() {
//        if (requireLogin) userDao.logout()
//    }

    fun executeInteractor(id: Int = 0, call: () -> Unit) = launch(CommonPool) { suspendExecute(id, call) }

    suspend private fun suspendExecute(id: Int = 0, call: () -> Unit) {
        try {
            call()
            networkResultLiveData.setNetworkResult(NetworkResult(SUCCESS, id))
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
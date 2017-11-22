package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.model.Code
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.mappers.UserMapper
import com.paradigmadigital.repository.preferences.Preferences
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import okhttp3.Credentials
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.HttpURLConnection
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
        val userMapper: UserMapper,
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

    fun getUserLiveData() = userDao.get()

    fun getUser() = userDao.getUser()

    fun setUser(login: Login) {
        userDao.insert(userMapper.map(login))
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

    fun login(email: String, pass: String): ApiResult {
        val response = loginRegisterService.login(Credentials.basic(email, pass)).execute()
        if (!response.isSuccessful) return HttpException(response).toApiResult()
        return ApiResult.Success(response.body() as Login)
    }

    fun localLogout() {
        accountManager.logout()
    }

    fun remoteLogout() {
        loginRegisterService.logout(getEmail())
    }

    fun register(user: Login): ApiResult {
        val response = loginRegisterService.register(user).execute()
        if (!response.isSuccessful) return HttpException(response).toApiResult()
        return ApiResult.Success(response.body() as Login)
    }

    fun requestCode(): ApiResult {
        val response = loginRegisterService.requestCode(getEmail()).execute()
        if (!response.isSuccessful) return HttpException(response).toApiResult()
        return ApiResult.Success(response.body() as Code)
    }

    fun setPass(email: String, pass: String, code: String): ApiResult {
        val response = loginRegisterService
                .setPass(Credentials.basic(email, pass), code)
                .execute()

        if (!response.isSuccessful) return HttpException(response).toApiResult()
        return ApiResult.Success(Unit)
    }

    fun executeInteractor(id: Int = 0, call: () -> Unit) = launch(CommonPool) { suspendExecute(id, call) }

    suspend private fun suspendExecute(id: Int = 0, call: () -> Unit) {
        try {
            call()
        } catch (e: Throwable) {
            sendResult(e.toApiResult().data, id)
        }
    }

    fun sendResult(code: NetworkResultCode, id: Int) {
        println("Code: $code, id: $id")
        networkResultLiveData.setNetworkResult(NetworkResult(code, id))
    }
}
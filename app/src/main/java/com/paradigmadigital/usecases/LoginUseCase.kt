package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode
import com.paradigmadigital.repository.Repository
import okhttp3.Credentials
import java.net.HttpURLConnection
import javax.inject.Inject

class LoginUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(email: String, pass: String, id: Int) {
        with(repository) {
            executeInteractor {
                val response = loginRegisterService.login(Credentials.basic(email, pass)).execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
                val login = response.body() as Login
                if (login.token.isEmpty()) throw RuntimeException(HttpURLConnection.HTTP_FORBIDDEN.toString())
                userDao.insert(loginMapper.map(login))
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.SUCCESS, id))
            }
        }
    }
}
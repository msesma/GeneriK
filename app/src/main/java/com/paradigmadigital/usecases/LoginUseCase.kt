package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.DataResult
import com.paradigmadigital.repository.LoginRepository
import java.net.HttpURLConnection
import javax.inject.Inject

class LoginUseCase
@Inject constructor(
        private val repository: LoginRepository
) {

    fun execute(email: String, pass: String, id: Int) {
        with(repository) {
            executeInteractor(id) {
                val result = login(email, pass)
                when (result) {
                    is DataResult.Success<*> -> handleSuccess(result.data as Login)
                    is DataResult.Failure -> throw  RuntimeException(result.data)
                }
            }
        }
    }

    private fun LoginRepository.handleSuccess(user: Login) {
        if (user.token.isEmpty()) throw RuntimeException(HttpURLConnection.HTTP_FORBIDDEN.toString())
        addAccount(user)
        setUser(user)
    }
}
package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.repository.NetworkResultCode
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
                    is ApiResult.Success<*> -> handleSuccess(result.data as Login, id)
                    is ApiResult.Failure -> sendResult(result.data, id)
                }
            }
        }
    }

    private fun LoginRepository.handleSuccess(user: Login, id: Int) {
        if (user.token.isEmpty()) {
            sendResult(NetworkResultCode.FORBIDDEN, id)
            return
        }
        addAccount(user)
        setUser(user)
        sendResult(NetworkResultCode.SUCCESS, id)
    }
}
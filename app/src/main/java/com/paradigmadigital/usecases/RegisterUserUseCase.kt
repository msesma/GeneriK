package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.LoginRepository
import javax.inject.Inject


class RegisterUserUseCase
@Inject constructor(
        private val repository: LoginRepository
) {

    fun execute(user: Login, id: Int) {
        with(repository) {
            executeInteractor(id) {
                val result = register(user)
                when (result) {
                    is ApiResult.Success<*> -> setUser(result.data as Login)
                    is ApiResult.Failure -> throw  RuntimeException(result.data)
                }
            }
        }
    }
}
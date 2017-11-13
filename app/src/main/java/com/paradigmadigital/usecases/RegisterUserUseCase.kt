package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.Repository
import javax.inject.Inject


class RegisterUserUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(user: Login, id: Int) {
        with(repository) {
            executeInteractor(id) {
                val response = loginRegisterService.register(user).execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            }
        }
    }
}
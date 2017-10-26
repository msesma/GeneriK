package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class LoginUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(email: String, pass: String, id: Int) {
        repository.login(email, pass, id)
    }
}
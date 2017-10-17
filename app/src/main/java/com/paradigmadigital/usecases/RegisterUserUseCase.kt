package com.paradigmadigital.usecases

import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.repository.Repository
import javax.inject.Inject


class RegisterUserUseCase
@Inject constructor(
        private val repository: Repository
){

    fun execute(user: User, pass: String){
        repository.setUser(user, pass)
    }
}
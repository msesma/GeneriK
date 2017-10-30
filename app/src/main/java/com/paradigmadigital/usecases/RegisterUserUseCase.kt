package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.repository.Repository
import javax.inject.Inject


class RegisterUserUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(user: User, pass: String, id: Int) {
        with(repository) {
            executeInteractor(id) {
                val login = userMapper.map(user)
                val response = loginRegisterService.register(login).execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
                userDao.insert(user.copy(uid = (response.body() as Login).uid))
                securePreferences.password = pass
            }
        }
    }
}
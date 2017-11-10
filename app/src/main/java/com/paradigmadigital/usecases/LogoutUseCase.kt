package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class LogoutUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute() {
        with(repository) {
            executeInteractor {
                loginRegisterService.logout(userDao.getUser().email)
                accountManager.logout()
//                userDao.insert(User())
            }
        }
    }
}
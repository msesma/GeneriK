package com.paradigmadigital.usecases

import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.repository.NetworkResultCode
import javax.inject.Inject

class LogoutUseCase
@Inject constructor(
        private val repository: LoginRepository
) {

    fun execute() {
        with(repository) {
            executeInteractor {
                remoteLogout()
                localLogout()
                sendResult(NetworkResultCode.SUCCESS, 0)
            }
        }
    }
}
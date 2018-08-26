package eu.sesma.generik.usecases

import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResultCode
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
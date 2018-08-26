package eu.sesma.generik.usecases

import eu.sesma.generik.api.model.Login
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResultCode
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
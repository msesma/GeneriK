package eu.sesma.generik.usecases

import eu.sesma.generik.api.model.Login
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResultCode
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
                    is ApiResult.Success<*> -> {
                        setUser(result.data as Login)
                        sendResult(NetworkResultCode.SUCCESS, id)
                    }
                    is ApiResult.Failure -> sendResult(result.data, id)
                }
            }
        }
    }
}
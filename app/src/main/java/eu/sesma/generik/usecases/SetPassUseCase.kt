package eu.sesma.generik.usecases

import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResultCode
import javax.inject.Inject

class SetPassUseCase
@Inject constructor(
        private val repository: LoginRepository
) {

    fun execute(code: String, email: String, pass: String, id: Int) {
        with(repository) {
            val storedCode = getCode(email)
            if (storedCode.isEmpty() || storedCode != code) {
                sendResult(NetworkResultCode.FAIL, id)
                return
            }

            executeInteractor(id) {
                val result = setPass(email, pass, code)
                when (result) {
                    is ApiResult.Success<*> -> sendResult(NetworkResultCode.SUCCESS, id)
                    is ApiResult.Failure -> sendResult(result.data, id)
                }
            }
        }
    }
}
package eu.sesma.generik.usecases

import eu.sesma.generik.api.model.Code
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResultCode
import java.util.*
import javax.inject.Inject

class RequestCodeUseCase
@Inject constructor(
        private val repository: LoginRepository
) {

    fun execute(id: Int) {
        with(repository) {
            executeInteractor(id) {
                val result = requestCode()
                when (result) {
                    is ApiResult.Success<*> -> {
                        setCode((result.data as Code).code, Date(), result.data.email)
                        sendResult(NetworkResultCode.SUCCESS, id)
                    }
                    is ApiResult.Failure -> sendResult(result.data, id)
                }
            }
        }
    }
}

package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Code
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.LoginRepository
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
                    is ApiResult.Success<*> -> setCode(
                            (result.data as Code).code, Date(), result.data.email)
                    is ApiResult.Failure -> throw  RuntimeException(result.data)
                }
            }
        }
    }
}

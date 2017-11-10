package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Code
import com.paradigmadigital.repository.Repository
import java.util.*
import javax.inject.Inject

class RequestCodeUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(id: Int) {
        with(repository) {
            executeInteractor(id) {
                val response = loginRegisterService.requestCode(getEmail()).execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
                val code = response.body() as Code
                setCode(code.code, Date(), code.email)
            }
        }
    }
}

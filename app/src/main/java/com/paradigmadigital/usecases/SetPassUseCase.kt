package com.paradigmadigital.usecases

import com.paradigmadigital.repository.DataResult
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode
import javax.inject.Inject

class SetPassUseCase
@Inject constructor(
        private val repository: LoginRepository
) {

    fun execute(code: String, email: String, pass: String, id: Int) {
        with(repository) {
            if (getCode(email) == "") {
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.FAIL, id))
                return
            }

            executeInteractor(id) {
                val result = setPass(email, pass, code)
                when (result) {
                    is DataResult.Success<*> -> Unit
                    is DataResult.Failure -> throw  RuntimeException(result.data)
                }
            }
        }
    }
}
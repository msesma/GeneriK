package com.paradigmadigital.usecases

import com.paradigmadigital.repository.ApiResult
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
            val storedCode = getCode(email)
            if (storedCode.isEmpty() || storedCode != code) {
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.FAIL, id))
                return
            }

            executeInteractor(id) {
                val result = setPass(email, pass, code)
                when (result) {
                    is ApiResult.Success<*> -> Unit
                    is ApiResult.Failure -> throw  RuntimeException(result.data)
                }
            }
        }
    }
}
package com.paradigmadigital.usecases

import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode
import com.paradigmadigital.repository.Repository
import okhttp3.Credentials
import javax.inject.Inject

class SetPassUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(code: String, email: String, pass: String, id: Int) {
        with(repository) {
            if (getCode(email) == "") {
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.FAIL, id))
                return
            }

            executeInteractor(id) {
                val response = loginRegisterService
                        .setPass(Credentials.basic(email, pass), code)
                        .execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            }
        }
    }
}
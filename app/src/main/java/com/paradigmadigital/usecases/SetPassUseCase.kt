package com.paradigmadigital.usecases

import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.Repository.Companion.TIMEOUT
import okhttp3.Credentials
import java.util.*
import javax.inject.Inject

class SetPassUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(code: String, id: Int) {
        with(repository) {
            if (getCode() != code || Date().time - getCodeDate().time > TIMEOUT) {
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.FAIL, id))
                return
            }

            executeInteractor(id) {
                val response = loginRegisterService
                        .setPass(Credentials.basic(getEmail(), securePreferences.password))
                        .execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
            }
        }
    }
}
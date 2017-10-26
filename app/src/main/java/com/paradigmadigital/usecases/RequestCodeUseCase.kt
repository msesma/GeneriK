package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Code
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode
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
                val response = loginRegisterService.requestCode(userDao.getUser().email).execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
                val code = response.body() as Code
                userDao.setCode(code.code, Date(), code.email)
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.SUCCESS, id))
            }
        }
    }
}

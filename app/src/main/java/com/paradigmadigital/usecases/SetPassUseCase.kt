package com.paradigmadigital.usecases

import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode
import com.paradigmadigital.repository.Repository
import okhttp3.Credentials
import java.util.*
import javax.inject.Inject

class SetPassUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(code: String, id: Int) {
        with(repository) {
            val user = userDao.getUser()
            if (user.code != code || Date().time - user.codeDate.time > Repository.TIMEOUT) {
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.FAIL, id))
                return
            }

            executeInteractor {
                val response = loginRegisterService
                        .setPass(Credentials.basic(user.email, securePreferences.password))
                        .execute()
                if (!response.isSuccessful) throw RuntimeException(response.raw().code().toString())
                networkResultLiveData.setNetworkResult(NetworkResult(NetworkResultCode.SUCCESS, id))
            }
        }
    }
}
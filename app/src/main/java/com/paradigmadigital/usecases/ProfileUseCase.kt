package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.DataRepository
import com.paradigmadigital.repository.LoginRepository
import io.reactivex.Single
import javax.inject.Inject


class ProfileUseCase
@Inject constructor(
        private val authRepository: DataRepository,
        private val repository: LoginRepository
) {

    fun execute(user: Login): Single<ApiResult> {
        return authRepository.update(user)
                .doOnSuccess {
                    if (it is ApiResult.Success<*>) repository.setUser(it.data as Login)
                }
    }
}

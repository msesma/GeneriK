package eu.sesma.generik.usecases

import eu.sesma.generik.api.model.Login
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.DataRepository
import eu.sesma.generik.repository.LoginRepository
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

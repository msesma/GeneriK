package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.repository.NetworkResultCode
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.TimeUnit


class RegisterUserUseCaseShould : BaseRepositoryUseCase() {

    private lateinit var usecase: RegisterUserUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = RegisterUserUseCase(repository)
    }

    @Test
    fun apiRegisterWhenExecuted() {

        usecase.execute(User(), "1234", 5)

        verify(userMapper).map(any())
        verify(loginRegisterService).register(any())
    }

    @Test
    fun callbackErrorWhenErrorInRegister() {
        val response = getResponse(404, true)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).register(any())

        usecase.execute(User(), "1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(networkResultLiveData).setNetworkResult(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.BAD_URL)
    }

    @Test
    fun insertUserOnDbOnSucessfulRegister() {
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).register(any())

        usecase.execute(User(), "1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(userDao).insert(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.SUCCESS)
        verify(securePreferences).password = "1234"
    }


    private fun getResponse(code: Int, error: Boolean): Response<Login> {
        if (error) {
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "{}")
            return Response.error<Login>(code, responseBody)
        }
        val login = Login()
        return Response.success(login)
    }
}
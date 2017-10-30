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
import java.util.*
import java.util.concurrent.TimeUnit


class SetPassUseCaseShould : BaseRepositoryUseCase() {

    private lateinit var usecase: SetPassUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = SetPassUseCase(repository)
    }

    @Test
    fun apiRequestCodeWhenExecutedWithIncorrectCode() {
        val user = User(code = "4321", codeDate = Date())
        doReturn(user).whenever(userDao).getUser()

        usecase.execute("1234", 5)

        verify(networkResultLiveData).setNetworkResult(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.FAIL)
    }

    @Test
    fun apiRequestCodeWhenExecutedWithCodeDateTooOld() {
        val user = User(code = "1234", codeDate = Date(0))
        doReturn(user).whenever(userDao).getUser()

        usecase.execute("1234", 5)

        verify(networkResultLiveData).setNetworkResult(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.FAIL)
    }

    @Test
    fun apiRequestCodeWhenExecutedWithCodeANdDAteOK() {
        val user = User(code = "1234", codeDate = Date(), email = "bob@acme.com")
        doReturn(user).whenever(userDao).getUser()
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).setPass(any())

        usecase.execute("1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(loginRegisterService).setPass(any())
    }

    @Test
    fun callbackErrorWhenErrorInSetPass() {
        val user = User(code = "1234", codeDate = Date())
        doReturn(user).whenever(userDao).getUser()
        val response = getResponse(404, true)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).setPass(any())

        usecase.execute("1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(networkResultLiveData).setNetworkResult(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.BAD_URL)
    }

    @Test
    fun insertUserOnDbOnSucessfulSetPass() {
        val user = User(code = "1234", codeDate = Date())
        doReturn(user).whenever(userDao).getUser()
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).setPass(any())

        usecase.execute("1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.SUCCESS)
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
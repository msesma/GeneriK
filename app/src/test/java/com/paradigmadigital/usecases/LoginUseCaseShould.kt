package com.paradigmadigital.usecases


import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.repository.NetworkResultCode
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.TimeUnit


class LoginUseCaseShould : BaseRepositoryUseCase() {


    private lateinit var usecase: LoginUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = LoginUseCase(repository)
    }

    @Test
    fun apiLoginWithCredentialsWhenExecuted() {

        usecase.execute("bob@acme.com", "1234", 5)

        val credentials = Credentials.basic("bob@acme.com", "1234")
        verify(loginRegisterService).login(credentials)
    }

    @Test
    fun callbackErrorWhenErrorInLogin() {
        val response = getResponse(404, true, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).login(any())

        usecase.execute("bob@acme.com", "1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(networkResultLiveData).setNetworkResult(any())
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.BAD_URL)
    }

    @Test
    fun callbackForbbiddenWhenUnsucessfulLogin() {
        val response = getResponse(200, false, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).login(any())

        usecase.execute("bob@acme.com", "1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(networkResultLiveData).setNetworkResult(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.FORBIDDEN)
    }

    @Test
    fun insertUserOnDbOnSucessfulLogin() {
        val response = getResponse(200, false, true)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).login(any())

        usecase.execute("bob@acme.com", "1234", 5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(userDao).insert(any())
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.SUCCESS)
        assertThat(loginCaptor.firstValue.token).isEqualTo("token")
    }


    private fun getResponse(code: Int, error: Boolean, loggedIn: Boolean): Response<Login> {
        if (error) {
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "{}")
            return Response.error<Login>(code, responseBody)
        }
        val login = Login()
        if (loggedIn) login.token = "token"
        return Response.success(login)
    }
}
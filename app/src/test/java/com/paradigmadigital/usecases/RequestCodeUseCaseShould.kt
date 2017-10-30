package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.api.model.Code
import com.paradigmadigital.repository.NetworkResultCode
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RequestCodeUseCaseShould : BaseRepositoryUseCase() {


    private lateinit var usecase: RequestCodeUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = RequestCodeUseCase(repository)
    }

    @Test
    fun apiRequestCodeWhenExecuted() {

        usecase.execute(5)

        verify(userDao).getUser()
        verify(loginRegisterService).requestCode(any())
    }

    @Test
    fun callbackErrorWhenErrorInRequestCode() {
        val response = getResponse(404, true)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).requestCode(any())

        usecase.execute(5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(networkResultLiveData).setNetworkResult(any())
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.BAD_URL)
    }

    @Test
    fun insertUserOnDbOnSucessfulrequestCode() {
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).requestCode(any())

        usecase.execute(5)

        TimeUnit.MILLISECONDS.sleep(200);
        verify(userDao).setCode(eq("1234"), any(), eq("bob@acme.com"))
        Assertions.assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.SUCCESS)
    }


    private fun getResponse(returnCode: Int, error: Boolean): Response<Code> {
        if (error) {
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "{}")
            return Response.error<Code>(returnCode, responseBody)
        }
        val code = Code()
        code.code = "1234"
        code.email = "bob@acme.com"
        return Response.success(code)
    }
}

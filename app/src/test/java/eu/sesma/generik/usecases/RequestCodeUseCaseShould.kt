package eu.sesma.generik.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import eu.sesma.generik.api.model.Code
import eu.sesma.generik.repository.NetworkResultCode
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RequestCodeUseCaseShould : BaseRepositoryUseCaseTest() {


    private lateinit var usecase: RequestCodeUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = RequestCodeUseCase(repository)
    }

    @Test
    fun apiRequestCodeWhenExecuted() {

        usecase.execute(5)

        TimeUnit.MILLISECONDS.sleep(200);
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
        Assertions.assertThat(resultCaptor.firstValue.code).isEqualTo(NetworkResultCode.BAD_URL)
    }

    @Test
    fun insertUserOnDbOnSucessfulrequestCode() {
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).requestCode(any())

        usecase.execute(5)

        TimeUnit.MILLISECONDS.sleep(200);
        Assertions.assertThat(resultCaptor.firstValue.code).isEqualTo(NetworkResultCode.SUCCESS)
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

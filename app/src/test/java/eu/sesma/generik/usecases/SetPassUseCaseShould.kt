package eu.sesma.generik.usecases


import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.repository.NetworkResultCode
import junit.framework.Assert.assertEquals
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit


class SetPassUseCaseShould : BaseRepositoryUseCaseTest() {

    private lateinit var usecase: SetPassUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = SetPassUseCase(repository)
    }

    @Test
    fun apiRequestCodeWhenExecutedWithIncorrectCode() {
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("4321")
        whenever(preferences.codeTime).thenReturn(Date())

        usecase.execute("1234", "bob@acme.com", "654321", 5)

        TimeUnit.MILLISECONDS.sleep(200)
        verify(networkResultLiveData).setNetworkResult(any())
        assertEquals(NetworkResultCode.FAIL, resultCaptor.firstValue.code)
    }

    @Test
    fun apiRequestCodeWhenExecutedWithCodeDateTooOld() {
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("1234")
        whenever(preferences.codeTime).thenReturn(Date(0))
        usecase.execute("1234", "bob@acme.com", "654321", 5)

        TimeUnit.MILLISECONDS.sleep(200)
        verify(networkResultLiveData).setNetworkResult(any())
        assertEquals(NetworkResultCode.FAIL, resultCaptor.firstValue.code)
    }

    @Test
    fun apiRequestCodeWhenEmailOk() {
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("1234")
        whenever(preferences.codeTime).thenReturn(Date())
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).setPass(any(), any())

        usecase.execute("1234", "bob@acme.com", "654321", 5)

        TimeUnit.MILLISECONDS.sleep(200)
        verify(loginRegisterService).setPass(any(), any())
    }

    @Test
    fun callbackErrorWhenErrorInSetPass() {
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("1234")
        whenever(preferences.codeTime).thenReturn(Date())
        val response = getResponse(404, true)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).setPass(any(), any())

        usecase.execute("1234", "bob@acme.com", "654321", 5)

        TimeUnit.MILLISECONDS.sleep(200)
        verify(networkResultLiveData).setNetworkResult(any())
        assertEquals(NetworkResultCode.BAD_URL, resultCaptor.firstValue.code)
    }

    @Test
    fun insertUserOnDbOnSucessfulSetPass() {
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("1234")
        whenever(preferences.codeTime).thenReturn(Date())
        val response = getResponse(200, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).setPass(any(), any())

        usecase.execute("1234", "bob@acme.com", "654321", 5)

        TimeUnit.MILLISECONDS.sleep(200)
        assertEquals(NetworkResultCode.SUCCESS, resultCaptor.firstValue.code)
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
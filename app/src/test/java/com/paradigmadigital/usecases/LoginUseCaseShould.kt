package com.paradigmadigital.usecases


import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.*
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


class LoginUseCaseShould : BaseRepositoryUseCase(){

    @Mock
    private lateinit var networkResultLiveData: NetworkResultLiveData
    @Mock
    private lateinit var userDao: UserDao
    @Mock
    private lateinit var securePreferences: SecurePreferences
    @Mock
    private lateinit var loginMapper: LoginMapper
    @Mock
    private lateinit var userMapper: UserMapper
    @Mock
    private lateinit var retrofit: Retrofit
    @Mock
    private lateinit var loginRegisterService: LoginRegisterService
    @Mock
    private lateinit var call: Call<Login>

    private val resultCaptor = argumentCaptor<NetworkResult>()
    private val loginCaptor = argumentCaptor<Login>()
    private lateinit var repository: Repository
    private lateinit var usecase: LoginUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        doNothing().whenever(networkResultLiveData).setNetworkResult(resultCaptor.capture())
        doReturn(User()).whenever(loginMapper).map(loginCaptor.capture())
        doReturn(loginRegisterService).whenever(retrofit).create<LoginRegisterService>(any())
        repository = Repository(networkResultLiveData, userDao, securePreferences, loginMapper, userMapper, retrofit)
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
        val response = getResponse(404, false, false)
        doReturn(response).whenever(call).execute()
        doReturn(call).whenever(loginRegisterService).login(any())

        usecase.execute("bob@acme.com", "1234", 5)


        TimeUnit.MILLISECONDS.sleep(200);
        verify(networkResultLiveData).setNetworkResult(any())
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.FORBIDDEN)
    }

    @Test
    fun insertUserOnDbOnSucessfulLogin() {
        val response = getResponse(404, false, true)
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
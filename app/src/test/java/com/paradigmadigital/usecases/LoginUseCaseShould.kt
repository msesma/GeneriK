package com.paradigmadigital.usecases


import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.SecurePreferences
import kotlinx.coroutines.experimental.Job
import okhttp3.Credentials
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit


class LoginUseCaseShould {

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
    private lateinit var job: Job

    private lateinit var repository: Repository
    private lateinit var usecase: LoginUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        doReturn(loginRegisterService).whenever(retrofit).create<LoginRegisterService>(any())
        repository = Repository(networkResultLiveData, userDao, securePreferences, loginMapper, userMapper, retrofit)
        usecase = LoginUseCase(repository)
     }

    @Test
    fun executeFunWhenExecuted() {

        usecase.execute("bob@acme.com", "1234", 5)

        val credentials = Credentials.basic("bob@acme.com", "1234")
        verify(loginRegisterService).login(credentials)
    }

//    @Test
//    fun verifyFunction() {
//
//        usecase.execute("bob@acme.com", "1234", 5)
//
//        val command = functionCaptor.firstValue
//
//        val b = 1
//
//    }
}
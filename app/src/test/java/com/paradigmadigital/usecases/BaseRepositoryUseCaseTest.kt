package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.repository.preferences.Preferences
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Retrofit

open class BaseRepositoryUseCaseTest {

    @Mock lateinit var networkResultLiveData: NetworkResultLiveData
    @Mock lateinit var accountManager: OauthAccountManager
    @Mock lateinit var retrofit: Retrofit
    @Mock lateinit var loginRegisterService: LoginRegisterService
    @Mock lateinit var call: Call<Login>
    @Mock lateinit var preferences: Preferences
    @Mock lateinit var userDao: UserDao
    @Mock lateinit var loginMapper: LoginMapper

    val resultCaptor = argumentCaptor<NetworkResult>()
    lateinit var repository: LoginRepository

    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        doNothing().whenever(networkResultLiveData).setNetworkResult(resultCaptor.capture())
        doReturn(loginRegisterService).whenever(retrofit).create<LoginRegisterService>(any())
        doReturn("bob@acme.com").whenever(accountManager).getEmail()

        repository = LoginRepository(
                networkResultLiveData,
                userDao,
                preferences,
                accountManager,
                loginMapper,
                retrofit)
    }
}
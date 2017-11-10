package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.preferences.Preferences
import com.paradigmadigital.repository.securepreferences.SecurePreferences
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Retrofit

open class BaseRepositoryUseCase {

    @Mock lateinit var networkResultLiveData: NetworkResultLiveData
    @Mock lateinit var userDao: UserDao
    @Mock lateinit var securePreferences: SecurePreferences
    @Mock lateinit var loginMapper: LoginMapper
    @Mock lateinit var userMapper: UserMapper
    @Mock lateinit var accountManager: OauthAccountManager
    @Mock lateinit var retrofit: Retrofit
    @Mock lateinit var loginRegisterService: LoginRegisterService
    @Mock lateinit var call: Call<Login>
    @Mock lateinit var preferences: Preferences

    val resultCaptor = argumentCaptor<NetworkResult>()
    val loginCaptor = argumentCaptor<Login>()
    lateinit var repository: Repository

    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        doNothing().whenever(networkResultLiveData).setNetworkResult(resultCaptor.capture())
        doReturn(User()).whenever(loginMapper).map(loginCaptor.capture())
        doReturn(Login()).whenever(userMapper).map(any())
        doReturn(loginRegisterService).whenever(retrofit).create<LoginRegisterService>(any())
        doReturn(User()).whenever(userDao).getUser()
        doReturn("1234").whenever(securePreferences).password
        repository = Repository(
                networkResultLiveData,
                userDao,
                securePreferences,
                preferences,
                loginMapper,
                userMapper,
                accountManager,
                retrofit)
    }
}
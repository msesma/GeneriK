package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.SecurePreferences
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Retrofit

open class BaseRepositoryUseCase {

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

    fun setUp() {
        MockitoAnnotations.initMocks(this)
        doNothing().whenever(networkResultLiveData).setNetworkResult(resultCaptor.capture())
        doReturn(User()).whenever(loginMapper).map(loginCaptor.capture())
        doReturn(loginRegisterService).whenever(retrofit).create<LoginRegisterService>(any())
        repository = Repository(networkResultLiveData, userDao, securePreferences, loginMapper, userMapper, retrofit)
    }
}
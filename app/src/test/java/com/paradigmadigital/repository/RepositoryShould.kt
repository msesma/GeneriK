package com.paradigmadigital.repository

import com.nhaarman.mockito_kotlin.any
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.mappers.LoginMapper
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit


class RepositoryShould {
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

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(retrofit.create<LoginRegisterService>(any())).thenReturn(loginRegisterService)
        repository = Repository(networkResultLiveData, userDao, securePreferences, loginMapper, userMapper, retrofit)
    }

    @Test
    fun updatePassOnUpdatePass() {

    }
}
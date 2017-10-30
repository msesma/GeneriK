package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import org.assertj.core.api.Assertions.assertThat
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
    fun returnNetworkLiveDataOnGetErrors() {

        val errorLiveData = repository.getErrors()

        assertThat(errorLiveData).isEqualTo(networkResultLiveData)
    }

    @Test
    fun returnFalseOnIsLoggedInWhenTokenNotExist() {
        `when`(userDao.getUser()).thenReturn(User(token = ""))

        val logged = repository.isLoggedIn()

        verify(userDao).getUser()
        assertThat(logged).isFalse()
    }

    @Test
    fun returnTrueOnIsLoggedInWhenTokenExist() {
        `when`(userDao.getUser()).thenReturn(User(token = "vkhgfkhgf"))

        val logged = repository.isLoggedIn()

        verify(userDao).getUser()
        assertThat(logged).isTrue()
    }

    @Test
    fun getUserliveDataOnGetUser() {
        val liveData = object : LiveData<User>() {}
        `when`(userDao.get()).thenReturn(liveData)

        val userliveData = repository.getUser()

        verify(userDao).get()
        assertThat(userliveData).isEqualTo(liveData)
    }

    @Test
    fun setUserOnSetUser() {

        repository.setUser(email = "bob@acme.com")

        verify(userDao).insert(User(email = "bob@acme.com"))
    }

    @Test
    fun updatePassOnUpdatePass() {

        repository.updatePass("password")

        verify(securePreferences).password = "password"
    }
}
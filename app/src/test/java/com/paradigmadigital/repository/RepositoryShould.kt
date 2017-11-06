package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.preferences.Preferences
import com.paradigmadigital.repository.securepreferences.SecurePreferences
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class RepositoryShould {
    @Mock
    private lateinit var networkResultLiveData: NetworkResultLiveData
    @Mock
    private lateinit var userDao: UserDao
    @Mock
    private lateinit var securePreferences: SecurePreferences
    @Mock
    private lateinit var preferences: Preferences
    @Mock
    private lateinit var loginMapper: LoginMapper
    @Mock
    private lateinit var userMapper: UserMapper
    @Mock
    private lateinit var retrofit: Retrofit
    @Mock
    private lateinit var loginRegisterService: LoginRegisterService

    private val resultCaptor = argumentCaptor<NetworkResult>()
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(retrofit.create<LoginRegisterService>(any())).thenReturn(loginRegisterService)
        doNothing().whenever(networkResultLiveData).setNetworkResult(resultCaptor.capture())
        repository = Repository(
                networkResultLiveData,
                userDao,
                securePreferences,
                preferences,
                loginMapper,
                userMapper,
                retrofit)
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

    @Test
    fun sendDisconnectedOnUnknownHostException() {

        repository.executeInteractor {
            throw UnknownHostException()
        }

        TimeUnit.MILLISECONDS.sleep(200);
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.DISCONNECTED)
    }

    @Test
    fun sendBadUrlOn404() {

        repository.executeInteractor {
            throw RuntimeException("404")
        }

        TimeUnit.MILLISECONDS.sleep(200);
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.BAD_URL)
    }

    @Test
    fun sendUnknownOnUnknownException() {

        repository.executeInteractor {
            throw RuntimeException()
        }

        TimeUnit.MILLISECONDS.sleep(200);
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.UNKNOWN)
    }

    @Test
    fun sendForbiddenOnForbidden403() {

        repository.executeInteractor {
            throw RuntimeException("403")
        }

        TimeUnit.MILLISECONDS.sleep(200);
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.FORBIDDEN)
    }

    @Test
    fun sendSuccessOnNoError() {

        repository.executeInteractor {
        }

        TimeUnit.MILLISECONDS.sleep(200);
        assertThat(resultCaptor.firstValue.result).isEqualTo(NetworkResultCode.SUCCESS)
    }
}
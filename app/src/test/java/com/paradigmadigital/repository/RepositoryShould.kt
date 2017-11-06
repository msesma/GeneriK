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
    fun returnTrueIsFingerPrintAuthdataAvailableWhenEmailAndPass() {
        whenever (userDao.getUser()).thenReturn(User(email = "bob@acme.com"))
        whenever (securePreferences.password).thenReturn("1234")

        val dataAvailable = repository.isFingerPrintAuthdataAvailable

        verify(userDao).getUser()
        assertThat(dataAvailable).isTrue()
    }

    @Test
    fun returnFalseIsFingerPrintAuthdataAvailableWhenNoEmail() {
        whenever (userDao.getUser()).thenReturn(User(email = ""))
        whenever (securePreferences.password).thenReturn("1234")

        val dataAvailable = repository.isFingerPrintAuthdataAvailable

        assertThat(dataAvailable).isFalse()
    }

    @Test
    fun returnFalseIsFingerPrintAuthdataAvailableWhenNoPass() {
        whenever (userDao.getUser()).thenReturn(User(email = "bob@acme.com"))
        whenever (securePreferences.password).thenReturn("")

        val dataAvailable = repository.isFingerPrintAuthdataAvailable

        assertThat(dataAvailable).isFalse()
    }

    @Test
    fun returnTrueRequireLoginWhenSelectedAndTimedOut() {
        whenever (preferences.timeout).thenReturn(true)
        whenever (preferences.requireLogin).thenReturn(true)

        val dataAvailable = repository.requireLogin

        assertThat(dataAvailable).isTrue()
    }

    @Test
    fun returnFalseRequireLoginWhenNotSelected() {
        whenever (preferences.timeout).thenReturn(false)
        whenever (preferences.requireLogin).thenReturn(true)

        val dataAvailable = repository.requireLogin

        assertThat(dataAvailable).isFalse()
    }

    @Test
    fun returnFalseRequireLoginWhenNotTimedOut() {
        whenever (preferences.timeout).thenReturn(true)
        whenever (preferences.requireLogin).thenReturn(false)

        val dataAvailable = repository.requireLogin

        assertThat(dataAvailable).isFalse()
    }

    @Test
    fun LogoutOnTimeoutCheckifTimedOut() {
        whenever (preferences.timeout).thenReturn(true)
        whenever (preferences.requireLogin).thenReturn(true)

        repository.timeoutRequireLoginCheck()

        verify(userDao).logout()
    }

    @Test
    fun notLogoutOnTimeoutCheckifTimedOut() {
        whenever (preferences.timeout).thenReturn(false)
        whenever (preferences.requireLogin).thenReturn(true)

        repository.timeoutRequireLoginCheck()

        verifyZeroInteractions(userDao)
    }

    @Test
    fun returnFalseOnIsLoggedInWhenTokenNotExist() {
        whenever (userDao.getUser()).thenReturn(User(token = ""))

        val logged = repository.isLoggedIn()

        verify(userDao).getUser()
        assertThat(logged).isFalse()
    }

    @Test
    fun returnTrueOnIsLoggedInWhenTokenExist() {
        whenever (userDao.getUser()).thenReturn(User(token = "vkhgfkhgf"))

        val logged = repository.isLoggedIn()

        verify(userDao).getUser()
        assertThat(logged).isTrue()
    }

    @Test
    fun getUserliveDataOnGetUser() {
        val liveData = object : LiveData<User>() {}
        whenever (userDao.get()).thenReturn(liveData)

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
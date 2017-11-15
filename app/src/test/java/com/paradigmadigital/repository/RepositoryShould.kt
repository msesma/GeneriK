package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.services.LoginRegisterService
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.preferences.Preferences
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.TimeUnit


class RepositoryShould {
    @Mock
    private lateinit var networkResultLiveData: NetworkResultLiveData
    @Mock
    private lateinit var userDao: UserDao
    @Mock
    private lateinit var preferences: Preferences
    @Mock
    private lateinit var loginMapper: LoginMapper
    @Mock
    private lateinit var accountManager: OauthAccountManager
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
                preferences,
                accountManager,
                loginMapper,
                retrofit)
    }

    @Test
    fun returnNetworkLiveDataOnGetErrors() {

        val errorLiveData = repository.getErrors()

        assertThat(errorLiveData).isEqualTo(networkResultLiveData)
    }


    @Test
    @Ignore
    fun LogoutOnTimeoutCheckifTimedOut() {
        whenever(preferences.timeout).thenReturn(true)
        whenever(preferences.requireLogin).thenReturn(true)

        repository.timeoutRequireLoginCheck()

//        verify(userDao).logout()
    }

    @Test
    @Ignore
    fun notLogoutOnTimeoutCheckifTimedOut() {
        whenever(preferences.timeout).thenReturn(false)
        whenever(preferences.requireLogin).thenReturn(true)

        repository.timeoutRequireLoginCheck()

        verifyZeroInteractions(userDao)
    }

    @Test
    fun returnFalseOnIsLoggedInWhenTokenNotExist() {
        whenever(accountManager.isLoggedIn()).thenReturn(false)

        val logged = repository.isLoggedIn()

        verify(accountManager).isLoggedIn()
        assertThat(logged).isFalse()
    }

    @Test
    fun returnTrueOnIsLoggedInWhenTokenExist() {
        whenever(accountManager.isLoggedIn()).thenReturn(true)

        val logged = repository.isLoggedIn()

        verify(accountManager).isLoggedIn()
        assertThat(logged).isTrue()
    }


    @Test
    fun getEmailfromAccountManagerOnGetEmail() {
        whenever(accountManager.getEmail()).thenReturn("bob@acme.com")

        val email = repository.getEmail()

        assertThat(email).isEqualTo("bob@acme.com")
    }


    @Test
    fun getUserliveDataOnGetUser() {
        val liveData = object : LiveData<User>() {}
        whenever(userDao.get()).thenReturn(liveData)

        val userliveData = repository.getUser()

        verify(userDao).get()
        assertThat(userliveData).isEqualTo(liveData)
    }

    @Test
    fun setUserOnSetUser() {
        val loginCaptor = argumentCaptor<Login>()
        doReturn(User(email = "bob@acme.com")).whenever(loginMapper).map(loginCaptor.capture())

        repository.setUser(Login(email = "bob@acme.com"))

        verify(loginMapper).map(any())
        verify(userDao).insert(any())
        assertThat(loginCaptor.firstValue.email).isEqualTo("bob@acme.com")
    }

    @Test
    fun setCodeOnSetCode() {
        val date = Date()

        repository.setCode("123456", date, "bob@acme.com")

        verify(preferences).setCode("123456", date, "bob@acme.com")
    }


    @Test
    fun getCodeOngetCodeOnTimeForCorrectUser() {
        whenever(preferences.codeTime).thenReturn(Date())
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("123456")

        val code = repository.getCode("bob@acme.com")

        assertThat(code).isEqualTo("123456")
    }


    @Test
    fun doNotGetCodeOngetCodeOffTimeForCorrectUser() {
        whenever(preferences.codeTime).thenReturn(Date(0))
        whenever(preferences.codeEmail).thenReturn("bob@acme.com")
        whenever(preferences.code).thenReturn("123456")

        val code = repository.getCode("bob@acme.com")

        assertThat(code).isEqualTo("")
    }


    @Test
    fun doNotGetCodeOngetCodeOonTimeForIncorrectUser() {
        whenever(preferences.codeTime).thenReturn(Date())
        whenever(preferences.codeEmail).thenReturn("ann@acme.com")
        whenever(preferences.code).thenReturn("123456")

        val code = repository.getCode("bob@acme.com")

        assertThat(code).isEqualTo("")
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
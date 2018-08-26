package eu.sesma.generik.usecases


import com.nhaarman.mockito_kotlin.*
import eu.sesma.generik.account.OauthAccountManager
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.api.services.LoginRegisterService
import eu.sesma.generik.domain.db.UserDao
import eu.sesma.generik.domain.mappers.UserMapper
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResult
import eu.sesma.generik.repository.NetworkResultLiveData
import eu.sesma.generik.repository.preferences.Preferences
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
    @Mock lateinit var userMapper: UserMapper

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
                userMapper,
                retrofit)
    }
}
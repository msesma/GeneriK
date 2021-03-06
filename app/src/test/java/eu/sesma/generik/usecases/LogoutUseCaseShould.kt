package eu.sesma.generik.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class LogoutUseCaseShould : BaseRepositoryUseCaseTest() {

    private lateinit var usecase: LogoutUseCase

    @Before
    override fun setUp() {
        super.setUp()
        usecase = LogoutUseCase(repository)
    }

    @Test
    fun apiLogoutWithUserWhenExecuted() {

        usecase.execute()

        TimeUnit.MILLISECONDS.sleep(200);
        verify(accountManager).logout()
        verify(loginRegisterService).logout(any())
    }

    @Test
    fun accountClearUserDataWhenExecuted() {

        usecase.execute()

        TimeUnit.MILLISECONDS.sleep(200);
        //TODO verify clearinbg
    }
}

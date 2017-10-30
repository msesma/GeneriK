package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paradigmadigital.domain.entities.User
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class LogoutUseCaseShould : BaseRepositoryUseCase() {

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
        verify(userDao).getUser()
        verify(loginRegisterService).logout(any())
    }

    @Test
    fun dbLogoutWhenExecuted() {

        usecase.execute()

        TimeUnit.MILLISECONDS.sleep(200);
        verify(userDao).logout()
    }
}

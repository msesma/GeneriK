package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LogoutUseCaseShould {
    @Mock
    private lateinit var repository: Repository

    private lateinit var usecase: LogoutUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = LogoutUseCase(repository)
    }

    @Test
    fun returnFunWhenExecuted() {

        usecase.execute()

//        verify(repository).setUser("bob@acme.com")
//        verify(navigator).navigateToChangePassword()
    }
}

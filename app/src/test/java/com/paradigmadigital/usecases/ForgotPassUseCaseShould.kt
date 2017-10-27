package com.paradigmadigital.usecases

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ForgotPassUseCaseShould {
    @Mock
    private lateinit var repository: Repository
    @Mock
    private lateinit var navigator: Navigator

    private lateinit var usecase: ForgotPassUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = ForgotPassUseCase(repository, navigator)
    }

    @Test
    fun returnFunWhenExecuted() {

    }
}
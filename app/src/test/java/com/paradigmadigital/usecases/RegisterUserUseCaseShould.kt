package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class RegisterUserUseCaseShould {
    @Mock
    private lateinit var repository: Repository

    private lateinit var usecase: RegisterUserUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = RegisterUserUseCase(repository)
    }

    @Test
    fun returnFunWhenExecuted() {

    }
}
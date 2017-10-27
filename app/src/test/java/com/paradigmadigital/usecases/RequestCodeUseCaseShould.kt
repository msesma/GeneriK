package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RequestCodeUseCaseShould {
    @Mock
    private lateinit var repository: Repository

    private lateinit var usecase: RequestCodeUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = RequestCodeUseCase(repository)
    }

    @Test
    fun returnFunWhenExecuted() {

    }
}

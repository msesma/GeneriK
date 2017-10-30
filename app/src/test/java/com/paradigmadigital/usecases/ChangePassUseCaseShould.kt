package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.verify
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ChangePassUseCaseShould {
    @Mock
    private lateinit var repository: Repository
    @Mock
    private lateinit var navigator: Navigator

    private lateinit var usecase: ChangePassUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = ChangePassUseCase(repository, navigator)
    }

    @Test
    fun setPassClosesAndNavigateToInputCodeExecuted() {

        usecase.execute("1234")

        verify(repository).updatePass("1234")
        verify(navigator).closeActivity()
        verify(navigator).navigateToInputCode()
    }
}
package com.paradigmadigital.ui.changepass

import com.paradigmadigital.usecases.ChangePassUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ChangePassPresenterShould {
    @Mock
    private lateinit var changePassUseCase: ChangePassUseCase

    private lateinit var presenter: ChangePassPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ChangePassPresenter(changePassUseCase)
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

    }
}
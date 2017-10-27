package com.paradigmadigital.ui.register

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.RegisterUserUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class RegisterPresenterShould {
    @Mock
    private lateinit var registerUserUseCase: RegisterUserUseCase
    @Mock
    private lateinit var navigator: Navigator

    private lateinit var presenter: RegisterPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = RegisterPresenter(navigator, registerUserUseCase)
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

    }
}
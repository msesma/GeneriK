package com.paradigmadigital.ui.login

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.ForgotPassUseCase
import com.paradigmadigital.usecases.LoginUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class LoginPresenterShould {
    @Mock
    private lateinit var loginUseCase: LoginUseCase
    @Mock
    private lateinit var forgotPassUseCase: ForgotPassUseCase
    @Mock
    private lateinit var navigator: Navigator

    private lateinit var presenter: LoginPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginPresenter(navigator, loginUseCase, forgotPassUseCase)
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

    }
}
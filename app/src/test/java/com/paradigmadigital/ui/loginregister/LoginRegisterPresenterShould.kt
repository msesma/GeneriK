package com.paradigmadigital.ui.loginregister

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.navigation.Navigator
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginRegisterPresenterShould {
    @Mock
    private lateinit var navigator: Navigator
    @Mock
    private lateinit var decorator: LoginRegisterUserInterface

    private val delegateCaptor = argumentCaptor<LoginRegisterUserInterface.Delegate>()
    private lateinit var presenter: LoginRegisterPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginRegisterPresenter(navigator)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator)

        verify(decorator).initialize(any())
    }

    @Test
    fun navigateToLoginOnLogin() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onLogin()

        verify(navigator).navigateToLogin()
    }

    @Test
    fun navigateToRegisterOnRegister() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onRegister()

        verify(navigator).navigateToRegister()
    }

    @Test
    fun navigateToTermsOnterms() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onTerms()

        verify(navigator).navigateToTerms()
    }
}
package com.paradigmadigital.ui.login

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.ui.viewmodels.UserViewModel
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
    @Mock
    private lateinit var decorator: LoginUserInterface
    @Mock
    private lateinit var resultViewModel: ResultViewModel
    @Mock
    private lateinit var userViewModel: UserViewModel

    private val delegateCaptor = argumentCaptor<LoginUserInterface.Delegate>()
    private lateinit var presenter: LoginPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginPresenter(navigator, loginUseCase, forgotPassUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any(), any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, userViewModel, resultViewModel)

        verify(decorator).initialize(any(), any(), any())
    }

    @Test
    fun callUseCaseOnLogin() {
        presenter.initialize(decorator, userViewModel, resultViewModel)

        delegateCaptor.firstValue.onLogin("bob@acme.com","123456")

        verify(loginUseCase).execute("bob@acme.com","123456", LoginDecorator.REQUEST_LOGIN)
    }

    @Test
    fun callUseCaseOnForgotPassword() {
        presenter.initialize(decorator, userViewModel, resultViewModel)

        delegateCaptor.firstValue.onForgotPassword("bob@acme.com")

        verify(forgotPassUseCase).execute("bob@acme.com")
    }

    @Test
    fun navigateToMainOnLoggedIn() {
        presenter.initialize(decorator, userViewModel, resultViewModel)

        delegateCaptor.firstValue.onLoggedIn()

        verify(navigator).navigateToMain()
    }
}
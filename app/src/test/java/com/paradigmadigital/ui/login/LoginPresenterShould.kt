package com.paradigmadigital.ui.login

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.LoginUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginPresenterShould {
    @Mock private lateinit var loginUseCase: LoginUseCase
    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var decorator: LoginUserInterface
    @Mock private lateinit var resultViewModel: ResultViewModel
    @Mock private lateinit var repository: LoginRepository

    private val delegateCaptor = argumentCaptor<LoginUserInterface.Delegate>()
    private lateinit var presenter: LoginPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginPresenter(
                navigator,
                loginUseCase,
                repository)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, resultViewModel)

        verify(decorator).initialize(any(), any())
    }

    @Test
    fun callUseCaseOnLogin() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onLogin("bob@acme.com", "123456")

        verify(loginUseCase).execute("bob@acme.com", "123456", LoginDecorator.REQUEST_LOGIN)
    }

    @Test
    fun callUseCaseOnForgotPassword() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onForgotPassword("bob@acme.com")

        verify(navigator).navigateToChangePassword("bob@acme.com")
    }

    @Test
    fun navigateToMainOnLoggedIn() {
        presenter.initialize(decorator, resultViewModel)
        whenever(repository.isLoggedIn()).thenReturn(true)

        delegateCaptor.firstValue.onLoggedIn()

        verify(navigator).navigateToMain()
    }
}
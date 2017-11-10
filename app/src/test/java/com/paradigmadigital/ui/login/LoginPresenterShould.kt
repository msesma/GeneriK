package com.paradigmadigital.ui.login

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.ui.viewmodels.UserViewModel
import com.paradigmadigital.usecases.ForgotPassUseCase
import com.paradigmadigital.usecases.LoginUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginPresenterShould {
    @Mock private lateinit var loginUseCase: LoginUseCase
    @Mock private lateinit var forgotPassUseCase: ForgotPassUseCase
    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var decorator: LoginUserInterface
    @Mock private lateinit var resultViewModel: ResultViewModel
    @Mock private lateinit var userViewModel: UserViewModel
    @Mock private lateinit var fingerprintManager: FingerprintManager
    @Mock private lateinit var repository: Repository

    private val delegateCaptor = argumentCaptor<LoginUserInterface.Delegate>()
    private val callbackCaptor = argumentCaptor<(Boolean) -> Unit>()
    private lateinit var presenter: LoginPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginPresenter(
                navigator,
                loginUseCase,
                forgotPassUseCase,
                fingerprintManager,
                repository)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any(), any())
        doNothing().whenever(fingerprintManager).startAuth(callbackCaptor.capture())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, userViewModel, resultViewModel)

        verify(decorator).initialize(any(), any(), any())
    }

//    @Test
//    fun checkTimeoutWhenInitialized() {
//
//        presenter.initialize(decorator, userViewModel, resultViewModel)
//
//        verify(repository).timeoutRequireLoginCheck()
//    }

    @Test
    fun initializeFingerprintManagerWhenInitialized() {

        presenter.initialize(decorator, userViewModel, resultViewModel)

        assertThat(callbackCaptor.firstValue).isNotNull()
    }

    @Test
    fun callUseCaseOnLogin() {
        presenter.initialize(decorator, userViewModel, resultViewModel)

        delegateCaptor.firstValue.onLogin("bob@acme.com", "123456")

        verify(loginUseCase).execute("bob@acme.com", "123456", LoginDecorator.REQUEST_LOGIN)
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


    @Test
    fun doLoginIfFingerprintAuthOk() {
        presenter.initialize(decorator, userViewModel, resultViewModel)

        callbackCaptor.firstValue.invoke(true)

        verify(repository).getEmail()
        verify(repository).getPass()
        verify(loginUseCase).execute(anyOrNull(), anyOrNull(), eq(LoginDecorator.REQUEST_LOGIN))
    }

    @Test
    fun doNotLoginIfFingerprintAuthOk() {
        presenter.initialize(decorator, userViewModel, resultViewModel)

        callbackCaptor.firstValue.invoke(false)

        verify(repository, never()).getEmail()
        verify(repository, never()).getPass()
        verifyZeroInteractions(loginUseCase)
    }
}
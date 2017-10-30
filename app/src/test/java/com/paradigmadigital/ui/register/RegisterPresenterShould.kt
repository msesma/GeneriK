package com.paradigmadigital.ui.register

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.RegisterUserUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisterPresenterShould {
    @Mock
    private lateinit var registerUserUseCase: RegisterUserUseCase
    @Mock
    private lateinit var navigator: Navigator
    @Mock
    private lateinit var decorator: RegisterUserInterface
    @Mock
    private lateinit var resultViewModel: ResultViewModel

    private val delegateCaptor = argumentCaptor<RegisterUserInterface.Delegate>()
    private lateinit var presenter: RegisterPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = RegisterPresenter(navigator, registerUserUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, resultViewModel)

        verify(decorator).initialize(any(), any())
    }

    @Test
    fun callUseCaseOnRegister() {
        val userCaptor = argumentCaptor<User>()
        doNothing().whenever(registerUserUseCase).execute(userCaptor.capture(), any(), any())
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onRegister("Bob", "123456789", "bob@acme.com", "1234")

        verify(registerUserUseCase).execute(any(),eq("1234"), eq(RegisterDecorator.REQUEST_REGISTER))
        assertThat(userCaptor.firstValue.name).isEqualTo("Bob")
        assertThat(userCaptor.firstValue.phone).isEqualTo("123456789")
        assertThat(userCaptor.firstValue.email).isEqualTo("bob@acme.com")
    }

    @Test
    fun navigateToInputCodeOnRegistered() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onRegistered()

        verify(navigator).navigateToInputCode()
    }
}
package com.paradigmadigital.ui.inputcode

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.RequestCodeUseCase
import com.paradigmadigital.usecases.SetPassUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class InputCodePresenterShould {
    @Mock
    private lateinit var setPassUseCase: SetPassUseCase
    @Mock
    private lateinit var requestCodeUseCase: RequestCodeUseCase
    @Mock
    private lateinit var navigator: Navigator
    @Mock
    private lateinit var smsManager: SmsManager
    @Mock
    private lateinit var decorator: InputCodeUserInterface
    @Mock
    private lateinit var resultViewModel: ResultViewModel

    private val delegateCaptor = argumentCaptor<InputCodeUserInterface.Delegate>()
    private lateinit var presenter: InputCodePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = InputCodePresenter(navigator, smsManager, setPassUseCase, requestCodeUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, resultViewModel)

        verify(decorator).initialize(any(), any())
    }

    @Test
    fun callUseCaseOnCode() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onCode("123456")

        verify(setPassUseCase).execute(eq("123456"), eq(InputCodeDecorator.REQUEST_SET_PASS))
    }

    @Test
    fun callUseCaseOnSendNew() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onSendNew()

        verify(requestCodeUseCase, times(2)).execute(InputCodeDecorator.REQUEST_CODE)
    }

    @Test
    fun navigateToLoginRegisterOnCodeSent() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onCodeSent()

        verify(navigator).navigateToLoginRegister()
    }
}

package eu.sesma.generik.ui.inputcode

import com.nhaarman.mockito_kotlin.*
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.ui.viewmodels.ResultViewModel
import eu.sesma.generik.usecases.RequestCodeUseCase
import eu.sesma.generik.usecases.SetPassUseCase
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

        presenter.initialize(decorator, resultViewModel, "bob@acme.com", "password")

        verify(decorator).initialize(any(), any())
    }

    @Test
    fun callUseCaseOnCode() {
        presenter.initialize(decorator, resultViewModel, "bob@acme.com", "password")

        delegateCaptor.firstValue.onCode("123456")

        verify(setPassUseCase).execute(
                eq("123456"),
                eq("bob@acme.com"),
                eq("password"),
                eq(InputCodeDecorator.REQUEST_SET_PASS)
        )
    }

    @Test
    fun callUseCaseOnSendNew() {
        presenter.initialize(decorator, resultViewModel, "bob@acme.com", "password")

        delegateCaptor.firstValue.onSendNew()

        verify(requestCodeUseCase, times(2)).execute(InputCodeDecorator.REQUEST_CODE)
    }

    @Test
    fun navigateToLoginRegisterOnCodeSent() {
        presenter.initialize(decorator, resultViewModel, "bob@acme.com", "password")

        delegateCaptor.firstValue.onCodeSent()

        verify(navigator).navigateToLoginRegister()
    }
}

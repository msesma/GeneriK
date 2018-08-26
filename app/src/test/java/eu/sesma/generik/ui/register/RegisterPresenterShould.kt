package eu.sesma.generik.ui.register

import com.nhaarman.mockito_kotlin.*
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.ui.viewmodels.ResultViewModel
import eu.sesma.generik.usecases.RegisterUserUseCase
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
        val userCaptor = argumentCaptor<Login>()
        doNothing().whenever(registerUserUseCase).execute(userCaptor.capture(), any())
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onRegister("Bob", "123456789", "bob@acme.com")

        verify(registerUserUseCase).execute(any(), eq(RegisterDecorator.REQUEST_REGISTER))
        assertThat(userCaptor.firstValue.name).isEqualTo("Bob")
        assertThat(userCaptor.firstValue.phone).isEqualTo("123456789")
        assertThat(userCaptor.firstValue.email).isEqualTo("bob@acme.com")
    }

    @Test
    fun navigateToInputCodeOnRegistered() {
        presenter.initialize(decorator, resultViewModel)

        delegateCaptor.firstValue.onRegistered("bob@acme.com", "1234")

        verify(navigator).navigateToInputCode("bob@acme.com", "1234")
    }
}
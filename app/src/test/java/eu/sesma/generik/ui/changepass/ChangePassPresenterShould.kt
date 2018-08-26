package eu.sesma.generik.ui.changepass


import com.nhaarman.mockito_kotlin.*
import eu.sesma.generik.usecases.ChangePassUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ChangePassPresenterShould {
    @Mock
    private lateinit var changePassUseCase: ChangePassUseCase
    @Mock
    private lateinit var decorator: ChangePassUserInterface

    private val delegateCaptor = argumentCaptor<ChangePassUserInterface.Delegate>()
    private lateinit var presenter: ChangePassPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ChangePassPresenter(changePassUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, "bob@acme.com")

        verify(decorator).initialize(any())
    }

    @Test
    fun callUseCaseOnNewPass() {
        presenter.initialize(decorator, "bob@acme.com")

        delegateCaptor.firstValue.onNewPass("password")

        verify(changePassUseCase).execute("bob@acme.com", "password")
    }
}
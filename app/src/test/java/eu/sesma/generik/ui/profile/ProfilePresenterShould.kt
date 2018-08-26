package eu.sesma.generik.ui.profile

import com.nhaarman.mockito_kotlin.*
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.toApiResult
import eu.sesma.generik.usecases.ProfileUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ProfilePresenterShould {

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var profileUseCase: ProfileUseCase
    @Mock private lateinit var loginRepository: LoginRepository
    @Mock private lateinit var decorator: ProfileUserInterface

    private val delegateCaptor = argumentCaptor<ProfileUserInterface.Delegate>()
    private lateinit var presenter: ProfilePresenter
    private val single = Single.just<ApiResult>(Login().toApiResult())

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ProfilePresenter(navigator, loginRepository, profileUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any())
        doReturn(single).whenever(profileUseCase).execute(any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator)

        verify(decorator).initialize(any(), any())
    }

    @Test
    fun callUseCaseOnProfileEdit() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onProfileEdit(name = "name", tel = "tel", email = "email")

        verify(profileUseCase).execute(any())
        verify(decorator).onResult(any())
    }


    @Test
    fun navigateToMainOnProfileEdited() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onProfileEdited()

        verify(navigator).navigateToMain()
    }
}

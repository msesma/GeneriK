package eu.sesma.generik.usecases

import com.nhaarman.mockito_kotlin.verify
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.LoginRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ChangePassUseCaseShould {
    @Mock
    private lateinit var repository: LoginRepository
    @Mock
    private lateinit var navigator: Navigator

    private lateinit var usecase: ChangePassUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        usecase = ChangePassUseCase(repository, navigator)
    }

    @Test
    fun setPassClosesAndNavigateToInputCodeExecuted() {

        usecase.execute("bob@acme.com", "1234")

        verify(navigator).closeActivity()
        verify(navigator).navigateToInputCode("bob@acme.com","1234")
    }
}
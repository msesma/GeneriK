package eu.sesma.generik.usecases

import com.nhaarman.mockito_kotlin.verify
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.domain.db.AuthorDao
import eu.sesma.generik.domain.db.PostDao
import eu.sesma.generik.domain.mappers.AuthorMapper
import eu.sesma.generik.domain.mappers.PostMapper
import eu.sesma.generik.repository.*
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ProfileUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: ProfileUseCase
    @Mock lateinit var loginRepository: LoginRepository
    @Mock lateinit var postDao: PostDao
    @Mock lateinit var authorDao: AuthorDao

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        val repository = DataRepository(postDao, authorDao, retrofit, AuthorMapper(), PostMapper())
        useCase = ProfileUseCase(repository, loginRepository)
    }

    @Test
    fun getCommentsHappyPath() {
        enqueueMockResponse(200, "login.json")
        val testObserver = TestObserver<ApiResult>()
        val login = Login(
                        token = "6432871b8c7ynkqwefjgjcbe89neqbfyqug",
                        uid = "12376",
                        name = "Bob",
                        phone = "+54123456789",
                        email = "bob@acme.com"
                )
        val result = login.toApiResult()

        useCase.execute(login).subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(result)
        verify(loginRepository).setUser(login)
    }

    @Test
    fun getCommentsUsesCorrectUrl() {
        enqueueMockResponse(200, "login.json")

        useCase.execute(Login()).subscribe()

        assertPostRequestSentTo("/update")
    }

    @Test
    fun getCommentsManagesHttpError() {
        enqueueMockResponse(403, "login.json")
        val testObserver = TestObserver<ApiResult>()
        val result = ApiResult.Failure(NetworkResultCode.FORBIDDEN)

        useCase.execute(Login()).subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(result)
    }
}

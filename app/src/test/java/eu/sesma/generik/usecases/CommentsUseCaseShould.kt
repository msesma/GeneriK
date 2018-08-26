package eu.sesma.generik.usecases

import eu.sesma.generik.api.model.Comment
import eu.sesma.generik.domain.db.AuthorDao
import eu.sesma.generik.domain.db.PostDao
import eu.sesma.generik.domain.mappers.AuthorMapper
import eu.sesma.generik.domain.mappers.PostMapper
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.DataRepository
import eu.sesma.generik.repository.NetworkResultCode
import eu.sesma.generik.repository.toApiResult
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class CommentsUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: CommentsUseCase
    @Mock lateinit var postDao: PostDao
    @Mock lateinit var authorDao: AuthorDao

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        val repository = DataRepository(postDao, authorDao, retrofit, AuthorMapper(), PostMapper())
        useCase = CommentsUseCase(repository)
    }

    @Test
    fun getCommentsHappyPath() {
        enqueueMockResponse(200, "comments.json")
        val testObserver = TestObserver<ApiResult>()
        val comments = arrayListOf(
                Comment(
                        id = 1,
                        postId = 1,
                        name = "name1",
                        email = "email1",
                        body = "body1"),
                Comment(
                        id = 2,
                        postId = 1,
                        name = "name2",
                        email = "email2",
                        body = "body2"
                )).toApiResult()

        useCase.execute(1).subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(comments)
    }

    @Test
    fun getCommentsUsesCorrectUrl() {
        enqueueMockResponse(200, "comments.json")

        useCase.execute(1).subscribe()

        assertGetRequestSentTo("/post/1/comments")
    }

    @Test
    fun getCommentsManagesHttpError() {
        enqueueMockResponse(403, "comments.json")
        val testObserver = TestObserver<ApiResult>()
        val result = ApiResult.Failure(NetworkResultCode.FORBIDDEN)

        useCase.execute(1).subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(result)
    }
}
package com.paradigmadigital.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.paradigmadigital.domain.db.AuthorDao
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.mappers.AuthorMapper
import com.paradigmadigital.domain.mappers.PostMapper
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.DataRepository
import com.paradigmadigital.repository.toApiResult
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.regex.Pattern

class RefreshPostsUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: RefreshPostsUseCase
    @Mock lateinit var postDao: PostDao
    @Mock lateinit var authorDao: AuthorDao

    val postsEndpoint = Pattern.compile("/posts")
    val usersEndpoint = Pattern.compile("/users/.*")

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        val repository = DataRepository(postDao, authorDao, retrofit, AuthorMapper(), PostMapper())
        useCase = RefreshPostsUseCase(repository)
    }

    @Test
    fun getPostsHappyPath() {
        setDispatcher(getDispatcher())
        whenever(authorDao.getAuthor(any())).thenReturn(Single.error(Exception()))
        val testObserver = TestObserver<ApiResult>()
        val posts = arrayListOf(Post(
                id = 1,
                userId = 1,
                title = "tittle",
                body = "body"
        )).toApiResult()

        useCase.execute().subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(posts)
    }

    @Test
    fun getPostsUsesCorrectUrl() {
        setDispatcher(getDispatcher())
        whenever(authorDao.getAuthor(any())).thenReturn(Single.error(Exception()))

        useCase.execute().subscribe()

        assertGetRequestSentTo("/posts")
        assertGetRequestSentTo("/users/1")
    }

    @Test
    fun getPostsManagesHttpError() {
        whenever(authorDao.getAuthor(any())).thenReturn(Single.error(Exception()))
        enqueueMockResponse(500, "posts.json")
        val testObserver = TestObserver<ApiResult>()
        val result = ApiResult.Failure("500")

        useCase.execute().subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(result)
    }

    private fun getDispatcher(): Dispatcher {
        return object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (postsEndpoint.matcher(request.path).matches()) return getResponse(fileName = "posts.json")
                if (usersEndpoint.matcher(request.path).matches()) return getResponse(fileName = "users.json")
                throw IllegalStateException("no mock set up for " + request.path)
            }
        }
    }


}

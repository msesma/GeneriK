package com.paradigmadigital.usecases

import com.paradigmadigital.domain.db.AuthorDao
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.domain.mappers.AuthorMapper
import com.paradigmadigital.domain.mappers.PostMapper
import com.paradigmadigital.repository.DataRepository
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class GetPostsUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: GetPostsUseCase
    @Mock lateinit var postDao: PostDao
    @Mock lateinit var authorDao: AuthorDao

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        MockitoAnnotations.initMocks(this)
        val repository = DataRepository(postDao, authorDao, retrofit, AuthorMapper(), PostMapper())
        super.setUp()
        useCase = GetPostsUseCase(repository)
    }

    @Test
    fun getPostsHappyPath() {
        enqueueMockResponse(200, "posts.json")
        enqueueMockResponse(200, "users.json")
        val testSubscriber = TestSubscriber<List<PostUiModel>>()
        val posts = arrayListOf(PostUiModel(
                id = 1,
                title = "tittle",
                body = "body",
                name = "name",
                email = "email"
        ))

        useCase.execute().subscribe(testSubscriber)
        testSubscriber.await()

        testSubscriber.assertNoErrors()
                .assertValue(posts)
    }

    @Test
    fun getPostsUsesCorrectUrl() {
        enqueueMockResponse(200, "posts.json")
        enqueueMockResponse(200, "users.json")

        useCase.execute().subscribe()

        assertGetRequestSentTo("/posts")
        assertGetRequestSentTo("/users")
    }

    @Test
    fun getPostsManagesHttpError() {
        enqueueMockResponse(500, "posts.json")
        enqueueMockResponse(500, "users.json")
        val subscriber = TestSubscriber<List<PostUiModel>>()

        useCase.execute().subscribe(subscriber)
        subscriber.await()

        subscriber.assertError { it -> (it as HttpException).code() == 500 }
    }
}
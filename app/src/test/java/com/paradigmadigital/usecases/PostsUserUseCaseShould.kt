package com.paradigmadigital.usecases

import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.mappers.PostsUserMapper
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class PostsUserUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: PostsUserUseCase

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        useCase = PostsUserUseCase(retrofit, PostsUserMapper())
    }

    @Test
    fun getPostsHappyPath() {
        enqueueMockResponse(200, "posts.json")
        enqueueMockResponse(200, "users.json")
        val testObserver = TestObserver<List<Post>>()
        val posts = arrayListOf(Post(
                id = 1,
                title = "tittle",
                body = "body",
                name = "name",
                email = "email"
        ))

        useCase.execute().subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
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
        val observer = TestObserver<List<Post>>()

        useCase.execute().subscribe(observer)
        observer.await()

        observer.assertError { it -> (it as HttpException).code() == 500 }
    }
}

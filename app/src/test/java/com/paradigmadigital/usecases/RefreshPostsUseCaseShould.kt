package com.paradigmadigital.usecases

import com.paradigmadigital.domain.db.AuthorDao
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.mappers.AuthorMapper
import com.paradigmadigital.domain.mappers.PostMapper
import com.paradigmadigital.repository.DataRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RefreshPostsUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: RefreshPostsUseCase
    @Mock lateinit var postDao: PostDao
    @Mock lateinit var authorDao: AuthorDao

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        MockitoAnnotations.initMocks(this)
        val repository = DataRepository(postDao, authorDao, retrofit, AuthorMapper(), PostMapper())
        super.setUp()
        useCase = RefreshPostsUseCase(repository)
    }

//    @Test
//    fun getPostsHappyPath() {
//        enqueueMockResponse(200, "posts.json")
//        enqueueMockResponse(200, "users.json")
//        val testObserver = TestObserver<List<Post>>()
//        val posts = arrayListOf(Post(
//                id = 1,
//                title = "tittle",
//                body = "body",
//                name = "name",
//                email = "email"
//        ))
//
//        useCase.execute().subscribe(testObserver)
//        testObserver.await()
//
//        testObserver.assertNoErrors()
//                .assertValue(posts)
//    }

    @Test
    fun getPostsUsesCorrectUrl() {
        enqueueMockResponse(200, "posts.json")
        enqueueMockResponse(200, "users.json")

        useCase.execute().subscribe()

        assertGetRequestSentTo("/posts")
        assertGetRequestSentTo("/users")
    }

//    @Test
//    fun getPostsManagesHttpError() {
//        enqueueMockResponse(500, "posts.json")
//        enqueueMockResponse(500, "users.json")
//        val observer = TestObserver<List<Post>>()
//
//        useCase.execute().subscribe(observer)
//        observer.await()
//
//        observer.assertError { it -> (it as HttpException).code() == 500 }
//    }
}

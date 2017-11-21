package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.Comment
import com.paradigmadigital.domain.db.AuthorDao
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.mappers.AuthorMapper
import com.paradigmadigital.domain.mappers.PostMapper
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.DataRepository
import com.paradigmadigital.repository.toApiResult
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
        enqueueMockResponse(500, "comments.json")
        val testObserver = TestObserver<ApiResult>()
        val result = ApiResult.Failure("500")

        useCase.execute(1).subscribe(testObserver)
        testObserver.await()

        testObserver.assertNoErrors()
                .assertValue(result)
    }
}
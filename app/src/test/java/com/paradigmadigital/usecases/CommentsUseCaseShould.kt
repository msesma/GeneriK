package com.paradigmadigital.usecases

import com.paradigmadigital.domain.mappers.CommentsMapper
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException


class CommentsUseCaseShould : MockWebServerTestBase() {

    lateinit private var useCase: CommentsUseCase

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        useCase = CommentsUseCase(retrofit, CommentsMapper())
    }

    @Test
    fun getCommentsHappyPath() {
        enqueueMockResponse(200, "comments.json")
        val observer = TestObserver<Int>()

        useCase.execute(1).subscribe(observer)
        observer.await()

        observer.assertNoErrors()
                .assertValue(2)
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
        val observer = TestObserver<Int>()

        useCase.execute(1).subscribe(observer)
        observer.await()

        observer.assertError { it -> (it as HttpException).code() == 500 }
    }
}
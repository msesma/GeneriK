package com.paradigmadigital.ui.detail

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.usecases.CommentsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class DetailPresenterShould {

    @Mock private lateinit var commentsUseCase: CommentsUseCase
    @Mock private lateinit var decorator: DetailUserInterface

    private lateinit var presenter: DetailPresenter
    private val single = Single.just(2)
    private val post = Post(id = 3)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(commentsUseCase)
        doReturn(single).whenever(commentsUseCase).execute(any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, post)

        verify(decorator).initialize(post)
    }

    @Test
    fun callUseCaseWhenInitialized() {

        presenter.initialize(decorator, post)

        verify(commentsUseCase).execute(3)
    }
}
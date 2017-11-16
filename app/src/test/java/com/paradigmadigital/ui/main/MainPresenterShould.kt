package com.paradigmadigital.ui.main

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.PostsUserUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class MainPresenterShould {

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var postsUserUseCase: PostsUserUseCase
    @Mock private lateinit var decorator: MainUserInterface

    private val delegateCaptor = argumentCaptor<MainUserInterface.Delegate>()
    private lateinit var presenter: MainPresenter
    private val single = Single.just<List<Post>>(listOf(Post()))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(navigator, postsUserUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture())
        doReturn(single).whenever(postsUserUseCase).execute()
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator)

        verify(decorator).initialize(any())
    }

    @Test
    fun callUseCaseWhenInitialized() {

        presenter.initialize(decorator)

        verify(postsUserUseCase).execute()
    }

    @Test
    fun callUseCaseOnRefresh() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onRefresh()

        verify(postsUserUseCase, times(2)).execute()
    }

    @Test
    fun navigateToDetailOnClick() {
        presenter.initialize(decorator)
        val post = Post()

        delegateCaptor.firstValue.onClick(post)

        verify(navigator).navigateToDetail(post)
    }
}

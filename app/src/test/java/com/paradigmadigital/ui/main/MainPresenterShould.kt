package com.paradigmadigital.ui.main

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.GetPostsUseCase
import com.paradigmadigital.usecases.RefreshPostsUseCase
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class MainPresenterShould {

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var refreshPostsUseCase: RefreshPostsUseCase
    @Mock private lateinit var getPostsUseCase: GetPostsUseCase
    @Mock private lateinit var decorator: MainUserInterface
    @Mock private lateinit var flowable: Flowable<List<PostUiModel>>

    private val delegateCaptor = argumentCaptor<MainUserInterface.Delegate>()
    private lateinit var presenter: MainPresenter
    private val single = Single.just<List<Post>>(listOf(Post()))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(navigator, refreshPostsUseCase, getPostsUseCase)
        doNothing().whenever(decorator).initialize(delegateCaptor.capture())
        doReturn(single).whenever(refreshPostsUseCase).execute()

        whenever(getPostsUseCase.execute()).thenReturn(flowable)
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator)

        verify(decorator).initialize(any())
    }

    @Test
    fun callUseCaseWhenInitialized() {

        presenter.initialize(decorator)

        verify(refreshPostsUseCase).execute()
    }

    @Test
    fun callUseCaseOnRefresh() {
        presenter.initialize(decorator)

        delegateCaptor.firstValue.onRefresh()

        verify(refreshPostsUseCase, times(2)).execute()
    }

    @Test
    fun navigateToDetailOnClick() {
        presenter.initialize(decorator)
        val post = PostUiModel()

        delegateCaptor.firstValue.onClick(post)

        verify(navigator).navigateToDetail(post)
    }
}

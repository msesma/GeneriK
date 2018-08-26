package eu.sesma.generik.ui.detail

import com.nhaarman.mockito_kotlin.*
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.usecases.CommentsUseCase
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
    private val post = PostUiModel(id = 3)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(commentsUseCase)
        doReturn(single).whenever(commentsUseCase).execute(any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, post)

        verify(decorator).initialize(any(), eq(post))
    }

    @Test
    fun callUseCaseWhenInitialized() {

        presenter.initialize(decorator, post)

        verify(commentsUseCase).execute(3)
    }
}
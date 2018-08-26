package eu.sesma.generik.ui.detail

import eu.sesma.generik.api.model.Comment
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.usecases.CommentsUseCase
import javax.inject.Inject

class DetailPresenter
@Inject
constructor(
        private val commentsUseCase: CommentsUseCase
) {

    private var decorator: DetailUserInterface? = null
    private var id: Int = 0

    private val delegate = object : DetailUserInterface.Delegate {
        override fun onRefresh() = getComments(id)
    }


    fun initialize(decorator: DetailUserInterface, post: PostUiModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, post)
        id = post.id
        getComments(id)
    }

    fun dispose() {
        decorator = null
    }

    private fun getComments(postId: Int) {
        commentsUseCase.execute(postId)
                .subscribe({ handleResult(it) }, { throw it })
    }

    private fun handleResult(result: ApiResult) {
        when (result) {
            is ApiResult.Success<*> -> decorator?.showComments(result.data as List<Comment>)
            is ApiResult.Failure -> decorator?.showError(result.data)
        }
    }
}

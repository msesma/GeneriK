package com.paradigmadigital.ui.detail

import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.usecases.CommentsUseCase
import javax.inject.Inject

class DetailPresenter
@Inject
constructor(
        private val commentsUseCase: CommentsUseCase
) {

    private var decorator: DetailUserInterface? = null


    fun initialize(decorator: DetailUserInterface, post: Post) {
        this.decorator = decorator
        this.decorator?.initialize(post)
        getNumberOfComments(post.id)
    }

    fun dispose() {
        decorator = null
    }

    private fun getNumberOfComments(postId: Int) {
        commentsUseCase.execute(postId)
                .subscribe(
                        { decorator?.setComments(it) },
                        { onError(it as Exception) }
                )
    }

    private fun onError(ex: Exception) {
        decorator?.showError(ex)
    }
}

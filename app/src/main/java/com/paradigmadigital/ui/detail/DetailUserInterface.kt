package com.paradigmadigital.ui.detail

import com.paradigmadigital.api.model.Comment
import com.paradigmadigital.domain.entities.PostUiModel

interface DetailUserInterface {

    fun initialize(delegate: Delegate, post: PostUiModel)

    fun showComments(comments: List<Comment>)

    fun showError(error: Exception)

    interface Delegate {

        fun onRefresh()

    }
}

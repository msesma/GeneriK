package com.paradigmadigital.ui.detail

import com.paradigmadigital.api.model.Comment
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.repository.NetworkResultCode

interface DetailUserInterface {

    fun initialize(delegate: Delegate, post: PostUiModel)

    fun showComments(comments: List<Comment>)

    fun showError(error: NetworkResultCode)

    interface Delegate {

        fun onRefresh()

    }
}

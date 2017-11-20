package com.paradigmadigital.ui.detail

import com.paradigmadigital.domain.entities.PostUiModel

interface DetailUserInterface {

    fun initialize(post: PostUiModel)

    fun setComments(numComments: Int)

    fun showError(error: Exception)
}

package com.paradigmadigital.ui.detail

import com.paradigmadigital.domain.entities.Post

interface DetailUserInterface {

    fun initialize(post: Post)

    fun setComments(numComments: Int)

    fun showError(error: Exception)
}

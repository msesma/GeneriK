package com.paradigmadigital.ui.main

import com.paradigmadigital.domain.entities.PostUiModel

interface MainUserInterface {

    fun initialize(delegate: Delegate)

    fun showError(error: Exception)

    fun showPosts(posts: List<PostUiModel>)

    interface Delegate {

        fun onClick(post: PostUiModel)

        fun onRefresh()

    }
}

package com.paradigmadigital.ui.main

import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.repository.NetworkResultCode

interface MainUserInterface {

    fun initialize(delegate: Delegate)

    fun showError(error: NetworkResultCode)

    fun showPosts(posts: List<PostUiModel>)

    interface Delegate {

        fun onClick(post: PostUiModel)

        fun onRefresh()

    }
}

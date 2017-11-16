package com.paradigmadigital.ui.main

import com.paradigmadigital.domain.entities.Post

interface MainUserInterface {

    fun initialize(delegate: Delegate)

    fun showError(error: Exception)

    fun showPosts(posts: List<Post>)

    interface Delegate {

        fun onClick(post: Post)

        fun onRefresh()

    }
}

package com.paradigmadigital.ui.main

import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.PostsUserUseCase
import javax.inject.Inject

class MainPresenter
@Inject constructor(
        private val navigator: Navigator,
        private val postsUserUseCase: PostsUserUseCase
) {

    private var decorator: MainUserInterface? = null

    private val delegate = object : MainUserInterface.Delegate {
        override fun onRefresh() = refresh()

        override fun onClick(post: Post) = navigator.navigateToDetail(post)
    }

    fun initialize(decorator: MainUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
        refresh()
    }

    fun resume() = refresh()

    fun dispose() {
        this.decorator = null
    }

    private fun refresh() {
        postsUserUseCase.execute().subscribe(
                { decorator?.showPosts(it) },
                { decorator?.showError(it as Exception) }
        )
    }
}

package com.paradigmadigital.ui.main

import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.usecases.GetPostsUseCase
import com.paradigmadigital.usecases.RefreshPostsUseCase
import javax.inject.Inject

class MainPresenter
@Inject constructor(
        private val navigator: Navigator,
        private val refreshPostsUseCase: RefreshPostsUseCase,
        private val getPostsUseCase: GetPostsUseCase
) {

    private var decorator: MainUserInterface? = null

    private val delegate = object : MainUserInterface.Delegate {
        override fun onRefresh() = refresh()

        override fun onClick(post: PostUiModel) = navigator.navigateToDetail(post)
    }

    fun initialize(decorator: MainUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
        subscribeToPosts()
        refresh()
    }

    fun resume() = refresh()

    fun dispose() {
        this.decorator = null
    }

    private fun subscribeToPosts() {
        getPostsUseCase.execute().subscribe({ decorator?.showPosts(it) }, { throw it })
    }

    private fun refresh() {
        refreshPostsUseCase.execute().subscribe({ handleResult(it) }, { throw it })
    }

    private fun handleResult(result: ApiResult) {
        if (result is ApiResult.Failure) decorator?.showError(RuntimeException(result.data))
    }
}

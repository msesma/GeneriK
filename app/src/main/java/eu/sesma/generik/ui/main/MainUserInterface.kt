package eu.sesma.generik.ui.main

import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.repository.NetworkResultCode

interface MainUserInterface {

    fun initialize(delegate: Delegate)

    fun showError(error: NetworkResultCode)

    fun showPosts(posts: List<PostUiModel>)

    interface Delegate {

        fun onClick(post: PostUiModel)

        fun onRefresh()

    }
}

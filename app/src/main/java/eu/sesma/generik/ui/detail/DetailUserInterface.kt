package eu.sesma.generik.ui.detail

import eu.sesma.generik.api.model.Comment
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.repository.NetworkResultCode

interface DetailUserInterface {

    fun initialize(delegate: Delegate, post: PostUiModel)

    fun showComments(comments: List<Comment>)

    fun showError(error: NetworkResultCode)

    interface Delegate {

        fun onRefresh()

    }
}

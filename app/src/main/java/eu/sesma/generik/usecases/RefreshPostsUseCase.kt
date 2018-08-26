package eu.sesma.generik.usecases

import eu.sesma.generik.domain.entities.Post
import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.DataRepository
import io.reactivex.Single
import javax.inject.Inject

class RefreshPostsUseCase
@Inject
constructor(
        private val repository: DataRepository
) {
    fun execute(): Single<ApiResult> {
        return repository.refreshPosts()
                .doOnSuccess { handleSuccess(it) }
    }

    private fun handleSuccess(result: ApiResult?) {
        when (result) {
            is ApiResult.Success<*> -> repository.savePosts(result.data as List<Post>)
            is ApiResult.Failure -> Unit
        }
    }
}
package eu.sesma.generik.usecases

import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.repository.DataRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetPostsUseCase
@Inject
constructor(
        private val repository: DataRepository
) {
    fun execute(): Flowable<List<PostUiModel>> {
        return repository.getPosts()
    }
}
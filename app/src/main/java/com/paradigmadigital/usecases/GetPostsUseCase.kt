package com.paradigmadigital.usecases

import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.repository.DataRepository
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
package eu.sesma.generik.usecases

import eu.sesma.generik.repository.ApiResult
import eu.sesma.generik.repository.DataRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CommentsUseCase
@Inject
constructor(
        val repository: DataRepository
) {
    fun execute(postId: Int): Single<ApiResult> = repository.getComments(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
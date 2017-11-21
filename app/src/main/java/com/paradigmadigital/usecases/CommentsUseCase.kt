package com.paradigmadigital.usecases

import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.repository.DataRepository
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
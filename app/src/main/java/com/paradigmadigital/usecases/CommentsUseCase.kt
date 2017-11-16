package com.paradigmadigital.usecases

import com.paradigmadigital.api.services.TypicodeService
import com.paradigmadigital.domain.mappers.CommentsMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class CommentsUseCase
@Inject
constructor(
        @Named("authenticated") retrofit: Retrofit,
        private val mapper: CommentsMapper
) {
    val service = retrofit.create(TypicodeService::class.java)

    fun execute(postId: Int): Single<Int> = service.getComments(postId)
            .map { mapper.map(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
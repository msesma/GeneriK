package com.paradigmadigital.usecases

import com.paradigmadigital.api.model.PostData
import com.paradigmadigital.api.model.UserData
import com.paradigmadigital.api.services.TypicodeService
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.mappers.PostsUserMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class PostsUserUseCase
@Inject
constructor(
        @Named("authenticated") retrofit: Retrofit,
        private val mapper: PostsUserMapper
) {
    val service = retrofit.create(TypicodeService::class.java)

    fun execute(): Single<List<Post>> {
        val obsPost: Single<List<PostData>> = service.getPosts()
        val obsUser: Single<List<UserData>> = service.getUsers()
        val zipper = BiFunction<List<PostData>, List<UserData>, List<Post>> { posts, users -> mapper.apply(posts, users) }

        return obsPost.zipWith(obsUser, zipper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
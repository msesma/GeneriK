package com.paradigmadigital.repository

import com.paradigmadigital.api.model.PostData
import com.paradigmadigital.api.model.UserData
import com.paradigmadigital.api.services.TypicodeService
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.mappers.PostsUserMapper
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class DataRepository
@Inject
constructor(
        private val postDao: PostDao,
        @Named("authenticated") retrofit: Retrofit,
        private val mapper: PostsUserMapper
) {

    val service = retrofit.create(TypicodeService::class.java)

    fun getPosts(): Flowable<List<Post>> {
        return postDao.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun refreshPosts(): Single<ApiResult> {
        //TODO move to a pattern where with the list of post we retrieve each user needed from db and if non extant, then from APi, and store users and post separately
        val obsPost: Single<List<PostData>> = service.getPosts()
        val obsUser: Single<List<UserData>> = service.getUsers()
        val zipper = BiFunction<List<PostData>, List<UserData>, List<Post>> { posts, users ->
            mapper.apply(posts, users)
        }

        return obsPost.zipWith(obsUser, zipper)
                .map { it.toApiResult() as ApiResult }
                .onErrorReturn { it.toApiResult() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun savePosts(posts: List<Post>) {
        postDao.insert(posts)
    }
}
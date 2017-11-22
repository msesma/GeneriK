package com.paradigmadigital.repository

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.api.model.PostData
import com.paradigmadigital.api.services.AuthenticatedService
import com.paradigmadigital.domain.db.AuthorDao
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.domain.mappers.AuthorMapper
import com.paradigmadigital.domain.mappers.PostMapper
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class DataRepository
@Inject
constructor(
        private val postDao: PostDao,
        private val authorDao: AuthorDao,
        @Named("authenticated") retrofit: Retrofit,
        private val authorMapper: AuthorMapper,
        private val postMapper: PostMapper
) {

    val service = retrofit.create(AuthenticatedService::class.java)

    fun update(user: Login): Single<ApiResult> {
        return service.update(user)
                .map { it.toApiResult() as ApiResult }
                .onErrorReturn { it.toApiResult() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPosts(): Flowable<List<PostUiModel>> {
        return postDao.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun refreshPosts(): Single<ApiResult> {

        return service.getPosts()
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map { postData: PostData ->
                    authorDao.getAuthor(postData.userId ?: 0)
                            .subscribe({}, {
                                service.getUser(postData.userId ?: 0)
                                        .subscribe({
                                            authorDao.insert(authorMapper.map(it))
                                        }, {})
                            })
                    postData
                }
                .map { postMapper.map(it) }
                .toList()
                .map { it.toApiResult() as ApiResult }
                .onErrorReturn { it.toApiResult() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun getComments(postId: Int): Single<ApiResult> {

        return service.getComments(postId)
                .map { it.toApiResult() as ApiResult }
                .onErrorReturn { it.toApiResult() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun savePosts(posts: List<Post>) {
        postDao.insert(posts)
    }
}
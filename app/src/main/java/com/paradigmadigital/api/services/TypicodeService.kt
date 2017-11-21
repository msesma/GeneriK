package com.paradigmadigital.api.services


import com.paradigmadigital.api.model.Comment
import com.paradigmadigital.api.model.PostData
import com.paradigmadigital.api.model.UserData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TypicodeService {

    @GET("posts")
    fun getPosts(): Single<List<PostData>>

    @GET("users")
    fun getUsers(): Single<List<UserData>>

    @GET("users/{id}")
    fun getUser(@Path("id") userId: Int): Single<UserData>

    @GET("post/{id}/comments")
    fun getComments(@Path("id") commentId: Int): Single<List<Comment>>
}
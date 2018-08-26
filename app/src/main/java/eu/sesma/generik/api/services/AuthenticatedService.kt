package eu.sesma.generik.api.services


import eu.sesma.generik.api.model.Comment
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.api.model.PostData
import eu.sesma.generik.api.model.UserData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticatedService {

    @GET("posts")
    fun getPosts(): Single<List<PostData>>

    @GET("users")
    fun getUsers(): Single<List<UserData>>

    @GET("users/{id}")
    fun getUser(@Path("id") userId: Int): Single<UserData>

    @GET("post/{id}/comments")
    fun getComments(@Path("id") commentId: Int): Single<List<Comment>>

    @POST("update")
    fun update(@Body user: Login): Single<Login>
}
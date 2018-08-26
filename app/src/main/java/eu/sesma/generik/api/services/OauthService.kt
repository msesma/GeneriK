package eu.sesma.generik.api.services

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface OauthService {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun getRefreshAccessToken(
            @Field("email") email: String,
            @Field("old_token") old_token: String
    ): Call<String>
}
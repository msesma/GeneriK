package com.paradigmadigital.api.services

import com.paradigmadigital.api.model.Code
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginRegisterService {

//    @POST("login")
//    fun login(@Field("user") user: String, @Field("password") password: String): Call<LoginResponse>

    @GET("logout")
    fun logout(@Query("email") email: String)

    @GET("requestCode")
    fun requestCode(@Query("email") email: String): Call<Code>

}



package com.paradigmadigital.api

import com.paradigmadigital.api.clients.AuthHttpClient
import com.paradigmadigital.api.clients.NonAuthHttpClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
class ApiModule() {

    companion object {
        private val ENDPOINT = "https://acme.com"
    }


    @Provides
    @Named("non-authenticated")
    fun ProvideNonAuthRetrofit(client: NonAuthHttpClient): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build()
    }

    @Provides
    @Named("authenticated")
    fun ProvideAuthRetrofit(client: AuthHttpClient): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build()
    }
}
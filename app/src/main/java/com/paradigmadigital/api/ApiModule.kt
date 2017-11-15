package com.paradigmadigital.api

import com.paradigmadigital.api.clients.AuthHttpClientFactory
import com.paradigmadigital.api.clients.NonAuthHttpClientFactory
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
    fun ProvideNonAuthRetrofit(clientFactory: NonAuthHttpClientFactory): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(clientFactory.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build()
    }

    @Provides
    @Named("authenticated")
    fun ProvideAuthRetrofit(clientFactory: AuthHttpClientFactory): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(clientFactory.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build()
    }
}
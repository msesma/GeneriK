package com.paradigmadigital.api

import android.content.Context
import com.paradigmadigital.api.clients.AuthHttpClientFactory
import com.paradigmadigital.api.clients.NonAuthHttpClientFactory
import com.squareup.picasso.Cache
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule() {

    private val LOGIN_URL = "https://acme.com"
    var DATA_URL: String = "http://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideCache(application: Context): Cache {
        return LruCache(application)
    }

    @Provides
    @Singleton
    fun providePicasso(cache: Cache, application: Context): Picasso {
        val builder = Picasso.Builder(application)
        builder.memoryCache(cache)
        return builder.build()
    }

    @Provides
    @Named("non-authenticated")
    fun ProvideNonAuthRetrofit(clientFactory: NonAuthHttpClientFactory): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(clientFactory.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(LOGIN_URL)
                .build()
    }

    @Provides
    @Named("authenticated")
    fun ProvideAuthRetrofit(clientFactory: AuthHttpClientFactory): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(clientFactory.getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(DATA_URL)
                .build()
    }
}
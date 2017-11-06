package com.paradigmadigital.api

import android.content.Context
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.paradigmadigital.BuildConfig
import com.paradigmadigital.platform.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


@Module
class ApiModule() {

    companion object {
        private val CACHE_SIZE: Long = 10 * 1024 * 1024
        private val CACHE_TIMESEC = 30
        private val ENDPOINT = "https://acme.com"
    }


    val cacheInterceptor: Interceptor
        get() = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                    .maxAge(CACHE_TIMESEC, TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("Retrofit", message) })
        interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
            context: Context,
            loggingInterceptor: HttpLoggingInterceptor,
            dummyInterceptor: DummyInterceptor
    ): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)
        val builder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .cache(cache)
        if (BuildConfig.DEBUG && Constants.DUMMY_ENABLED) builder.addInterceptor(dummyInterceptor)
        return builder.build()
    }

    @Provides
    fun ProvideRetrofit(client: OkHttpClient): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build()
    }
}
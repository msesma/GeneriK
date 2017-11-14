package com.paradigmadigital.api.clients

import android.content.Context
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.paradigmadigital.BuildConfig
import com.paradigmadigital.api.DummyInterceptor
import com.paradigmadigital.platform.Constants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import java.io.File
import java.util.concurrent.TimeUnit


abstract class OkHttpClientBase
constructor(
        context: Context,
        val dummyInterceptor: DummyInterceptor
) : OkHttpClient() {

    companion object {
        private val CACHE_SIZE: Long = 10 * 1024 * 1024
        private val CACHE_TIMESEC = 30
    }

    private val cacheInterceptor: Interceptor
        get() = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                    .maxAge(CACHE_TIMESEC, TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }


    private val cache = Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)

    private val builder = OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor(cacheInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .cache(cache)

    abstract fun getClient(): OkHttpClient

    protected fun getBuilder(): Builder {
        if (BuildConfig.DEBUG && Constants.DUMMY_ENABLED) builder.addInterceptor(dummyInterceptor)
        return builder
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.d("Retrofit", message)
        })
        interceptor.level = if (BuildConfig.DEBUG) BODY else NONE
        return interceptor
    }
}
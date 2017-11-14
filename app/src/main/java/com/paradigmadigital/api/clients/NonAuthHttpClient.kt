package com.paradigmadigital.api.clients

import android.content.Context
import com.paradigmadigital.api.DummyInterceptor
import javax.inject.Inject


class NonAuthHttpClient
@Inject constructor(
        context: Context,
        dummyInterceptor: DummyInterceptor
) : OkHttpClientBase(context, dummyInterceptor) {

    override fun getClient() = getBuilder().build()

}
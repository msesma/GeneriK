package com.paradigmadigital.api.clients

import android.content.Context
import com.paradigmadigital.api.DummyInterceptor
import javax.inject.Inject


class NonAuthHttpClientFactory
@Inject constructor(
        context: Context,
        dummyInterceptor: DummyInterceptor
) : OkHttpClientFactoryBase(context, dummyInterceptor) {

    override fun getClient() = getBuilder().build()

}
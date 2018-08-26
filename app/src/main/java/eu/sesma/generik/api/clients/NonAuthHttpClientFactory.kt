package eu.sesma.generik.api.clients

import android.content.Context
import eu.sesma.generik.api.DummyInterceptor
import javax.inject.Inject


class NonAuthHttpClientFactory
@Inject constructor(
        context: Context,
        dummyInterceptor: DummyInterceptor
) : OkHttpClientFactoryBase(context, dummyInterceptor) {

    override fun getClient() = getBuilder().build()

}
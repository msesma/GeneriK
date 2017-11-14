package com.paradigmadigital.api.clients

import android.content.Context
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.api.DummyInterceptor
import com.paradigmadigital.api.services.OauthService
import okhttp3.*
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class AuthHttpClient
@Inject constructor(
        context: Context,
        dummyInterceptor: DummyInterceptor,
        val accountManager: OauthAccountManager,
        @Named("non-authenticated") val retrofit: Retrofit
) : OkHttpClientBase(context, dummyInterceptor) {

    val authInterceptor
        get() = Interceptor {
            val original = it.request()
            val token = accountManager.getAuthToken()

            val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .header("Authorization", token)
            val request = requestBuilder.build()
            it.proceed(request)
        }

    val authenticator = object : Authenticator {
        override fun authenticate(route: Route, response: Response): Request? {
            if (responseCount(response) >= 2) {
                return null;
            }

            val service: OauthService = retrofit.create(OauthService::class.java)
            val call = service.getRefreshAccessToken(accountManager.getEmail(), accountManager.getAuthToken())
            val refreshResponse = call.execute()
            if (!refreshResponse.isSuccessful) return null

            val newToken = refreshResponse.body() ?: ""
            accountManager.refreshToken(newToken)

            return response.request().newBuilder()
                    .header("Authorization", newToken)
                    .build();
        }
    }

    override fun getClient(): OkHttpClient {
        return getBuilder()
                .addInterceptor(authInterceptor)
                .authenticator(authenticator)
                .build()
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var responseCopy = response.priorResponse()
        while (responseCopy != null) {
            result++
            responseCopy = responseCopy.priorResponse()
        }
        return result
    }
}
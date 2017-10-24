package com.paradigmadigital.api

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject


class DummyInterceptor
@Inject constructor(
        val context: Context
) : Interceptor {
    companion object {
        private val TAG = DummyInterceptor::class.java.simpleName
        private val FILE_EXTENSION = ".json"
    }

    private var contentType = "application/json"

    fun setContentType(contentType: String): DummyInterceptor {
        this.contentType = contentType
        return this
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val method = chain.request().method().toLowerCase()

        var response: Response?
        val uri = chain.request().url().uri()
        Log.d(TAG, "--> Request url: [" + method.toUpperCase() + "]" + uri.toString())

        val defaultFileName = getFileName(chain)


        Log.d(TAG, "Read data from file: " + defaultFileName)
        try {
            val inputStream = context.assets.open(defaultFileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val responseStringBuilder = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                responseStringBuilder.append(line).append('\n')
                line = reader.readLine()
            }

            Log.d(TAG, "Response: " + responseStringBuilder.toString())
            response = Response.Builder()
                    .code(200)
                    .message(responseStringBuilder.toString())
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse(contentType), responseStringBuilder.toString().toByteArray()))
                    .addHeader("content-type", contentType)
                    .build()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            response = chain.proceed(chain.request())
        }


        Log.d(TAG, "<-- END [" + method.toUpperCase() + "]" + uri.toString())
        return response
    }

    private fun upCaseFirstLetter(str: String): String {
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }


    private fun getFileName(chain: Interceptor.Chain): String {

        val pathSegments = chain.request().url().pathSegments()

        val fileName = "mocks/" + pathSegments[0]
        return if (fileName.isEmpty()) "index" + FILE_EXTENSION else fileName + FILE_EXTENSION
    }
}

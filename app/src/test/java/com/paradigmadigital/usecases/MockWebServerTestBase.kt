package com.paradigmadigital.usecases

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.apache.commons.io.FileUtils
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException

open class MockWebServerTestBase {

    companion object {
        private val FILE_ENCODING = "UTF-8"
    }

    lateinit private var server: MockWebServer

    private val url = server.url("/").toString()

    private val httpClient: OkHttpClient
        get() = OkHttpClient.Builder().build()


    protected val retrofit
        get() = retrofit2.Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        this.server = MockWebServer()
        this.server.start()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        RxAndroidPlugins.reset()
        server.shutdown()
    }

    @Throws(IOException::class)
    @JvmOverloads protected fun enqueueMockResponse(code: Int = 200, fileName: String? = null) {
        val mockResponse = MockResponse()
        val fileContent = getContentFromFile(fileName)
        mockResponse.setResponseCode(code)
        mockResponse.setBody(fileContent)
        server.enqueue(mockResponse)
    }

    @Throws(InterruptedException::class)
    protected fun assertGetRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
        assertEquals("GET", request.method)
    }

    @Throws(IOException::class)
    protected fun getContentFromFile(name: String?): String {
        var fileName: String? = name ?: return ""

        fileName = javaClass.getResource("/" + fileName).file
        val file = File(fileName!!)
        val lines = FileUtils.readLines(file, FILE_ENCODING)
        val stringBuilder = StringBuilder()
        for (line in lines) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }
}
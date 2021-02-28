package me.toptas.okmockerwriter

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class OkMockerWriterTest {

    @get:Rule
    var server = MockWebServer()


    private val writer = Writer()
    private var client = OkHttpClient.Builder()
        .addInterceptor(OkMockerWriteInterceptor(writer))
        .build()
    private val url = Writer.PATH


    @Test
    fun testWrite() {
        server.enqueue(MockResponse())
        val response = client.newCall(request().build()).execute()

        Assert.assertEquals(writer.storage, "$url\n${response.body?.string()}")

    }

    private fun request(): Request.Builder {
        return Request.Builder().url(url)
    }
}
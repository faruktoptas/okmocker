package me.toptas.okmockerreader

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import org.junit.Assert.*


class OkMockerReaderTest {

    @get:Rule
    var server = MockWebServer()

    private var url = Reader.PATH_1
    private val reader = Reader()
    private var client = OkHttpClient.Builder()
        .addInterceptor(OkMockerReadInterceptor(reader))
        .build()


    @Test
    @Throws(IOException::class)
    fun handledResponse1() {
        url = Reader.PATH_1
        server.enqueue(MockResponse())

        val response = client.newCall(request().build()).execute()
        assertEquals(response.body()?.string(), Reader.RESPONSE_1)
    }

    @Test
    @Throws(IOException::class)
    fun handledResponse2() {
        url = Reader.PATH_2
        server.enqueue(MockResponse())

        val response = client.newCall(request().build()).execute()
        assertEquals(response.body()?.string(), Reader.RESPONSE_2)
    }

    @Test
    @Throws(IOException::class)
    fun unhandledResponse() {
        url = "https://example.com/other"
        server.enqueue(MockResponse())

        val response = client.newCall(request().build()).execute()
        assertEquals(response.body()?.string(), Reader.RESPONSE_OTHER)
    }

    private fun request(): Request.Builder {
        return Request.Builder().url(url)
    }
}
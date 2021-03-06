package me.toptas.okmocker

import android.util.Log
import me.toptas.okmocker.core.toOkMockerFileName
import me.toptas.okmockerreader.OkMockerReader
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class MyReader : OkMockerReader {

    override fun read(chain: Interceptor.Chain): ResponseBody {
        val content = when (chain.request().url.toOkMockerFileName()) {
            "https://PATH" -> "{\"result\":\"some_result\"}"
            else -> "{\"result\":\"result_from_reader\"}"

        }
        Log.i("asd", "content $content")
        return content.toResponseBody("application/json".toMediaTypeOrNull())
    }

    override fun canRead(request: Request) = true // Always return true

}
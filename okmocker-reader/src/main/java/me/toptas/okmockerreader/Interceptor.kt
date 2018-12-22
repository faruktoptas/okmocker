package me.toptas.okmockerreader

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response

class OkMockerReadInterceptor(
    private val context: Context,
    private val reader: OkMockerReader = AssetsReader(context.assets)
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        log("OkMocker enabled")
        return if (reader.canRead(request)) {
            log("OkMocker can read")
            val body = reader.read(chain)
            Response.Builder()
                .code(200)
                .body(body)
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .message("")
                .build()
        } else {
            chain.proceed(request)
        }
    }

}
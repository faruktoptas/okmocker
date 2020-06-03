/*
 * Copyright (c) 2018. Faruk Toptaş
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.toptas.okmockerwriter

import me.toptas.okmocker.core.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.nio.charset.Charset

class OkMockerWriteInterceptor(private val writer: OkMockerWriter = SdCardWriter()) : Interceptor {

    var logger: Logger? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val headers = response.headers()
        val responseBody = response.body()
        val contentLength = responseBody?.contentLength() ?: 0

        val source = responseBody?.source()
        source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source?.buffer()

        if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
            var gzippedResponseBody: GzipSource? = null
            try {
                if (buffer != null) {
                    gzippedResponseBody = GzipSource(buffer.clone())
                }
                buffer = Buffer()
                if (gzippedResponseBody != null) buffer.writeAll(gzippedResponseBody)
            } finally {
                gzippedResponseBody?.close()
            }
        }

        var charset = UTF8
        val contentType = responseBody?.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)
        }

        if (isPlaintext(buffer) && contentLength != 0L) {
            buffer?.clone()?.readString(charset)?.apply {
                logger?.log("OkMocker write -> $this")
                writer.write(request.url(), this)
            }
        }




        return response
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")

        internal fun isPlaintext(buffer: Buffer?): Boolean {
            if (buffer == null) return false
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false
            }

        }
    }

}
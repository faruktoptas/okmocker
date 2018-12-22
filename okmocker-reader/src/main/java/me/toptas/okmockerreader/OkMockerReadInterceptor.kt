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
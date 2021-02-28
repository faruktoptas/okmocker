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

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class Reader : OkMockerReader {

    override fun read(chain: Interceptor.Chain): ResponseBody {
        val content = when (chain.request().url.toString()) {
            PATH_1 -> RESPONSE_1
            PATH_2 -> RESPONSE_2
            else -> RESPONSE_OTHER

        }
        return content.toResponseBody("application/json".toMediaTypeOrNull())
    }

    override fun canRead(request: Request) = true

    companion object {
        const val PATH_1 = "https://example.com/path1"
        const val PATH_2 = "https://example.com/path2"
        const val RESPONSE_1 = "{\"path\":\"path1\"}"
        const val RESPONSE_2 = "{\"path\":\"path2\"}"
        const val RESPONSE_OTHER = "{\"path\":\"other\"}"
    }
}
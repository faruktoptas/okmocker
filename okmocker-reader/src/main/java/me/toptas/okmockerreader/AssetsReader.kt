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

import android.content.res.AssetManager
import me.toptas.okmocker.core.Logger
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import me.toptas.okmocker.core.toOkMockerFileName

class AssetsReader(private val assets: AssetManager) : OkMockerReader {

    var logger: Logger? = null

    private val folder: String = "okmocker"

    override fun canRead(request: Request) =
        fileExists(assets, folder, request.url().toString().toOkMockerFileName())

    override fun read(chain: Interceptor.Chain): ResponseBody {
        val path = chain.request().url().toString().toOkMockerFileName()
        val content = assets.open("$folder/$path").readBytes()

        logger?.log("OkMocker Read from assets: $content")
        return ResponseBody.create(MediaType.parse("application/json"), content)

    }
}

internal fun fileExists(assets: AssetManager, path: String, file: String) =
    assets.list(path)?.contains(file) ?: false

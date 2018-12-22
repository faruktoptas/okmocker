package me.toptas.okmockerreader

import okhttp3.*

interface OkMockerReader {
    fun read(chain: Interceptor.Chain): ResponseBody
    fun canRead(request: Request): Boolean
}
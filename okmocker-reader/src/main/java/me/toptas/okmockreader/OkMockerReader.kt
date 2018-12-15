package me.toptas.okmockreader

import android.content.res.AssetManager
import android.util.Log
import okhttp3.*

interface OkMockerReader {
    fun read(chain: Interceptor.Chain): ResponseBody
    fun canRead(request: Request): Boolean
}

fun String.toFileName() = replace("://", "_").replace("/", "_")

class AssetsReader(private val assets: AssetManager) : OkMockerReader {

    private val folder: String = "okmock"

    override fun canRead(request: Request) = fileExists(assets, folder, request.url().toString().toFileName())

    override fun read(chain: Interceptor.Chain): ResponseBody {
        val path = chain.request().url().toString().toFileName()
        val content = assets.open("$folder/$path").bufferedReader().use { it.readLine() }

        log("Read from assets: $content")
        return ResponseBody.create(MediaType.parse("application/json"), content)

    }
}

internal fun fileExists(assets: AssetManager, path: String, file: String) = assets.list(path)?.contains(file) ?: false

internal fun log(msg: String) {
    Log.i("OkMockerasd", msg)
}
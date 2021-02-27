package me.toptas.okmocker.core

import okhttp3.HttpUrl
import java.lang.IllegalArgumentException

const val DEFAULT_BASE_URL = "https://example.com"

fun HttpUrl.toOkMockerFileName(baseUrl: String = DEFAULT_BASE_URL): String {
    val url = HttpUrl.parse(baseUrl) ?: throw  IllegalArgumentException("Set a valid base url")

    return this.newBuilder()
        .host(url.host())
        .scheme(url.scheme())
        .build()
        .toString()
        .replace(url.toString(), "")
        .toFileName()

}

private fun String.toFileName() = replace("://", "_")
    .replace("/", "_")
    .replace("?", "_")
    .replace("=", "_")
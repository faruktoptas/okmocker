package me.toptas.okmockerwriter


import okhttp3.HttpUrl

interface OkMockerWriter {
    fun write(url: HttpUrl, body: String)
}

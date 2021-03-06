package me.toptas.okmocker

import android.content.Context
import me.toptas.okmocker.core.DefaultLogger
import me.toptas.okmockerwriter.OkMockerWriteInterceptor
import me.toptas.okmockerwriter.SdCardWriter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    lateinit var apiService: ApiService
    var clientBuilder = OkHttpClient.Builder()

    fun init(context: Context) {
        val writer = SdCardWriter(context)
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(OkMockerWriteInterceptor(writer).apply {
                logger = DefaultLogger()
            })
        }

        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        val instance = NetworkManager()
    }
}
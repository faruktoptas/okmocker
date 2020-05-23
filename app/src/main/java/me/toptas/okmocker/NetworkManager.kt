package me.toptas.okmocker

import me.toptas.okmockerwriter.LogCatLogger
import me.toptas.okmockerwriter.OkMockerWriteInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    lateinit var apiService: ApiService
    var clientBuilder = OkHttpClient.Builder()

    fun init() {

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(OkMockerWriteInterceptor().apply {
                logger = LogCatLogger()
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
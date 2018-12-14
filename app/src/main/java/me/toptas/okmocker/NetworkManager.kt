package me.toptas.okmocker

import android.content.Context
import me.toptas.okmockerwriter.OkMockerWriteInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    lateinit var apiService: ApiService
    var clientBuilder = OkHttpClient.Builder()

    fun init() {

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(OkMockerWriteInterceptor())
        }

        val client = clientBuilder
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://okmocker.free.beeceptor.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        val instance = NetworkManager()
    }
}
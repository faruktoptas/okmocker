package me.toptas.okmocker


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("okmocker")
    fun dummy(): Call<Response>
}

data class Response(var result: String?)
package me.toptas.okmocker


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("business")
    fun dummy(): Call<List<Response>>
}

data class Response(var name: String?)
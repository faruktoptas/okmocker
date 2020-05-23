package me.toptas.okmocker


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("faruktoptas/okmocker/master/app/sample.json")
    fun dummy(): Call<Response>
}

data class Response(var result: String?)
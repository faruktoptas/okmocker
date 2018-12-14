package me.toptas.okmocker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkManager.instance.init(applicationContext)

        val service = NetworkManager.instance.apiService

        btn.setOnClickListener {
            service.dummy().enqueue(object : Callback<List<Response>?> {
                override fun onFailure(call: Call<List<Response>?>, t: Throwable) {
                    Log.v("asd", "fail $t")
                }

                override fun onResponse(call: Call<List<Response>?>, response: retrofit2.Response<List<Response>?>) {
                    Log.v("asd", "success")
                    response.body()?.get(0)?.apply {
                        tvText.text = name
                    }
                }
            })
        }
    }
}

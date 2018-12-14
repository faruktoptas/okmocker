package me.toptas.okmocker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

const val TAG = "okmocker"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkManager.instance.init()
        val service = NetworkManager.instance.apiService

        btn.setOnClickListener {
            service.dummy().enqueue(object : Callback<Response?> {
                override fun onFailure(call: Call<Response?>, t: Throwable) {
                    Log.v(TAG, "fail $t")
                }

                override fun onResponse(call: Call<Response?>, response: retrofit2.Response<Response?>) {
                    Log.v(TAG, "success")
                    response.body()?.apply {
                        tvText.text = result
                    }
                }
            })
        }
    }
}

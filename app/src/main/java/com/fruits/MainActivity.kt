package com.fruits

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fruits.repository.remote.FruitsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fruitPresenter = FruitsPresenter()
        val fruitClient = fruitPresenter.fruitClient
        val fruits = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                if (response.isSuccessful) {
                    for (fruit in response.body()!!.fruit){
                        println("fruit: "+ fruit.type)
                    }
                }
            }
            override fun onFailure(call: Call<FruitsApiResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.toString())
                // Log error here since request failed
            }
        })
    }
}

package com.fruits.repository

import android.util.Log
import com.fruits.FruitsPresenter
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.repository.remote.FruitsClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FruitRepository {

    private val fruitClient by lazy {
        FruitsClient.create()
    }

    fun getFruits(callback: FruitsPresenter){
        val fruits : Call<FruitsApiResponse> = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                when (response.isSuccessful) {
                    true -> callback.onSuccess(response.body()!!.fruit)
                    else -> callback.onError(response.message())
                }
            }
            override fun onFailure(call: Call<FruitsApiResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.toString())
                // Log error here since request failed
                //callback.onError(t.message)
            }
        })
    }

    interface FruitRepositoryCallback {
        fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>)
        fun onError(message: String)
    }

}
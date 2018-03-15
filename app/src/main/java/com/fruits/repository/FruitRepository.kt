package com.fruits.repository

import android.util.Log
import com.fruits.fruits.FruitsPresenter
import com.fruits.tracking.EventTracker
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.repository.remote.FruitsClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FruitRepository {

    val fruitClient by lazy {
        FruitsClient.create()
    }

    fun getFruits(callback: FruitsPresenter){
        val fruits : Call<FruitsApiResponse> = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                when (response.isSuccessful) {
                    true -> {
                        callback.onSuccess(response.body()!!.fruit)
                        sendEventCompleteRequest()
                    }
                    else -> {callback.onError(response.message())
                        sendEventErrorRequest(response.message())
                    }
                }
            }
            override fun onFailure(call: Call<FruitsApiResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.toString())

                callback.onError(t.toString())
                sendEventErrorRequest(t.toString())
            }
        })
    }

    private fun sendEventErrorRequest(errorMessage : String) {
        fruitClient.sentEventError("error", errorMessage)
    }

    fun sendEventCompleteRequest(){
        fruitClient.sentEventLoad("load", EventTracker.timeCompleteRequest.toInt())
    }

    fun sendTrackUserInteractionRequest(userTimeTracked : Double) {
        fruitClient.sentEventDisplay("display", userTimeTracked.toString())
    }

    interface FruitRepositoryCallback {
        fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>)
        fun onError(message: String)
    }

}
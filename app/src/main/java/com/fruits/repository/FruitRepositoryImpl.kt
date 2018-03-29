package com.fruits.repository

import android.util.Log
import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.repository.remote.FruitsClient
import com.fruits.tracking.EventTracker
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FruitRepositoryImpl : FruitRepository{

    private val TAG = FruitRepository::class.java!!.getName()

    val fruitClient by lazy {
        FruitsClient.create()
    }

    override fun getSingleFruits(): Single<FruitsApiResponse> {
        return fruitClient.getSingleFruit()
    }

    override fun trackErrorRequest(errorMessage : String) {
        fruitClient.sentEventError("error", errorMessage)
    }

    override fun trackCompleteRequest(){
        fruitClient.sentEventLoad("load", EventTracker.timeCompleteRequest.toInt())
    }

    override fun trackUserInteractionRequest(userTimeTracked : Double) {
        fruitClient.sentEventDisplay("display", userTimeTracked.toString())
    }

    override fun getFruits(callback: FruitRepository.FruitRepositoryCallback){
        val fruits : Call<FruitsApiResponse> = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                when (response.isSuccessful) {
                    true -> {
                        //val onSuccess = SuccessfulResponseFruitRepository(response.body()!!.fruit)
                        //onSuccess.getFruits()
                        callback.onSuccess(response.body()!!.fruit)
                        trackCompleteRequest()
                    }
                    else -> {
                        callback.onError(response.message())

                        trackErrorRequest(response.message())
                    }
                }
            }
            override fun onFailure(call: Call<FruitsApiResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.toString())

                callback.onError(t.toString())
                trackErrorRequest(t.toString())
            }
        })
    }
}
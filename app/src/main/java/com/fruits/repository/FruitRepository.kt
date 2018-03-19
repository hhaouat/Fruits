package com.fruits.repository

import android.util.Log
import com.fruits.fruits.FruitListFragment
import com.fruits.fruits.FruitsPresenter
import com.fruits.tracking.EventTracker
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.repository.remote.FruitsClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class FruitRepository {

    private val TAG = FruitRepository::class.java!!.getName()

    val fruitClient by lazy {
        FruitsClient.create()
    }

    fun getFruits(callback: FruitRepositoryCallback){
        val fruits : Call<FruitsApiResponse> = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                when (response.isSuccessful) {
                    true -> {
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

    fun getSingleFruits(): Single <FruitsApiResponse> {
        return fruitClient.getSingleFruit().subscribeOn(Schedulers.io())
    }

    private fun trackErrorRequest(errorMessage : String) {
        fruitClient.sentEventError("error", errorMessage)
    }

    fun trackCompleteRequest(){
        fruitClient.sentEventLoad("load", EventTracker.timeCompleteRequest.toInt())
    }

    fun trackUserInteractionRequest(userTimeTracked : Double) {
        fruitClient.sentEventDisplay("display", userTimeTracked.toString())
    }

    interface FruitRepositoryCallback {
        fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>)
        fun onError(message: String)
    }
}
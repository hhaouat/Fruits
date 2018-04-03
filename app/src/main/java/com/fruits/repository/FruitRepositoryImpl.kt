package com.fruits.repository

import android.util.Log
import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.repository.remote.FruitsClient
import com.fruits.tracking.EventTracker
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FruitRepositoryImpl(val fruitClient: FruitsClient) : FruitRepository{

    private val TAG = FruitRepositoryImpl::class.java.getName()

    /**
     * Fetches the fruits remotely and trigger the [FruitsClient] to get the latest
     * fruits.
     *
     * This method uses callbacks in order to trigger the latest fruits
     */
    override fun getFruits(callback: FruitRepository.FruitRepositoryCallback){
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
                Log.e(TAG, "onFailure: " + t.toString())

                callback.onError(t.toString())
                trackErrorRequest(t.toString())
            }
        })
    }

    /**
     * Fetches the fruits remotely and trigger the [FruitsClient] to get the latest
     * fruits.
     * This method is similar to the previous one, except that it uses RX Kotlin and
     * @return [Single]
     *
     * This method is used in OnRefreshAction
     */
    override fun getSingleFruits(): Single<FruitsApiResponse> {
        return fruitClient.getSingleFruitApiResponse()
    }

    /**
     * Send the event "load" when the request is complete
     *
     */
    override fun trackCompleteRequest(){
        fruitClient.sentEventLoad("load", EventTracker.timeCompleteRequest.toInt())
    }

    /**
     * Send the event "error" when an error happen during the request
     *
     */
    override fun trackErrorRequest(errorMessage : String) {
        fruitClient.sentEventError("error", errorMessage)
    }

    /**
     * Send the event "display" when ever a screen is shown
     *
     */
    override fun trackUserInteractionRequest(userTimeTracked : Double) {
        fruitClient.sentEventDisplay("display", userTimeTracked.toString())
    }

}
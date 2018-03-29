package com.fruits.repository

import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import io.reactivex.Single

interface FruitRepository {

    fun getFruits(callback: FruitRepositoryCallback)

    fun getSingleFruits(): Single<FruitsApiResponse>

    fun trackErrorRequest(errorMessage: String) {}

    fun trackCompleteRequest() {}

    fun trackUserInteractionRequest(userTimeTracked: Double) {}

    interface FruitRepositoryCallback {
        fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>)
        fun onError(message: String)
    }

}
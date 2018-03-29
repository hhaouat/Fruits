package com.fruits.repository

import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import io.reactivex.Single

class SuccessfulResponseFruitRepository(val response: List<FruitItemApiResponse>) : FruitRepository {

    override fun getFruits(callback: FruitRepository.FruitRepositoryCallback) {
        callback.onSuccess(response)
    }

    override fun getSingleFruits(): Single<FruitsApiResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
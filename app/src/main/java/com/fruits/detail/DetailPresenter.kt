package com.fruits.detail

import com.fruits.repository.FruitRepositoryImpl
import com.fruits.repository.remote.FruitsClient
import com.fruits.tracking.EventTracker

class DetailPresenter {

    val repository = FruitRepositoryImpl(FruitsClient.create())

    fun trackUserInteractionRequest(){
        val userTimeTracked = EventTracker.get().calculTrackDisplayScreen()
        repository.trackUserInteractionRequest(userTimeTracked)
    }
}
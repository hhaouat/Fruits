package com.fruits.detail

import com.fruits.repository.FruitRepository
import com.fruits.tracking.EventTracker

class DetailPresenter {

    var repository = FruitRepository()

    fun trackUserInteractionRequest(){
        var userTimeTracked = EventTracker.get().calculTrackDisplayScreen()
        repository.trackUserInteractionRequest(userTimeTracked)
    }
}
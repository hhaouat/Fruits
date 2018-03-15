package com.fruits.detail

import com.fruits.repository.FruitRepository
import com.fruits.tracking.EventTracker

class DetailPresenter {

    var repository = FruitRepository()

    fun sendTrackUserInteractionRequest(){
        var userTimeTracked = EventTracker.get().calculTrackDisplayScreen()
        repository.sendTrackUserInteractionRequest(userTimeTracked)
    }
}
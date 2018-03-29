package com.fruits.detail

import com.fruits.repository.FruitRepository
import com.fruits.repository.FruitRepositoryImpl
import com.fruits.tracking.EventTracker
import kotlin.jvm.internal.FunctionReferenceImpl

class DetailPresenter {

    var repository = FruitRepositoryImpl()

    fun trackUserInteractionRequest(){
        var userTimeTracked = EventTracker.get().calculTrackDisplayScreen()
        repository.trackUserInteractionRequest(userTimeTracked)
    }
}
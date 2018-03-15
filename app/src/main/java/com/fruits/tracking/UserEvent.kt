package com.fruits.tracking

interface UserEvent {

    fun calculTrackDisplayScreen() : Double
    fun startTrackDisplayScreen(varStartTrackDisplayScreen: Long)
    fun endTrackDisplayScreen(varEndTrackDisplayScreen: Long)
}
package com.fruits.tracking

class EventTracker : NetworkEvent, UserEvent {

    init {
        instance = this
    }

    companion object {

        var startTrackDisplayScreen : Long = 0L

        var endTrackDisplayScreen : Long = 0L

        var timeCompleteRequest : Double = 0.0

        var instance : EventTracker = EventTracker.get()

        /**
         * Static accessor allowing callers to easily hook into the WebKit Inspector system without
         * creating dependencies on the main Stetho initialization code path.
         */
        @Synchronized
        open fun get() : EventTracker {
            if (instance == null) {
                instance = EventTracker()
            }
            return instance
        }

    }

    override fun startTrackDisplayScreen(paramStartTrackDisplayScreen: Long) {
        startTrackDisplayScreen = paramStartTrackDisplayScreen
    }

    override fun endTrackDisplayScreen(paramEndTrackDisplayScreen: Long) {
        endTrackDisplayScreen = paramEndTrackDisplayScreen
    }

    override fun calculTrackDisplayScreen() = (endTrackDisplayScreen - startTrackDisplayScreen) / 1e6

    @Synchronized
    override fun setTimeCompleteRequest(paramTimeCompleteRequest: Double) {
        timeCompleteRequest = paramTimeCompleteRequest
    }
}
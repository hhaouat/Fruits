package com.fruits.network

class NetworkEventReporter: NetworkEvent {

    init {
        instance = this
    }

    companion object {

        var timeCompleteRequest : Double = 5.0

        var instance : NetworkEvent = NetworkEventReporter.get()

        /**
         * Static accessor allowing callers to easily hook into the WebKit Inspector system without
         * creating dependencies on the main Stetho initialization code path.
         */
        @Synchronized
        open fun get() : NetworkEvent {
            if (instance == null) {
                instance = NetworkEventReporter()
            }
            return instance
        }
        //fun create(): NetworkEventReporter = NetworkEventReporter()

    }
    @Synchronized
    override fun setTimeCompleteRequest(valTimeCompleteRequest : Double) {
        timeCompleteRequest = valTimeCompleteRequest
    }
}
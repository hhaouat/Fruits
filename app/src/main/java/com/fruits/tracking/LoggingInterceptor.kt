package com.fruits.tracking

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class LoggingInterceptor : Interceptor {

    private val eventReporter: NetworkEvent? = EventTracker.get()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        println(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()))

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        completeRequestTime = (t2 - t1) / 1e6
        println(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), completeRequestTime, response.headers()))

        eventReporter!!.setTimeCompleteRequest(completeRequestTime)
        return response
    }

    companion object {
        var completeRequestTime : Double = 0.0
    }
}
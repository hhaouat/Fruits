package com.fruits.util

import android.util.Log

class AndroidLogger() : Logger {

    override fun logError(tag: String, error: String) {
        Log.e(tag, error)
    }

    override fun logInfo(tag: String, info: String) {
        Log.i(tag, info)
    }
}
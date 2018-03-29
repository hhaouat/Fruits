package com.fruits.util

import android.util.Log

class AndroidLogger() : Logger {

    override fun logError(error: String) {
        Log.e("",error)
    }

    override fun logInfo(info: String) {
        Log.i("",info)
    }
}
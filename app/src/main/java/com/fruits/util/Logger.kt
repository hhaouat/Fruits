package com.fruits.util

interface Logger {
    fun logError(tag: String, error: String)
    fun logInfo(tag: String, info: String)
}
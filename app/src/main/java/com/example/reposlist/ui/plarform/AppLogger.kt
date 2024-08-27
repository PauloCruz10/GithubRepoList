package com.example.reposlist.plarform

import android.util.Log
import com.example.shareddata.logger.Logger

private const val LOG_SEPARATOR = " - "

class AppLogger : Logger {

    override fun v(tag: String, method: String, message: String) {
        val finalMessage = getFinalMessage(method, message)
        Log.v(tag, finalMessage)
    }

    override fun d(tag: String, method: String, message: String) {
        val finalMessage = getFinalMessage(method, message)
        Log.d(tag, finalMessage)
    }

    override fun i(tag: String, method: String, message: String) {
        val finalMessage = getFinalMessage(method, message)
        Log.i(tag, finalMessage)
    }

    override fun w(tag: String, method: String, message: String) {
        val finalMessage = getFinalMessage(method, message)
        Log.w(tag, finalMessage)
    }

    override fun e(tag: String, method: String, message: String) {
        val finalMessage = getFinalMessage(method, message)
        Log.e(tag, finalMessage)
    }

    override fun wtf(tag: String, method: String, throwable: Throwable) {
        val finalMessage = getFinalMessage(method, throwable.message.orEmpty())

        Log.wtf(tag, finalMessage, throwable)
    }

    private fun getFinalMessage(method: String, message: String) = StringBuilder(method).apply {
        if (message.isNotEmpty()) append(LOG_SEPARATOR)
        append(message)
    }.toString()
}
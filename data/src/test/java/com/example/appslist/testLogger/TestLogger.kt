package com.example.reposlist.testLogger

import com.example.shareddata.logger.Logger

object TestLogger : Logger {
    override fun v(tag: String, method: String, message: String) {
        println("v $tag | $method${getFinalMessage(message)}")
    }

    override fun d(tag: String, method: String, message: String) {
        println("d $tag | $method${getFinalMessage(message)}")
    }

    override fun i(tag: String, method: String, message: String) {
        println("i $tag | $method${getFinalMessage(message)}")
    }

    override fun w(tag: String, method: String, message: String) {
        println("w $tag | $method${getFinalMessage(message)}")
    }

    override fun e(tag: String, method: String, message: String) {
        println("e $tag | $method${getFinalMessage(message)}")
    }

    override fun wtf(tag: String, method: String, throwable: Throwable) {
        println("wtf $tag | $method${getFinalMessage(throwable.message)}")
    }

    private fun getFinalMessage(message: String?) = if (message.isNullOrBlank()) ""
    else " - $message"
}
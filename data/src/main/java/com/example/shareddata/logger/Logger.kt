package com.example.shareddata.logger

interface Logger {

    // region Companion object

    companion object : Logger {
        private lateinit var logger: Logger

        fun injectLogger(logger: Logger) {
            Companion.logger = logger
        }

        override fun v(tag: String, method: String, message: String) {
            logger.v(tag, method, message)
        }

        override fun d(tag: String, method: String, message: String) {
            logger.d(tag, method, message)
        }

        override fun i(tag: String, method: String, message: String) {
            logger.i(tag, method, message)
        }

        override fun w(tag: String, method: String, message: String) {
            logger.w(tag, method, message)
        }

        override fun e(tag: String, method: String, message: String) {
            logger.e(tag, method, message)
        }

        override fun wtf(tag: String, method: String, throwable: Throwable) {
            logger.wtf(tag, method, throwable)
        }
    }

    // endregion

    // region Public methods

    fun v(tag: String, method: String, message: String = "")

    fun d(tag: String, method: String, message: String = "")

    fun i(tag: String, method: String, message: String = "")

    fun w(tag: String, method: String, message: String = "")

    fun e(tag: String, method: String, message: String = "")

    fun wtf(tag: String, method: String, throwable: Throwable)

    // endregion
}
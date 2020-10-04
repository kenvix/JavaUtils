package com.kenvix.utils.lang

import sun.misc.Signal
import kotlin.Throws

object ProcessSignalHandler {
    lateinit var handlers: MutableMap<String, MutableList<() -> Unit>>
        private set

    /**
     * Setup handler. Must be called before use.
     */
    @Throws(UnsupportedOperationException::class)
    fun setup() {
        kotlin.runCatching {

        }.onFailure {

        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

    }
}
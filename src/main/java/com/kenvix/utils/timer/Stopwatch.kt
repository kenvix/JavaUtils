//--------------------------------------------------
// Class Stopwatch
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------
package com.kenvix.utils.timer

class Stopwatch {
    private var beginTime: Long = 0

    fun start(): Long {
        return System.currentTimeMillis().also { beginTime = it }
    }

    val timeDifference: Long
        get() = System.currentTimeMillis() - beginTime
}
//--------------------------------------------------
// Class Stopwatch
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.timer;

public class Stopwatch {
    private long beginTime;

    public long start() {
        return beginTime = System.currentTimeMillis();
    }

    public long getTimeDifference() {
        return System.currentTimeMillis() - beginTime;
    }
}

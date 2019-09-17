//--------------------------------------------------
// Class BlockingTools
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.tools;

public final class BlockingTools {
    private BlockingTools() {}

    private final Object interruptLock = new Object();
    private boolean isBlocked = false;

    /**
     * Sleep
     * @param millis time
     * @return false if sleep is interrupted
     */
    public boolean sleep(long millis) {
        return CommonTools.sleep(millis);
    }

    public void makeUnblocked() {
        synchronized (interruptLock) {
            isBlocked = false;
            interruptLock.notifyAll();
        }
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void makeBlocked() throws InterruptedException {
        synchronized (interruptLock) {
            isBlocked = true;
            interruptLock.wait();
        }
    }

    public static void makeCurrentThreadPermanentlyBlocked() throws InterruptedException {
        new BlockingTools().makeBlocked();
    }
}

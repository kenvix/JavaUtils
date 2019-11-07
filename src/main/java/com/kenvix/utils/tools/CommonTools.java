package com.kenvix.utils.tools;

import java.util.Timer;

public final class CommonTools {
    private CommonTools() {}
    private static Timer globalTimer = null;

    /**
     * Sleep
     * @param millis time
     * @return false if sleep is interrupted
     */
    public static boolean sleep(long millis) {
        try {
            Thread.sleep(millis, 0);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * Get global timer
     * @return Timer
     */
    public static Timer getGlobalTimer() {
        return globalTimer == null ? globalTimer = new Timer() : globalTimer;
    }
}

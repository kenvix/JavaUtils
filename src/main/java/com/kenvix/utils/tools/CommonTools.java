package com.kenvix.utils.tools;

public final class CommonTools {
    private CommonTools() {}

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
}

//--------------------------------------------------
// Class LogSettings
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class LogSettings {
    private static Map<String, Logger> loggerCache = new HashMap<>();

    public static void initLogger(Logger logger) {
        logger.setLevel(Level.ALL);
    }

    public static Logger getGlobal() {
        return getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public static Logger getLogger(String tag) {
        Logger logger = loggerCache.get(tag);

        if (logger == null) {
            logger = Logger.getLogger(tag);
            initLogger(logger);

            loggerCache.put(tag, logger);
        }

        return logger;
    }
}

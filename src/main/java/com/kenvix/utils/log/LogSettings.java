//--------------------------------------------------
// Class LogSettings
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.*;

public abstract class LogSettings {
    private static Map<String, Logger> loggerCache = new HashMap<>();

    public static void initLogger(Logger logger) {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("[%s][%s][%s=>%s] %s\n",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(record.getMillis())),
                        record.getLevel().toString(),
                        record.getSourceClassName(),
                        record.getSourceMethodName(),
                        record.getMessage()
                );
            }
        });

        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.FINEST);
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
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

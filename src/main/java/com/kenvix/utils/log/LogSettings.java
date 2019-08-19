//--------------------------------------------------
// Class LogSettings
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.*;

public final class LogSettings {
    private static Map<String, Logger> loggerCache = new HashMap<>();
    private static Map<String, String> simplifiedSourceClassNameMap = new HashMap<>();

    /**
     * No instances
     */
    private LogSettings() {}

    private static java.util.logging.Formatter formatter = new Formatter() {
        @Override
        public String format(LogRecord record) {
            return String.format("[%s][%s][%s][%s=>%s] %s\n",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.CHINA).format(new Date(record.getMillis())),
                    record.getLoggerName(),
                    record.getLevel().toString(),
                    getSimplifiedSourceClassName(record.getSourceClassName()),
                    record.getSourceMethodName(),
                    record.getMessage()
            );
        }
    };
    private static java.util.logging.Level level = Level.ALL;

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        LogSettings.level = level;
    }

    public static Formatter getFormatter() {
        return formatter;
    }

    public static String getSimplifiedSourceClassName(String sourceClassName) {
        if (simplifiedSourceClassNameMap.containsKey(sourceClassName))
            return simplifiedSourceClassNameMap.get(sourceClassName);

        StringBuilder builder = new StringBuilder(sourceClassName);

        if (sourceClassName.contains(".")) {
            int beginPosition = 0;
            int nextPosition = 0;

            do {
                nextPosition = builder.indexOf(".", beginPosition + 1);
                int deletedLength = nextPosition - beginPosition - 1;
                builder.delete(beginPosition + 1, nextPosition);
                beginPosition = nextPosition - deletedLength + 1;
            } while ((nextPosition = builder.indexOf(".", beginPosition + 1)) >= 0);
        }

        String result = builder.toString();
        simplifiedSourceClassNameMap.put(sourceClassName, result);
        return result;
    }

    public static void setFormatter(Formatter formatter) {
        LogSettings.formatter = formatter;
    }

    public static void initLogger(Logger logger) {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        logger.setLevel(level);
        consoleHandler.setLevel(level);
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

    public static void replaceLogger(Object target, @NotNull String fieldName, @NotNull Logger logger) throws NoSuchFieldException {
        Field field;

        if (target instanceof Class)
            field = ((Class) target).getDeclaredField(fieldName);
        else
            field = target.getClass().getDeclaredField(fieldName);

        Field modifiersField = Field.class.getDeclaredField("modifiers");

        try {
            AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                field.setAccessible(true);
                modifiersField.setAccessible(true);
                return null;
            });

            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            if ((field.getModifiers() & Modifier.STATIC) > 0)
                field.set(null, logger);
            else
                field.set(target, logger);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Logger replaceLogger(Object target, @NotNull String fieldName, @NotNull String loggerTag) throws NoSuchFieldException {
        Logger logger = getLogger(loggerTag);
        replaceLogger(target, fieldName, logger);

        return logger;
    }
}


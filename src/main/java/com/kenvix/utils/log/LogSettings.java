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
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

public final class LogSettings {
    private static Map<String, Logger> allocatedLoggers = new HashMap<>();
    private static Map<String, String> simplifiedSourceClassNameMap = new HashMap<>();
    private static Set<Handler> handlers = new HashSet<>();
    private static ConsoleHandler consoleHandler;
    private static boolean useConsoleHandler = true;
    private static java.util.logging.Formatter formatter;
    private static java.util.logging.Level level = Level.ALL;

    /**
     * No instances
     */
    private LogSettings() {
    }

    static {
        formatter = new Formatter() {
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

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
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

    public static void initLogger(Logger logger) {
        logger.setLevel(level);
        logger.setUseParentHandlers(false);

        if (useConsoleHandler) {
            consoleHandler.setLevel(level);
            logger.addHandler(consoleHandler);
        }
    }

    public static Logger getGlobal() {
        return getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public static Logger getLogger(String tag) {
        Logger logger = allocatedLoggers.get(tag);

        if (logger == null) {
            logger = Logger.getLogger(tag);
            initLogger(logger);

            allocatedLoggers.put(tag, logger);
        }

        return logger;
    }

    /**
     * Inject logger
     *
     * @param target
     * @param fieldName
     * @param logger
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void replaceLogger(Object target, @NotNull String fieldName, @NotNull Logger logger)
            throws NoSuchFieldException, IllegalAccessException {
        Field field;

        if (target instanceof Class)
            field = ((Class) target).getDeclaredField(fieldName);
        else
            field = target.getClass().getDeclaredField(fieldName);

        Field modifiersField = Field.class.getDeclaredField("modifiers");

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
    }

    public static Logger replaceLogger(Object target, @NotNull String fieldName, @NotNull String loggerTag)
            throws NoSuchFieldException, IllegalAccessException {
        Logger logger = getLogger(loggerTag);
        replaceLogger(target, fieldName, logger);

        return logger;
    }

    public static void setAsDefaultLogger() {
        initLogger(getLogger(""));
    }

    public static synchronized boolean isUseConsoleHandler() {
        return useConsoleHandler;
    }

    public static synchronized void setUseConsoleHandler(boolean useConsoleHandler) {
        if (LogSettings.useConsoleHandler ^ useConsoleHandler) {
            LogSettings.useConsoleHandler = useConsoleHandler;

            allocatedLoggers.forEach((key, value) -> {
                if (useConsoleHandler)
                    value.addHandler(consoleHandler);
                else
                    value.removeHandler(consoleHandler);
            });
        }
    }

    public static synchronized Set<Handler> getHandlers() {
        return handlers;
    }

    public static synchronized void addHandler(Handler handler) {
        if (!handlers.contains(handler)) {
            handlers.add(handler);

            allocatedLoggers.forEach((key, value) -> {
                value.addHandler(handler);
            });
        }
    }

    public static synchronized void removeHandler(Handler handler) {
        if (handlers.contains(handler)) {
            handlers.remove(handler);

            allocatedLoggers.forEach((key, value) -> {
                value.removeHandler(handler);
            });
        }
    }

    public static synchronized Formatter getFormatter() {
        return formatter;
    }

    public static synchronized void setFormatter(Formatter formatter) {
        LogSettings.formatter = formatter;
    }

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        LogSettings.level = level;
    }
}


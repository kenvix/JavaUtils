//--------------------------------------------------
// Class LogSettings
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import com.kenvix.utils.tools.ReflectTools;
import com.kenvix.utils.tools.StringTools;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
                String thrown = "";

                if (record.getThrown() != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    PrintStream stackTraceBuffer = new PrintStream(byteArrayOutputStream);
                    stackTraceBuffer.print(" [With thrown: ");
                    stackTraceBuffer.print(record.getThrown().getClass().getName());
                    stackTraceBuffer.print("] \n");
                    record.getThrown().printStackTrace(stackTraceBuffer);
                    thrown = byteArrayOutputStream.toString();
                }

                return String.format("[%s][%s/%s][%s][%s=>%s] %s%s\n",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(new Date(record.getMillis())),
                        record.getLoggerName(),
                        record.getThreadID(),
                        record.getLevel().toString(),
                        getSimplifiedSourceClassName(record.getSourceClassName()),
                        record.getSourceMethodName(),
                        record.getMessage(),
                        thrown
                );
            }
        };

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
    }

    public static String getSimplifiedSourceClassName(String sourceClassName) {
        if (simplifiedSourceClassNameMap.containsKey(sourceClassName))
            return simplifiedSourceClassNameMap.get(sourceClassName);

        String result = StringTools.getShortPackageName(sourceClassName);
        simplifiedSourceClassNameMap.put(sourceClassName, result);
        return result;
    }

    public static void initLogger(Logger logger) {
        initLogger(logger, level);
    }

    public static void initLogger(Logger logger, @Nullable Level level) {
        if (level == null)
            level = LogSettings.level;

        logger.setLevel(level);
        logger.setUseParentHandlers(false);

        if (useConsoleHandler) {
            consoleHandler.setLevel(level);
            logger.addHandler(consoleHandler);
        }

        handlers.forEach(logger::addHandler);
    }

    public static Logger getGlobal() {
        return getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public static Logger getLogger(String tag) {
        return getLogger(tag, level);
    }

    public static Logger getLogger(String tag, @Nullable Level level) {
        Logger logger = allocatedLoggers.get(tag);

        if (logger == null) {
            logger = Logger.getLogger(getLoggerName(tag));
            initLogger(logger, level);

            allocatedLoggers.put(tag, logger);
        }

        return logger;
    }

    public static String getLoggerName(String tag) {
        return tag;
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
        ReflectTools.replaceField(target, fieldName, logger);
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


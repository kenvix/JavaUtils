//--------------------------------------------------
// Class LoggingOutputStream
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingOutputStream extends OutputStream implements AutoCloseable {
    private Logger logger;
    private Level level;
    private char separator;
    private int count;
    private byte[] bytes;

    @Override
    public void write(int b) {
        if (separator != '\0' && b == separator) {
            flush();
        } else {
            if (count + 1 == bytes.length)
                flush();

            bytes[count] = (byte) b;
            count++;
        }
    }

    @Override
    public void write(@NotNull byte[] b) {
        logger.log(level, new String(b));
    }


    @Override
    public void flush() {
        if (count > 0) {
            String str = new String(bytes, 0, bytes[count - 1] == '\r' ? count - 1 : count);
            count = 0;

            logger.log(level, str);
        }
    }

    @Override
    public void close() {
        flush();
    }

    public LoggingOutputStream(@NotNull Logger logger, Level level, char separator, int bufferSize) {
        this.logger = logger;
        this.level = level;
        this.separator = separator;

        bytes = new byte[bufferSize];
    }

    public LoggingOutputStream(@NotNull Logger logger, Level level) {
        this(logger, level, '\n', 1024);
    }

    public LoggingOutputStream(@NotNull Logger logger, Level level, char separator) {
        this(logger, level, separator, 1024);
    }

    public LoggingOutputStream(@NotNull Logger logger, Level level, int bufferSize) {
        this(logger, level, '\n', bufferSize);
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "LoggingOutputStream for logger ${logger.name}";
    }
}

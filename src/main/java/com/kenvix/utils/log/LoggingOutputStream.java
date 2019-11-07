//--------------------------------------------------
// Class LoggingOutputStream
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import com.kenvix.utils.lang.StringOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingOutputStream extends OutputStream implements AutoCloseable, Consumer<String> {
    private Logger logger;
    private Level level;
    private StringOutputStream stream;

    @Override
    public void write(int b) {
        stream.write(b);
    }

    @Override
    public void write(@NotNull byte[] b) {
        logger.log(level, new String(b));
    }


    @Override
    public void flush() {
        stream.flush();
    }

    @Override
    public void close() {
        flush();
    }

    public LoggingOutputStream(@NotNull Logger logger, Level level, char separator, int bufferSize) {
        this.logger = logger;
        this.level = level;
        stream = new StringOutputStream(this, separator, bufferSize);
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
        return stream.getCount();
    }

    /**
     * Return current current buffer data as string
     * @return String data
     */
    @Override
    public String toString() {
        return stream.toString();
    }

    @Override
    public void accept(String s) {
        logger.log(level, s);
    }
}

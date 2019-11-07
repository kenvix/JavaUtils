//--------------------------------------------------
// Class StringOutputStream
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * String Output Stream.
 * It has a buffer, and accepts a callback which will be called when a byte[] inputted or separator appears or flush() is called.
 * Then flush the buffer after callback done.
 */
public class StringOutputStream extends OutputStream implements AutoCloseable {
    private Consumer<String> callback;
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
        callback.accept(new String(b));
    }


    @Override
    public void flush() {
        if (count > 0) {
            String str = toString();
            count = 0;

            callback.accept(str);
        }
    }

    @Override
    public void close() {
        flush();
    }

    /**
     * Create a new StringOutputStream
     * @param onNewString Callback when a byte[] inputted or separator appears or flush()
     * @param separator Separator, use \0 to disable
     * @param bufferSize Max buffer size, will be flushed if size reached
     */
    public StringOutputStream(Consumer<String> onNewString, char separator, int bufferSize) {
        this.callback = onNewString;
        this.separator = separator;

        bytes = new byte[bufferSize];
    }

    /**
     * Create a new StringOutputStream with separator \n
     * @param onNewString Callback when a byte[] inputted or separator appears or flush()
     * @param separator Separator, use \0 to disable
     */
    public StringOutputStream(Consumer<String> onNewString, char separator) {
        this(onNewString, separator, 1024);
    }

    /**
     * Create a new StringOutputStream with Max Size 1024
     * @param onNewString Callback when a byte[] inputted or separator appears or flush()
     * @param bufferSize Max buffer size, will be flushed if size reached
     */
    public StringOutputStream(Consumer<String> onNewString, int bufferSize) {
        this(onNewString,'\n', bufferSize);
    }

    /**
     * Create a new StringOutputStream with Max Size 1024 and separator \n
     * @param onNewString Callback when a byte[] inputted or separator appears or flush()
     */
    public StringOutputStream(Consumer<String> onNewString) {
        this(onNewString,'\n', 1024);
    }

    /**
     * Get current buffer count
     * @return size
     */
    public int getCount() {
        return count;
    }

    /**
     * Return current current buffer data as string
     * @return String data
     */
    @Override
    public String toString() {
        return count <= 0 ? "" : new String(bytes, 0, bytes[count - 1] == '\r' ? count - 1 : count);
    }
}

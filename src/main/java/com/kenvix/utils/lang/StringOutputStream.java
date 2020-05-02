//--------------------------------------------------
// Class StringOutputStream
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * String Output Stream implement.
 * It has a buffer, and accepts a callback which will be called when a byte[] inputted or separator appears or flush() is called.
 * Then flush the buffer after callback done.
 *
 * Thread Unsafe.
 */
@SuppressWarnings("unused")
public class StringOutputStream extends OutputStream implements AutoCloseable {
    private final Consumer<String> callback;
    private final char separator;
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


    /**
     * Invoke callback and flush data
     */
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
     * Increases the capacity if necessary to ensure that it can hold
     * at least the number of elements specified by the minimum
     * capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if {@code minCapacity < 0}.  This is
     * interpreted as a request for the unsatisfiably large capacity
     * {@code (long) Integer.MAX_VALUE + (minCapacity - Integer.MAX_VALUE)}.
     */
    private void ensureCapacity(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - bytes.length > 0)
            grow(minCapacity);
    }

    /**
     * Writes the complete contents of this string output stream to
     * the specified output stream argument, as if by calling the output
     * stream's write method using <code>out.write(buf, 0, count)</code>.
     *
     * @param      out   the output stream to which to write the data.
     * @exception IOException  if an I/O error occurs.
     */
    public void writeTo(OutputStream out) throws IOException {
        out.write(bytes, 0, count);
    }

    /**
     * Resets the <code>count</code> field of this string output
     * stream to zero, so that all currently accumulated output in the
     * output stream is discarded. The output stream can be used again,
     * reusing the already allocated buffer space.
     */
    public void reset() {
        count = 0;
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
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = bytes.length;
        int newCapacity = oldCapacity << 1;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        bytes = Arrays.copyOf(bytes, newCapacity);
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
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

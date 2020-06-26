//--------------------------------------------------
// Class LoggingOutputStream
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------
package com.kenvix.utils.log

import com.kenvix.utils.lang.StringOutputStream
import org.slf4j.Logger
import java.io.OutputStream
import java.util.function.Consumer

@Suppress("MemberVisibilityCanBePrivate")
class LoggingOutputStream @JvmOverloads constructor(
    private val logger: Logger,
    separator: Char = '\n',
    bufferSize: Int = 1024,
    var onAccept: Consumer<String> = Consumer { s ->
        logger.info(s)
    }
) : OutputStream(), AutoCloseable, Consumer<String> {

    val count: Int
        get() = stream.count

    private val stream: StringOutputStream = StringOutputStream(this, separator, bufferSize)

    override fun write(b: Int) = stream.write(b)
    override fun write(b: ByteArray) = onAccept.accept(String(b))
    override fun flush() = stream.flush()
    override fun close() = flush()

    constructor(logger: Logger, bufferSize: Int) : this(logger, '\n', bufferSize)

    /**
     * Return current current buffer data as string
     * @return String data
     */
    override fun toString(): String {
        return stream.toString()
    }

    override fun accept(t: String) = onAccept.accept(t)
}
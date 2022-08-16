package com.kenvix.utils.lang

import kotlinx.coroutines.channels.Channel
import java.io.Closeable
import java.nio.ByteBuffer

class DirectBufferPool private constructor(
    size: Int,
    maxNum: Int,
    private val buffers: Channel<ByteBuffer>
) {
    /**
     * Buffer size. Default 4 MiB
     */
    private val bufferSize: Int = size

    /**
     * Buffer Max Num. Default 2
     */
    private val bufferNum: Int = maxNum

    suspend fun useBuffer(then: (suspend (ByteBuffer) -> Unit)) {
        val buffer = buffers.receive()
        then(buffer)
        buffer.clear()
        buffers.send(buffer)
    }

    companion object Init {
        @JvmStatic
        suspend fun of(size: Int = 1 shl 22, maxNum: Int = 2): DirectBufferPool {
            val channel = Channel<ByteBuffer>(maxNum)
            for (i in 0 until maxNum) {
                channel.send(ByteBuffer.allocateDirect(size))
            }

            return DirectBufferPool(size, maxNum, channel)
        }
    }
}
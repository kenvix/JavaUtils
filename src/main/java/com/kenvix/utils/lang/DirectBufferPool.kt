package com.kenvix.utils.lang

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.nio.ByteBuffer

object DirectBufferPool {
    /**
     * Buffer size. Default 4 MiB
     */
    private var bufferSize: Int = 0

    /**
     * Buffer Max Num. Default 2
     */
    private var bufferNum: Int = 0

    private lateinit var buffers: Channel<ByteBuffer>

    private val setupMutex = Mutex()

    suspend fun setupBuffer(size: Int = 1 shl 22, maxNum: Int = 2) {
        setupMutex.withLock {
            if (this::buffers.isInitialized) {
                throw IllegalStateException("DirectBufferPool already initialized")
            }

            bufferNum = maxNum
            bufferSize = size
            buffers = Channel(maxNum)

            for (i in 0 until maxNum) {
                buffers.send(ByteBuffer.allocateDirect(size))
            }
        }
    }

    suspend fun useBuffer(then: (suspend (ByteBuffer) -> Unit)) {
        val buffer = buffers.receive()
        then(buffer)
        buffers.send(buffer)
    }
}
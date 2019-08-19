//--------------------------------------------------
// Interface ServerEventCallback
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.tcpserver;

import com.kenvix.utils.log.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public interface ServerEventCallback<@Nullable U> extends Logging {
    /**
     * Accept Completed Handler
     *
     * @param acceptHandler Instance of {@link TCPAcceptHandler}
     * @param socketChannel Connection
     * @param baseServer
     * @return Should accept more. If false, server will exit
     */
    boolean onAcceptCompleted(@NotNull TCPAcceptHandler acceptHandler, @NotNull AsynchronousSocketChannel socketChannel, @Nullable U baseServer);
    void onAcceptFailed(@NotNull TCPAcceptHandler acceptHandler, @NotNull Throwable throwable, @Nullable U baseServer);

    /**
     * Read Completed Handler
     *
     * @param readHandler Instance of {@link TCPReadHandler}
     * @param data Received data
     * @param readResultCode
     * @param socketChannel Connection
     * @return Data to send. If null, nothing will be sent
     */
    @Nullable
    String onReadCompleted(@NotNull TCPReadHandler readHandler, @NotNull ByteBuffer data, Integer readResultCode, @NotNull AsynchronousSocketChannel socketChannel);
    void onReadFailed(@NotNull TCPReadHandler readHandler, @NotNull Throwable exc, @NotNull AsynchronousSocketChannel socketChannel);

    /**
     * Send Completed Handler
     * Will be invoked if {@link #onReadCompleted(TCPReadHandler, ByteBuffer, Integer, AsynchronousSocketChannel)} return true
     *
     * @param sendHandler Instance of {@link TCPWriteHandler}
     * @param sendResultCode
     * @param socketChannel Connection
     */
    void onSendCompleted(@NotNull TCPWriteHandler sendHandler, Integer sendResultCode, @NotNull AsynchronousSocketChannel socketChannel);
    void onSendFailed(@NotNull TCPWriteHandler sendHandler, @NotNull Throwable exc, @NotNull AsynchronousSocketChannel socketChannel);
}

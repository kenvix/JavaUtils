//--------------------------------------------------
// Class TCPAcceptHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.tcpserver;

import com.kenvix.utils.log.Logging;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

public final class TCPAcceptHandler<T extends SimpleAbstractServer> implements CompletionHandler<AsynchronousSocketChannel, T>, Logging {
    private ServerEventCallback<T> callback;
    private int readBufferSize = 10000000;
    private long readTimeout = 10000L;
    private long writeTimeout = 5000L;
    private int socketReceiveBufferSize = 4096;
    private int socketSendBufferSize = 4096;

    private void acceptMore(T attachment) {
        attachment.getChannel().accept(attachment, this);
    }

    public void readMore(AsynchronousSocketChannel socketChannel) {
        if (socketChannel.isOpen()) {
            ByteBuffer buffer = ByteBuffer.allocate(readBufferSize);
            socketChannel.read(buffer, readTimeout, TimeUnit.SECONDS, socketChannel, new TCPReadHandler(buffer, this));
        }
    }

    @Override
    public void completed(AsynchronousSocketChannel socketChannel, T attachment) {
        acceptMore(attachment);
        try {
            getLogger().finer("Accepted: " + socketChannel.getRemoteAddress());

            socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, socketReceiveBufferSize);
            socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, socketSendBufferSize);

            if(callback.onAcceptCompleted(this, socketChannel, attachment)) {
                ByteBuffer buffer = ByteBuffer.allocate(readBufferSize);
                readMore(socketChannel);
            } else {
                try {
                    socketChannel.close();
                    getLogger().fine("Successfully reset connection");
                } catch (IOException e) {
                    getLogger().info("Connection positive reset failed (accept stage): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, T attachment) {
        acceptMore(attachment);
        getLogger().warning("Accept Failed" + exc.toString());

        if (callback != null)
            callback.onAcceptFailed(this, exc, attachment);
    }

    public int getSocketReceiveBufferSize() {
        return socketReceiveBufferSize;
    }

    public TCPAcceptHandler setSocketReceiveBufferSize(int socketReceiveBufferSize) {
        this.socketReceiveBufferSize = socketReceiveBufferSize;
        return this;
    }

    public int getSocketSendBufferSize() {
        return socketSendBufferSize;
    }

    public TCPAcceptHandler setSocketSendBufferSize(int socketSendBufferSize) {
        this.socketSendBufferSize = socketSendBufferSize;
        return this;
    }

    public ServerEventCallback<T> getCallback() {
        return callback;
    }

    public TCPAcceptHandler setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
        return this;
    }

    public TCPAcceptHandler setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public TCPAcceptHandler(ServerEventCallback<T> event) {
        this.callback = event;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public TCPAcceptHandler<T> setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    @Override
    public String getLogTag() {
        return callback.getLogTag();
    }
}
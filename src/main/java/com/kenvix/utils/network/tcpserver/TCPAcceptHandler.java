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
    private int readTimeout = 5000;
    private int writeTimeout = 5000;
    private int socketReceiveBufferSize = 4096;
    private int socketSendBufferSize = 4096;

    private void acceptMore(T attachment) {
        attachment.getChannel().accept(attachment, this);
    }

    @Override
    public void completed(AsynchronousSocketChannel result, T attachment) {
        acceptMore(attachment);
        try {
            getLogger().finer("Accepted: " + result.getRemoteAddress());

            result.setOption(StandardSocketOptions.SO_RCVBUF, socketReceiveBufferSize);
            result.setOption(StandardSocketOptions.SO_SNDBUF, socketSendBufferSize);

            if(callback.onAcceptCompleted(this, result, attachment)) {
                ByteBuffer buffer = ByteBuffer.allocate(readBufferSize);
                result.read(buffer, readTimeout, TimeUnit.MILLISECONDS, result, new TCPReadHandler(buffer, this));
            } else {
                try {
                    result.close();
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
        getLogger().warning("Accept Failed" + exc.getMessage());

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

    public int getWriteTimeout() {
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
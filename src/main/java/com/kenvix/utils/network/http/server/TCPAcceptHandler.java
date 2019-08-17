//--------------------------------------------------
// Class TCPAcceptHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import com.kenvix.utils.log.Logging;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

public class TCPAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, SimpleAsyncHTTPServer>, Logging {
    private ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> callback;
    private int readBufferSize = 10000000;
    private int timeout = 5000;
    private int socketReceiveBufferSize = 4096;
    private int socketSendBufferSize = 4096;

    public ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> getCallback() {
        return callback;
    }

    public TCPAcceptHandler setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
        return this;
    }

    public TCPAcceptHandler setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public TCPAcceptHandler(ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> event) {
        this.callback = event;
    }

    private void acceptMore(SimpleAsyncHTTPServer attachment) {
        attachment.getChannel().accept(attachment, this);
    }

    @Override
    public void completed(AsynchronousSocketChannel result, SimpleAsyncHTTPServer attachment) {
        acceptMore(attachment);
        try {
            getLogger().finer("Accepted: " + result.getRemoteAddress());

            result.setOption(StandardSocketOptions.SO_RCVBUF, socketReceiveBufferSize);
            result.setOption(StandardSocketOptions.SO_SNDBUF, socketSendBufferSize);

            if(callback.onAcceptComplete(this, result, attachment)) {
                ByteBuffer buffer = ByteBuffer.allocate(readBufferSize);
                result.read(buffer, timeout, TimeUnit.MINUTES, result, new TCPReadHandler(buffer, this));
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
    public void failed(Throwable exc, SimpleAsyncHTTPServer attachment) {
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

    @Override
    public String getLogTag() {
        return "SimpleHTTPServer";
    }
}
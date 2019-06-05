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

class TCPAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, SimpleAsyncHTTPServer>, Logging {
    private ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> callback;
    private int bufferSize = 10000000;
    private int timeout = 5000;

    public ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> getCallback() {
        return callback;
    }

    public TCPAcceptHandler setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
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
            getLogger().fine("Accepted: " + result.getRemoteAddress());

            result.setOption(StandardSocketOptions.SO_RCVBUF, 4096);
            result.setOption(StandardSocketOptions.SO_SNDBUF, 4096);

            if(callback.onAcceptComplete(this, result, attachment)) {
                ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
                result.read(buffer, timeout, TimeUnit.MINUTES, result, new TCPReadHandler(buffer, this));
            } else {
                try {
                    result.close();
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

    @Override
    public String getLogTag() {
        return "SimpleHTTPServer";
    }
}
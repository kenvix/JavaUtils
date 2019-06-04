//--------------------------------------------------
// Class TCPAcceptHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import com.kenvix.ping.Environment;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

class TCPAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, SimpleAsyncHTTPServer> {
    private ServerEvent<TCPAcceptHandler, SimpleAsyncHTTPServer> event = null;

    public TCPAcceptHandler(ServerEvent<TCPAcceptHandler, SimpleAsyncHTTPServer> event) {
        this.event = event;
    }

    private void acceptMore(SimpleAsyncHTTPServer attachment) {
        attachment.getChannel().accept(attachment, this);
    }

    @Override
    public void completed(AsynchronousSocketChannel result, SimpleAsyncHTTPServer attachment) {
        acceptMore(attachment);
        try {
            Environment.getLogger().info("Accepted: " + result.getRemoteAddress());

            result.setOption(StandardSocketOptions.SO_RCVBUF, 4096);
            result.setOption(StandardSocketOptions.SO_SNDBUF, 4096);

            if (event != null)
                event.onAcceptComplete(this, result, attachment);

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            result.read(buffer, 100, TimeUnit.MINUTES, result, new TCPReadHandler(buffer, this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, SimpleAsyncHTTPServer attachment) {
        acceptMore(attachment);
        Environment.getLogger().warning("Accept Failed" + exc.getMessage());

        if (event != null)
            event.onAcceptFailed(this, exc, attachment);
    }
}
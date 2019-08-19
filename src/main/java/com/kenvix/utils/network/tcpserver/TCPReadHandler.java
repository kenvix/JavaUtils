//--------------------------------------------------
// Class TCPReadHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.tcpserver;

import com.kenvix.utils.log.Logging;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

public final class TCPReadHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>, Logging {
    private ByteBuffer buffer;
    private final TCPAcceptHandler acceptHandler;

    TCPReadHandler(ByteBuffer buffer, TCPAcceptHandler acceptHandler) {
        this.buffer = buffer;
        this.acceptHandler = acceptHandler;
    }

    @Override
    public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
        if (result < 0) {
            getLogger().fine("Connection closed by client.");
        } else if (result == 0) {
            getLogger().fine("Empty data");
        } else {
            buffer.flip();
            String outDataBuilder = acceptHandler.getCallback().onReadCompleted(this, buffer, result, socketChannel);
            ByteBuffer outBuffer = null;

            if (outDataBuilder != null) {
                byte[] bytes = outDataBuilder.getBytes();
                outBuffer = ByteBuffer.wrap(bytes);
                socketChannel.write(outBuffer, acceptHandler.getWriteTimeout(), TimeUnit.SECONDS,
                        socketChannel, new TCPWriteHandler(this));
            }

            acceptHandler.readMore(socketChannel);
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel socketChannel) {
        getLogger().warning("Read Failed: " + exc.toString());
        acceptHandler.getCallback().onReadFailed(this, exc, socketChannel);
    }

    @Override
    public String getLogTag() {
        return acceptHandler.getLogTag();
    }

    public TCPAcceptHandler getAcceptHandler() {
        return acceptHandler;
    }
}


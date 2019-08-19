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
    public void completed(Integer result, AsynchronousSocketChannel attachment) {
        if (result < 0) {
            getLogger().info("Connection closed by client.");
        } else if (result == 0) {
            getLogger().info("Empty data");
        } else {
            buffer.flip();
            String outDataBuilder = acceptHandler.getCallback().onReadCompleted(this, buffer, result, attachment);
            ByteBuffer outBuffer = null;

            if (outDataBuilder != null) {
                byte[] bytes = outDataBuilder.getBytes();
                outBuffer = ByteBuffer.wrap(bytes);
                attachment.write(outBuffer, acceptHandler.getWriteTimeout(), TimeUnit.MILLISECONDS,
                        attachment, new TCPWriteHandler(this));
            }
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        getLogger().warning("Read Failed" + exc.getMessage());
        acceptHandler.getCallback().onReadFailed(this, exc, attachment);
    }

    @Override
    public String getLogTag() {
        return acceptHandler.getLogTag();
    }

    public TCPAcceptHandler getAcceptHandler() {
        return acceptHandler;
    }
}


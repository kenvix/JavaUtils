//--------------------------------------------------
// Class TCPReadHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import com.kenvix.utils.log.Logging;
import com.kenvix.utils.log.ResourcedLogging;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

class TCPReadHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>, ResourcedLogging {
    private ByteBuffer buffer;
    private final TCPAcceptHandler acceptHandler;

    public TCPReadHandler(ByteBuffer buffer, TCPAcceptHandler acceptHandler) {
        this.buffer = buffer;
        this.acceptHandler = acceptHandler;
    }

    @Override
    public void completed(Integer result, AsynchronousSocketChannel attachment) {
        if (result < 0) {
            System.err.println("Connection closed by client.");
        } else if (result == 0) {
            System.out.println("Empty data");
        } else {
            buffer.flip();
            CharBuffer charBuffer = Charset.forName("utf-8").decode(buffer);
            System.out.println(charBuffer.toString());

            String outDataBuilder = acceptHandler.getCallback().onReadComplete(acceptHandler, result, attachment);

            if (outDataBuilder != null) {
                ByteBuffer outBuffer = ByteBuffer.wrap(outDataBuilder.getBytes());
                attachment.write(outBuffer);
            }

            try {
                attachment.close();
            } catch (IOException e) {
                getLogger().info("Connection close failed (read stage): " + e.getMessage());
            }
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        getLogger().warning("Read Failed" + exc.getMessage());
        acceptHandler.getCallback().onReadFailed(acceptHandler, exc, attachment);
    }

    @Override
    public String getLogTag() {
        return acceptHandler.getLogTag();
    }

    @Override
    public String getLogResourceBundleName() {
        return String.valueOf(acceptHandler.hashCode());
    }
}


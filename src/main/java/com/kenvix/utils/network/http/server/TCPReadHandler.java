//--------------------------------------------------
// Class TCPReadHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

class TCPReadHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {
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

            String outDataBuilder = "HTTP/1.1 200 OK" +
                    "\r\nContent-type: text/html\r\n\r\n" +
                    "<h1>fuck♂you.<h2><pre> \r\nOriginal Request:\r\n" +
                    charBuffer + "</pre>";

            ByteBuffer outBuffer = ByteBuffer.wrap(outDataBuilder.getBytes());
            attachment.write(outBuffer);
            try {
                attachment.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        System.err.println("===Read Failed===" + exc.getMessage());
    }
}


//--------------------------------------------------
// Class TCPWriteHandler
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.tcpserver;

import com.kenvix.utils.log.Logging;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public final class TCPWriteHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>, Logging {
    private TCPReadHandler readHandler;

    public TCPWriteHandler(TCPReadHandler readHandler) {
        this.readHandler = readHandler;
    }

    @Override
    public void completed(Integer result, AsynchronousSocketChannel attachment) {
        readHandler.getAcceptHandler().getCallback().onSendCompleted(this, result, attachment);
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        getLogger().warning("Write Failed: " + exc.getMessage());
        readHandler.getAcceptHandler().getCallback().onSendFailed(this, exc, attachment);
    }

    @Override
    public String getLogTag() {
        return readHandler.getLogTag();
    }
}

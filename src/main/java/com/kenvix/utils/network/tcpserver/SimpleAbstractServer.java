//--------------------------------------------------
// Class AbstractServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.tcpserver;

import com.kenvix.utils.log.Logging;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public abstract class SimpleAbstractServer implements Logging {
    private AsynchronousServerSocketChannel channel;
    private ServerEventCallback<SimpleAbstractServer> callback;
    private int bufferSize = 10000000;
    private int timeout = 5000;

    public SimpleAbstractServer(ServerEventCallback<SimpleAbstractServer> callback) {
        this.callback = callback;
    }

    protected final void listen(InetSocketAddress address) throws IOException {
        channel = AsynchronousServerSocketChannel.open().bind(address);
        TCPAcceptHandler handler = new TCPAcceptHandler<>(callback).setReadBufferSize(bufferSize).setReadTimeout(timeout);
        channel.accept(this, handler);
    }

    public final int getBufferSize() {
        return bufferSize;
    }

    public final SimpleAbstractServer setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public final int getTimeout() {
        return timeout;
    }

    public final SimpleAbstractServer setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public final AsynchronousServerSocketChannel getChannel() {
        return channel;
    }
}

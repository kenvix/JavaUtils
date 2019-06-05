// Class SimpleAsyncHTTPServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import com.kenvix.utils.log.Logging;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class SimpleAsyncHTTPServer implements Logging {
    private AsynchronousServerSocketChannel channel;
    private ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> callback;
    private int bufferSize = 10000000;
    private int timeout = 5000;

    public SimpleAsyncHTTPServer(ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer> callback) {
        this.callback = callback;
    }

    public SimpleAsyncHTTPServer setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public SimpleAsyncHTTPServer setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    private void listen(InetSocketAddress address) throws IOException {
        channel = AsynchronousServerSocketChannel.open().bind(address);
        channel.accept(this, new TCPAcceptHandler(callback).setBufferSize(bufferSize).setTimeout(timeout));
    }

    public AsynchronousServerSocketChannel getChannel() {
        return channel;
    }

    public void start(String host, int port) {
        try {
            listen(new InetSocketAddress(InetAddress.getByName(host), port));
            getLogger().fine(String.format("Server started at %s:%d", host, port));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String getLogTag() {
        return "SimpleHTTPServer";
    }
}
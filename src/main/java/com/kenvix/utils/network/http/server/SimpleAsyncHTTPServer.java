// Class SimpleAsyncHTTPServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class SimpleAsyncHTTPServer {
    private AsynchronousServerSocketChannel channel;
    private ServerEvent<TCPAcceptHandler, SimpleAsyncHTTPServer> callback;

    private void listen(InetSocketAddress address) throws IOException {
        channel = AsynchronousServerSocketChannel.open().bind(address);
        channel.accept(this, new TCPAcceptHandler(callback));
    }

    public AsynchronousServerSocketChannel getChannel() {
        return channel;
    }

    public SimpleAsyncHTTPServer setCallback(ServerEvent<TCPAcceptHandler, SimpleAsyncHTTPServer> callback) {
        this.callback = callback;
        return this;
    }

    public void start(String host, int port) throws IOException {
        listen(new InetSocketAddress(InetAddress.getByName(host), port));
    }
}
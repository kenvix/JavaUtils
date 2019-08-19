// Class SimpleAsyncHTTPServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http;

import com.kenvix.utils.log.Logging;
import com.kenvix.utils.network.tcpserver.SimpleAbstractServer;
import com.kenvix.utils.network.tcpserver.ServerEventCallback;
import com.kenvix.utils.network.tcpserver.TCPAcceptHandler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class SimpleAsyncHTTPServerSimple extends SimpleAbstractServer implements Logging {
    public SimpleAsyncHTTPServerSimple(ServerEventCallback<SimpleAbstractServer> callback) {
        super(callback);
    }

    public void start(String host, int port) {
        try {
            listen(new InetSocketAddress(InetAddress.getByName(host), port));
            getLogger().fine(String.format("HTTP Server started at %s:%d", host, port));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String getLogTag() {
        return "SimpleHTTPServer";
    }
}
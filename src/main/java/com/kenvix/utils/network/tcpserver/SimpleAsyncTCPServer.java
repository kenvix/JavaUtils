//--------------------------------------------------
// Class SimpleAsyncTCPServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.tcpserver;

import com.kenvix.utils.log.Logging;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class SimpleAsyncTCPServer extends SimpleAbstractServer implements Logging {
    private ServerEventCallback<SimpleAbstractServer> callback;

    public SimpleAsyncTCPServer(ServerEventCallback<SimpleAbstractServer> callback) {
        super(callback);
        this.callback = callback;
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
        return callback.getLogTag();
    }
}

//--------------------------------------------------
// Interface ServerEvent
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import java.nio.channels.AsynchronousSocketChannel;

public interface ServerEvent<T, U> {
    void onReadComplete(T instance, Integer result, AsynchronousSocketChannel attachment);
    void onReadFailed(T instance, Throwable exc, AsynchronousSocketChannel attachment);

    void onAcceptComplete(T instance, AsynchronousSocketChannel result, U attachment);
    void onAcceptFailed(T instance, Throwable result, U attachment);
}

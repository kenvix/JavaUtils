//--------------------------------------------------
// Interface ServerEvent
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http.server;

import org.jetbrains.annotations.Nullable;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public interface ServerEvent<T extends CompletionHandler<AsynchronousSocketChannel, U>, U> {
    @Nullable
    String onReadComplete(T acceptHandler, Integer result, AsynchronousSocketChannel socketChannel);
    void onReadFailed(T acceptHandler, Throwable exc, AsynchronousSocketChannel socketChannel);

    boolean onAcceptComplete(T acceptHandler, AsynchronousSocketChannel socketChannel, U baseServer);
    void onAcceptFailed(T acceptHandler, Throwable throwable, U baseServer);
}

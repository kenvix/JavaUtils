//--------------------------------------------------
// Class SimpleHttpServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.http.HTTPResponseUtils;
import com.kenvix.utils.network.tcpserver.*;
import com.kenvix.utils.network.http.SimpleAsyncHTTPServer;
import com.kenvix.utils.tools.BlockingTools;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SimpleHttpServer {
    public static void main(String[] args) {
        SimpleAsyncHTTPServer server = new SimpleAsyncHTTPServer(new ServerEventCallback<SimpleAbstractServer>() {
            @Override
            public @Nullable String onReadCompleted(@NotNull TCPReadHandler readHandler, @NotNull ByteBuffer data, Integer readResultCode, @NotNull AsynchronousSocketChannel socketChannel) {
                HTTPResponseUtils responseUtils = new HTTPResponseUtils(data);
                return responseUtils.constructResponse(200).constructData("ok").toString();
            }

            @Override
            public void onReadFailed(@NotNull TCPReadHandler readHandler, @NotNull Throwable exc, @NotNull AsynchronousSocketChannel socketChannel) {

            }

            @Override
            public void onSendCompleted(@NotNull TCPWriteHandler sendHandler, Integer sentResult, @NotNull AsynchronousSocketChannel socketChannel) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSendFailed(@NotNull TCPWriteHandler sendHandler, @NotNull Throwable exc, @NotNull AsynchronousSocketChannel socketChannel) {

            }

            @Override
            public boolean onAcceptCompleted(@NotNull TCPAcceptHandler acceptHandler, @NotNull AsynchronousSocketChannel socketChannel, @Nullable SimpleAbstractServer baseServer) {
                return true;
            }

            @Override
            public void onAcceptFailed(@NotNull TCPAcceptHandler acceptHandler, @NotNull Throwable throwable, @Nullable SimpleAbstractServer baseServer) {

            }

            @Override
            public String getLogTag() {
                return "TestHTTPServer";
            }
        });

        server.start("127.0.0.1", 11515);
        try {
            BlockingTools.makeCurrentThreadPermanentlyBlocked();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

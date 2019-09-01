//--------------------------------------------------
// Class ChatServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.http.HTTPResponseUtils;
import com.kenvix.utils.network.tcpserver.*;
import com.kenvix.utils.tools.BlockingTools;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;

public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        SimpleAsyncTCPServer server = new SimpleAsyncTCPServer(new ServerEventCallback<SimpleAbstractServer>() {
            @Override
            public boolean onAcceptCompleted(@NotNull TCPAcceptHandler acceptHandler, @NotNull AsynchronousSocketChannel socketChannel, @Nullable SimpleAbstractServer baseServer) {
                return true;
            }

            @Override
            public void onAcceptFailed(@NotNull TCPAcceptHandler acceptHandler, @NotNull Throwable throwable, @Nullable SimpleAbstractServer baseServer) {

            }

            @Override
            public @Nullable String onReadCompleted(@NotNull TCPReadHandler readHandler, @NotNull ByteBuffer data, Integer readResultCode, @NotNull AsynchronousSocketChannel socketChannel) {
                String received = StandardCharsets.UTF_8.decode(data).toString();
                getLogger().finest("Received: "+ received);
                return received + "\r\n";
            }

            @Override
            public void onReadFailed(@NotNull TCPReadHandler readHandler, @NotNull Throwable exc, @NotNull AsynchronousSocketChannel socketChannel) {

            }

            @Override
            public void onSendCompleted(@NotNull TCPWriteHandler sendHandler, Integer sentResult, @NotNull AsynchronousSocketChannel socketChannel) {
                getLogger().finest("Sent: "+ sentResult);
            }

            @Override
            public void onSendFailed(@NotNull TCPWriteHandler sendHandler, @NotNull Throwable exc, @NotNull AsynchronousSocketChannel socketChannel) {

            }

            @Override
            public String getLogTag() {
                return "EchoServer";
            }
        });
        server.start("127.0.0.1", 11515);
        BlockingTools.makeCurrentThreadPermanentlyBlocked();
    }
}

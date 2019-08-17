//--------------------------------------------------
// Class SimpleHttpServer
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.http.HTTPResponseUtils;
import com.kenvix.utils.network.http.server.ServerEventCallback;
import com.kenvix.utils.network.http.server.SimpleAsyncHTTPServer;
import com.kenvix.utils.network.http.server.TCPAcceptHandler;
import com.kenvix.utils.tools.BlockingTools;
import com.kenvix.utils.tools.CommonTools;
import jdk.nashorn.internal.ir.Block;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SimpleHttpServer {
    public static void main(String[] args) {
        SimpleAsyncHTTPServer server = new SimpleAsyncHTTPServer(new ServerEventCallback<TCPAcceptHandler, SimpleAsyncHTTPServer>() {
            @Override
            public String onReadComplete(TCPAcceptHandler acceptHandler, ByteBuffer data, Integer readResultCode, AsynchronousSocketChannel socketChannel) {
                HTTPResponseUtils responseUtils = new HTTPResponseUtils(data);
                return responseUtils.constructResponse(200).constructData("ok").toString();
            }

            @Override
            public void onReadFailed(TCPAcceptHandler acceptHandler, Throwable exc, AsynchronousSocketChannel socketChannel) {

            }

            @Override
            public boolean onAcceptComplete(TCPAcceptHandler acceptHandler, AsynchronousSocketChannel socketChannel, SimpleAsyncHTTPServer baseServer) {
                return true;
            }

            @Override
            public void onAcceptFailed(TCPAcceptHandler acceptHandler, Throwable throwable, SimpleAsyncHTTPServer baseServer) {

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

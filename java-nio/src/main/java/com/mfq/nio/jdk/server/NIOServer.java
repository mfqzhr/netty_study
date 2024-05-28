package com.mfq.nio.jdk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO-server-jdk
 * //todo: 这里代码是错的 后面研究下
 */
public class NIOServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //nio-select 首先打开和网络缓冲区的交互
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(() -> {
            ServerSocketChannel serverSocketChannel = null;
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(28888));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
                while (serverSelector.select() > 0) {
                    Set<SelectionKey> set = serverSelector.selectedKeys();
                    for (SelectionKey key : set) {
                        if (key.isAcceptable()) {
                            SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                            try {
                                clientChannel.configureBlocking(false);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            clientChannel.register(clientSelector, SelectionKey.OP_READ);

                        }

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            try {
                while (clientSelector.select() > 0) {
                    Set<SelectionKey> set = clientSelector.selectedKeys();
                    for (SelectionKey key : set) {
                        if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            clientChannel.read(byteBuffer);
                            //这什么玩意
                            byteBuffer.flip();
                            System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}

package com.mfq.bio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞IO-Server
 */
public class IOServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(18888);
        while (true) {
            //1.这个方法会阻塞,直到有连接过来
            Socket socket = serverSocket.accept();
            System.out.println("I am accept a new client connect...");
            //2.处理数据的读取,这里重新启动一个线程,因为我们Server仅仅需要读取client的数据,
            //然后输出到控制台,这个流程涉及到io但是他们不是阻塞流程
            new Thread(() -> {
                try {
                    //这是一个死循环,如果有多个客户端串讲多个链接 那么就会有
                    // 多个线程一直循环读取数据
                    doReadData(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }
    }

    private static void doReadData(Socket socket) throws IOException {
        int len;
        byte[] buffer = new byte[1024];
        //这里的读取也是阻塞的
        InputStream inputStream = socket.getInputStream();
        for (; ; ) {
            len = inputStream.read(buffer);
            if (len != -1) {
                System.out.println(new String(buffer, 0, len));
            } else {
                System.out.println("数据为空");
            }
        }

    }
}

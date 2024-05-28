package com.mfq.nio.jdk.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * 阻塞io-client
 */
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 28888);
        while (true) {
            socket.getOutputStream().write(("client : " + System.getenv("name") +" " + new Date() + " : hello").getBytes());
            Thread.sleep(2000);
        }
    }
}

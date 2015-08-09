package com.nio.learning.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class TimeClient implements Runnable {
    final SocketChannel c;

    TimeClient(String host, int port) throws IOException {
        c = SocketChannel.open();
        c.socket().connect(new InetSocketAddress(host, port));
        c.configureBlocking(false);
    }

    public void run() {
        try {
            c.write(ByteBuffer.wrap("hello".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

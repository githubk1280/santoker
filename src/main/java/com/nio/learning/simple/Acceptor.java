package com.nio.learning.simple;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lefu on 15/8/10.
 * Description:
 */
public class Acceptor implements Runnable {
    AtomicInteger count;
    final int op;
    final SelectionKey key;

    Acceptor(int op, SelectionKey key, AtomicInteger count) {
        this.count = count;
        this.op = op;
        this.key = key;
    }

    public void run() {
        if (SelectionKey.OP_ACCEPT == op) {
            try {
                accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (SelectionKey.OP_READ == op) {
            try {
                read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (SelectionKey.OP_CONNECT == op) {
            connect();
        } else if (SelectionKey.OP_WRITE == op) {
            try {
                write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read() throws IOException {
        SocketChannel c = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        int readBytes = c.read(buffer);
        System.out.println("read bytes = " + readBytes);
        if (-1 == readBytes) {
            c.close();
            key.cancel();
            return;
        }
        byte[] values = buffer.array();
        System.out.println("#########" + c.getLocalAddress() + "#########received :[" + new String(values).trim() + "]");
    }

    private void write() throws IOException {
        SocketChannel c = (SocketChannel) key.channel();
        c.write(ByteBuffer.wrap((" message from server " + count.incrementAndGet()).getBytes()));
        c.register(key.selector(), SelectionKey.OP_READ);
    }

    private void connect() {
        System.out.println("connect ...");
    }

    private void accept() throws IOException {
        System.out.println("accept ...");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel c = serverSocketChannel.accept();
        c.configureBlocking(false);
        c.write(ByteBuffer.wrap((" accept from server" + count.incrementAndGet()).getBytes()));
        c.register(key.selector(), SelectionKey.OP_READ );
    }
}

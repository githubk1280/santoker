package com.nio.learning.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class TimeServer implements Runnable {
    final ServerSocketChannel serverSocketChannel;
    final Selector selector;

    TimeServer(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("TimeServer start listening on " + port);
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                int hit = selector.select();
                System.out.println("======hit num =" + hit);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        System.out.println("------------" + key.channel() + " isAcceptable");
                        new Acceptor(SelectionKey.OP_ACCEPT, key).run();
                    } else if (key.isReadable()) {
                        System.out.println("------------ " + key.channel() + " isReadable");
                        new Acceptor(SelectionKey.OP_READ, key).run();
                    } else if (key.isConnectable()) {
                        System.out.println("------------ isConnectable");
                    } else if (key.isWritable()) {
                        System.out.println("------------ isWritable");
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Acceptor implements Runnable {
        final int op;
        final SelectionKey key;

        Acceptor(int op, SelectionKey key) {
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
                write();
            }
        }

        private void read() throws IOException {
            printContent();
        }

        private void write() {

        }

        private void connect() {

        }

        private void accept() throws IOException {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel c = serverSocketChannel.accept();
            c.configureBlocking(false);
            c.write(ByteBuffer.wrap("hello from server".getBytes()));
            c.register(key.selector(), SelectionKey.OP_READ);
        }

        private void printContent() throws IOException {
            SocketChannel c = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(128);
            int readBytes = c.read(buffer);
            System.out.println("read " + readBytes);
            if (-1 == readBytes) {
                c.close();
                key.cancel();
            }
            byte[] values = buffer.array();
            System.out.println("#########Server received :[" + new String(values).trim()+"]");
        }
    }

}

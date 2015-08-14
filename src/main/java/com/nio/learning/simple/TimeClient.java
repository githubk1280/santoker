package com.nio.learning.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class TimeClient implements Runnable {
    final SocketChannel c;
    final Selector selector;

    TimeClient(String host, int port) throws IOException {
        c = SocketChannel.open();
        c.socket().connect(new InetSocketAddress(host, port));
        c.configureBlocking(false);
        selector = Selector.open();
        c.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
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
                        new ClientAcceptor(SelectionKey.OP_ACCEPT, key).run();
                    } else if (key.isReadable()) {
                        System.out.println("------------ " + key.channel() + " isReadable");
                        new ClientAcceptor(SelectionKey.OP_READ, key).run();
                    } else if (key.isConnectable()) {
                        System.out.println("------------ isConnectable");
                    } else if (key.isWritable()) {
                        System.out.println("------------ " + key.channel() + " isWritable");
                        new ClientAcceptor(SelectionKey.OP_WRITE, key).run();
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

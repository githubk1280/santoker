package com.nio.learning.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class TimeServer implements Runnable {
    AtomicInteger count = new AtomicInteger(0);
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
                        System.out.println("------------" + key.channel() + " ------------isAcceptable");
                        new Acceptor(SelectionKey.OP_ACCEPT, key, count).run();
                    } else if (key.isReadable()) {
                        System.out.println("------------ " + key.channel() + " ------------isReadable");
                        new Acceptor(SelectionKey.OP_READ, key, count).run();
                    } else if (key.isConnectable()) {
                        System.out.println("------------  " + key.channel() + " isConnectable");
                    } else if (key.isWritable()) {
                        System.out.println("------------ " + key.channel() + " ------------isWritable");
                        new Acceptor(SelectionKey.OP_WRITE, key, count).run();
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

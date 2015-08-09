package com.nio.learning;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lefu on 15/8/8.
 * Description:
 */
public class Reactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    dispatch(it.next());
                }
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) k.attachment();
        if (null != r)
            r.run();
    }

    class Acceptor implements Runnable {
        public void run() {
            try {
                SocketChannel c = serverSocketChannel.accept();
                if (c != null) {
                    new Handler(selector, c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

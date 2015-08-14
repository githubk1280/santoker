package com.nio.learning.high;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class UDPReactor implements Runnable {
    private final static String module = UDPReactor.class.getName();
    private final Selector selector;

    public UDPReactor(int port) throws IOException {
        selector = Selector.open();
        InetSocketAddress address =
                new InetSocketAddress("127.0.0.1", port);
        DatagramChannel channelRec = openDatagramChannel();
        channelRec.socket().bind(address); //绑定socketAddress
        //向selector注册该channel
        SelectionKey key = channelRec.register(selector, SelectionKey.OP_READ);
        key.attach(new UDPHandler(key, channelRec));
    }

    //生成一个DatagramChannel实例
    private DatagramChannel openDatagramChannel() {
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (Exception e) {
            System.out.println(e + module);
        }
        return channel;
    }

    public void run() { // normally in a new Thread
        try {
            while (!Thread.interrupted()) {
                System.out.println("looping ...");
                selector.select();
                Set selected = selector.selectedKeys();
                System.out.println("looping ..." + selected.size());
                Iterator it = selected.iterator();
                while (it.hasNext())
                    //来一个事件 第一次触发一个accepter线程
                    //以后触发SocketReadHandler
                    dispatch((SelectionKey) (it.next()));
                selected.clear();
            }
        } catch (IOException ex) {
            System.out.println("reactor stop!" + ex + module);
        }
    }

    //运行Acceptor或SocketReadHandler
    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            System.out.println("-->dispatch running");
            new Thread(r).start();
        }
    }

    public static void main(String args[]) throws IOException {
        UDPReactor server = new UDPReactor(8080);
        server.run();
    }
}


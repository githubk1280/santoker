package com.nio.learning.high;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class TCPClient extends Thread {
    private final static String module = TCPClient.class.getName();
    //这是一个消息队列，用于和前台客户端界面输入实现通信
    private final static MessageList messageList = MessageList.getInstance();

    private InetSocketAddress socketAddress;
    private Selector selector;
    private SocketHelper socketHelper;                                         //Socket读写帮助

    public TCPClient(String url, int port) {
        try {
            socketAddress = new InetSocketAddress(url, port);
            selector = Selector.open();
            openSocketChannel();                                                       //开启一个SocketChannel
            socketHelper = new SocketHelper();
        } catch (Exception e) {
            System.out.println("init error:" + e + module);
        }
    }

    //直接开启一个SocketChannel
    private SocketChannel openSocketChannel() {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(this.socketAddress);                               //绑定SocketAddress
            channel.register(selector, SelectionKey.OP_CONNECT); //注册OP_CONNECT事件
        } catch (Exception e) {
            System.out.println(e + module);
        }
        return channel;
    }

    public void run() {//线程运行方法
        try {
            while (!Thread.interrupted()) {
                if (selector.select(30) > 0) {//为防止底层堵塞，设置TimeOutt事件
                    doSelector(selector);
                }
            }
        } catch (Exception e) {
            System.out.println("run error:" + e + module);
        }
    }

    //分别获取触发的事件对象SelectionKey
    private void doSelector(Selector selector) throws Exception {
        Set readyKeys = selector.selectedKeys();
        Iterator readyItor = readyKeys.iterator();
        while (readyItor.hasNext()) {
            SelectionKey key = (SelectionKey) readyItor.next();
            readyItor.remove();
            doKey(key);
            readyKeys.clear();
        }
    }

    private void doKey(SelectionKey key) throws Exception {
        SocketChannel keyChannel = null;
        try {
            keyChannel = (SocketChannel) key.channel();
            if (key.isConnectable()) {//如果连接成功
                if (keyChannel.isConnectionPending()) {
                    keyChannel.finishConnect();
                }
                System.out.println(" connected the server" + module);
                sendRequest(keyChannel);//首先发送数据
                key.interestOps(SelectionKey.OP_READ); //注册为可写
            } else if (key.isReadable()) {  //如果可以从服务器读取response数据
                readResponse(keyChannel);
                key.interestOps(SelectionKey.OP_WRITE);
            } else if (key.isWritable()) { //如果可以向服务器发送request数据
                sendRequest(keyChannel);
                key.interestOps(SelectionKey.OP_READ);
            }
        } catch (Exception e) {
            System.out.println("run error:" + e + module);
            socketHelper.close(keyChannel);
            throw new Exception(e);
        }
    }

    //向服务器发送信息
    private void sendRequest(SocketChannel keyChannel) throws Exception {
        try {
            Message request = messageList.removeReqFirst(); //获取队列中的数据
            String strs = request.getMsg();
            System.out.println(" send the request to the server =" + strs + module);
            //写入Socket
            socketHelper.writeSocket(strs.getBytes("UTF-8"), keyChannel);
        } catch (Exception e) {
            System.out.println(e + module);
            throw new Exception(e);
        }
    }

    //从服务器读取信息
    private void readResponse(SocketChannel keyChannel) throws Exception {
        try {
            byte[] bytes = socketHelper.readSocket(keyChannel); //从Socket读取数组字节
            System.out.println(" read the response from the server:" +
                    new String(bytes) + module);
            //实现其他处理，如在客户端屏幕显示服务器的反应信息
        } catch (Exception e) {
            System.out.println(e + module);
            throw new Exception(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting client..." + module);
        try {
            String url = "127.0.0.1";
            int port = 1234;
            TCPClient nonBlockingSocket = new TCPClient(url, port);
            nonBlockingSocket.start();
            System.out.println("create a request ..." + module);
            //连续发送100条信息到客户端
            for (int i = 0; i < 100; i++) {
                Message request = new Message(i + "hello I am Peng" + i);
                messageList.pushRequest(request);
            }
        } catch (Exception e) {
            System.out.println("Client start error:" + e);
        }
        System.out.println("Client started ..." + module);
    }
}


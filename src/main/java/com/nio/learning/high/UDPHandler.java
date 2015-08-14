package com.nio.learning.high;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class UDPHandler implements Runnable {

    private final static String module = UDPHandler.class.getName();
    private final DatagramChannel datagramChannel;
    private final SelectionKey key;

    private static final int READING = 0, SENDING = 1;
    private int state = READING;
    private SocketAddress address = null;

    public UDPHandler(SelectionKey key, DatagramChannel datagramChannel) throws
            IOException {
        this.datagramChannel = datagramChannel;
        this.key = key;
        System.out.println(" UDPHandler prepare ..." + module);
    }

    public void run() {  //线程运行方法
        System.out.println(" UDPHandler running ..." + module);
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (Exception ex) {
            System.out.println("readRequest .. error:" + ex + module);
            close(datagramChannel);
            key.cancel();
        }
    }

    private void close(DatagramChannel datagramChannel) {
        try {
            datagramChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从datagramChannel读取数据
    private void read() throws Exception {
        try {
            //这里应该是聊天服务器处理聊天信息的核心功能
            //下面只是测试用代码，直接从客户端读取字符串并打印出来
            ByteBuffer buffer = ByteBuffer.allocate(512);
            address = datagramChannel.receive(buffer);
            String str = new String(buffer.array());
            System.out.println("handlePendingReads() :" + str);
            state = SENDING;
            key.interestOps(SelectionKey.OP_WRITE);  //注册为可写事件
        } catch (Exception ex) {
            System.out.println("readRequest .. error:" + ex + module);
        }
    }

    //向datagramChannel写入数据
    private void send() throws Exception {
        try {
            //以下是测试发送数据
            String request = "come back";
            ByteBuffer buffer1 = ByteBuffer.wrap(request.getBytes());
            datagramChannel.send(buffer1, address);
            System.out.println("send :" + request);
            state = READING;
            key.interestOps(SelectionKey.OP_READ);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

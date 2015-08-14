package com.nio.learning.high;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class TCPClientSimple extends Thread {

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.socket().connect(new InetSocketAddress("localhost",8080));
//            for(int i=2;i>0;i--){
                channel.write(ByteBuffer.wrap("localhost".getBytes()));
//            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            System.out.println("Client start error:" + e);
        }
    }
}


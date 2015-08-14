package com.nio.learning.high;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class SocketHelper {
    public void close(SocketChannel keyChannel) {
        try {
            keyChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSocket(byte[] bytes, SocketChannel keyChannel) {
        try {
            keyChannel.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readSocket(SocketChannel keyChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(512);
        try {
            keyChannel.read(buffer);
            return  buffer.array();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}

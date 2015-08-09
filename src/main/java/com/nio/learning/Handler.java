package com.nio.learning;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by lefu on 15/8/8.
 * Description:
 */
public class Handler implements Runnable {
    final int MAXIN = 1024;
    final int MAXOUT = 1024;
    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    public Handler(Selector selector, SocketChannel c) throws IOException {
        socket = c;
        socket.configureBlocking(false);
        sk = socket.register(selector, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send() throws IOException {
        socket.write(output);
        if (outputIsComplete())
            sk.cancel();
    }

    private boolean outputIsComplete() {
        return false;
    }

    private void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void process() {

    }

    private boolean inputIsComplete() {
        return false;
    }
}

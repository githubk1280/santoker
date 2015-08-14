package com.nio.learning.simple;

import java.io.IOException;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class ClientEntry {

    public static void main(String args[]) throws IOException, InterruptedException {
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        new TimeClient("localhost", 8080).run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            ).start();
        }
    }
}

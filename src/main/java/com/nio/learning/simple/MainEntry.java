package com.nio.learning.simple;

import java.io.IOException;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class MainEntry {
    public static void main(String args[]) throws IOException {
        new Thread(new TimeServer(8080)).start();
    }
}

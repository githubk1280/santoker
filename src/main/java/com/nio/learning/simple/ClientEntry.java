package com.nio.learning.simple;

import java.io.IOException;

/**
 * Created by lefu on 15/8/9.
 * Description:
 */
public class ClientEntry {

    public static void main (String args[]) throws IOException {
        new TimeClient("localhost",8080).run();
    }
}

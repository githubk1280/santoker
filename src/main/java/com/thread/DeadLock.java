package com.thread;

/**
 * Created by
 * on 15/8/25.
 * Description:
 */
public class DeadLock {
    static Object north = new Object();
    static Object sourth = new Object();

    public static void main(String args[]) {
        new Thread(new North()).start();
        new Thread(new Sourth()).start();
    }

    static class North implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (north) {
                    synchronized (sourth) {
                        System.out.print("get n --> s");
                    }
                }
            }
        }
    }

    static class Sourth implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (sourth) {
                    synchronized (north) {
                        System.out.print("get s --> n");
                    }
                }
            }
        }
    }
}

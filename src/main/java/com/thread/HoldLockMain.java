package com.thread;

import java.util.Random;

/**
 * Created by
 * on 15/8/25.
 * Description:
 */
public class HoldLockMain {
    public static Object[] lock = new Object[10];

    static {
        for (int i = 0; i < 10; i++) {
            lock[i] = new Object();
        }
    }

    static class HoldTask implements Runnable {
        private int i;

        public HoldTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                while (Thread.interrupted()) {
                    synchronized (lock[i]) {
                        if (i % 2 == 0)
                            lock[i].wait(new Random().nextInt(10));
                        else
                            lock[i].notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main (String args[]){
        for(int i=0;i<lock.length*200000;i++){
            new Thread(new HoldTask(i)).start();
        }
    }
}

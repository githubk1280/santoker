package com.thread;

/**
 * Created by
 * on 15/8/28.
 * Description:
 */
public class ThreadLocalMain {
    public static void main(String args[]){
        for(int i=0;i<3;i++){
            new Thread(new Practise()).start();
        }
    }

    static class Practise implements Runnable{

        @Override
        public void run() {
            ThreadLocalMy.getTraceId();
            ThreadLocalMy.getTraceId();
        }
    }
}

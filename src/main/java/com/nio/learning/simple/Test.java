package com.nio.learning.simple;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class Test {
    public static void main (String args[]){
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println("main start");

        new Thread(new Runnable (){

            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(500);
                    System.out.println("run thread...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("point ...");
        new Runnable (){

            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(2000);
                    System.out.println("runn...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.run();

        System.out.println("main end");
    }
}

package com.nio.learning.high;

import java.util.LinkedList;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class MessageList {
    private final static String module = MessageList.class.getName();
    //Request信号的Queue
    private LinkedList requestList = new LinkedList();
    //Response信号的Queue
    private LinkedList responseList = new LinkedList();
    //使用单态模式保证当前JVM中只有一个MessageList实例
    private static MessageList messageList =  new MessageList();
    public static MessageList getInstance(){
        return messageList;
    }
    //加入数据
    public void pushRequest(Message requestMsg) {
        synchronized (requestList) {
            requestList.add(requestMsg);
            requestList.notifyAll();  //提醒锁在requestList的其他线程
        }
    }
    //取出Queue中第一数据
    public synchronized Message removeReqFirst() {
        synchronized (requestList) {
            // 如果没有数据，就锁定在这里
            while (requestList.isEmpty()) {
                try {
                    requestList.wait(); //等待解锁 等待加入数据后的提醒
                } catch (InterruptedException ie) {}
            }
            return (Message) requestList.removeFirst();
        }
    }
}


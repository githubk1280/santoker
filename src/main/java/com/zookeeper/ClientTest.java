package com.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by
 * on 15/9/4.
 * Description:
 */
public class ClientTest {
    public static void connect() throws IOException, KeeperException, InterruptedException {
        //connect
        ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("event happening :" + event);
            }
        });

        Stat tRoot = zk.exists("/test", true);
        if (null == tRoot) {
            //create root node
            zk.create("/test", "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
//            zk.delete("/test",0);
        }
        //create sub node
//        zk.create("/test/sub1", "sub1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/test/sub2", "sub2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.create("/test/sub3", "sub3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("children : " + zk.getChildren("/test", true));
    }

    public static void main(String args[]) throws IOException, KeeperException, InterruptedException {
        connect();
        System.in.read();
    }
}

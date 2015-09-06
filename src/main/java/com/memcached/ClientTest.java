package com.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * Created by
 * on 15/9/4.
 * Description:
 */
public class ClientTest {

    public static void main (String args[]){
        connect();
    }

    public static void connect(){
        String [] servers = new String[]{
                "127.0.0.1:11211"
        };
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);

        pool.initialize();

        MemCachedClient client = new MemCachedClient();
        client.add("chinese","中文x");
        System.out.println(client.get("chinese"));
    }

}

package com.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by
 * on 15/9/1.
 * Description:
 */
public class RepeatableEntity {

    public static void main(String args[]) throws IOException {
//        repeat();
        nonRepeat();
    }

    public static void repeat() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            entity = new BufferedHttpEntity(entity);
            System.out.println(IOUtils.toString(entity.getContent()).length());
            System.out.println("===================================");
            System.out.println(IOUtils.toString(entity.getContent()));
        }
    }

    public static void nonRepeat() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            System.out.println(IOUtils.toString(entity.getContent()).length());
            System.out.println("===================================");
            System.out.println(IOUtils.toString(entity.getContent()));
        }
    }

}

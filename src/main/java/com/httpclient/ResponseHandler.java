package com.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by
 * on 15/9/2.
 * Description:
 */
public class ResponseHandler {

    public static void main(String args[]) {
        test("http://www.baidu.com", 1000, new CallBack() {
            @Override
            public void onSuccess(String code, InputStream result) {
                System.out.println(code);
                try {
                    InputStreamReader reader = new InputStreamReader(result);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    for (int i = 0; i < 10; i++) {
                        System.out.println(bufferedReader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String code, InputStream result) {
                System.out.println("failure " + code);
                try {
                    InputStreamReader reader = new InputStreamReader(result);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    for (int i = 0; i < 10; i++) {
                        System.out.println(bufferedReader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onExceptionCaught(Exception e) {

            }
        });
    }

    public static void test(String url, int timeout, final CallBack callBack) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            post.setConfig(RequestConfig.custom().
                    setConnectTimeout(timeout).
                    setSocketTimeout(timeout).
                    setConnectionRequestTimeout(timeout).
                    build());
            // Create a custom response handler
            org.apache.http.client.ResponseHandler responseHandler = new org.apache.http.client.ResponseHandler() {

                @Override
                public Object handleResponse(
                        final HttpResponse response) throws IOException {
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();
                    if (null == entity)
                        throw new ClientProtocolException("Response is null");
                    if (statusLine.getStatusCode() < 300) {
                        callBack.onSuccess(String.valueOf(statusLine.getStatusCode()), entity.getContent());
                    } else {
                        callBack.onFailure(String.valueOf(statusLine.getStatusCode()), entity.getContent());

                    }
                    return null;
                }

            };
            // execute
            httpclient.execute(post, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface CallBack {
        void onSuccess(String code, InputStream result);

        void onFailure(String code, InputStream result);

        void onExceptionCaught(Exception e);
    }
}

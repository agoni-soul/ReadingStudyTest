package com.readingstudytest.Util;

import com.readingstudytest.IInterface.HttpCallBackListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SendPostUtil {

    public static void sendRequestHttp(final String address, final String requestMethod, String postData,
                                     final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(requestMethod);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();

                    //下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if(listener != null){
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if(listener != null){
                        //回调Error()方法
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void postRequestHttp(final String address, final String requestMethod, String postData,
                                   final HttpCallBackListener listener){
        StringBuilder response = new StringBuilder();
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        String line = "";
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(postData);

            reader = new BufferedReader(new InputStreamReader(
                    ((HttpURLConnection) connection).getInputStream(), "gb2312"));
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            if(listener != null){
                //回调onFinish()方法
                listener.onFinish(response.toString());
            }
        }catch (Exception e){
            if(listener != null){
                //回调Error()方法
                listener.onError(e);
            }
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    public static void sendRequestOkHttp(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}

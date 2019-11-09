package com.readingstudytest.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.readingstudytest.MainActivity;
import com.readingstudytest.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment_Android extends Fragment implements View.OnClickListener{

    private TextView dic_android_content_item;
    private final static String address = "https://www.wanandroid.com/article/list/0/json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dic_layout_android, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initView();
        sendRequestWithOkHttp(address, "GET");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(){
        dic_android_content_item = (TextView) getActivity().findViewById(R.id.dic_android_content_item);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }

    private void sendRequestWithOkHttp(final String address, final String requestMethod){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(requestMethod);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    };
                    showResponse(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try{
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void sendRequestWithOkHttp(final String address){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(address)
                            .build();
                    Response response = client.newCall(request).execute();
                    if(!response.isSuccessful()){
                        throw new IOException("Unexpected code" + response);
                    }
                    String responseData = response.body().string();
                    Headers responseHeaders = response.headers();
                    for(int i = 0; i < responseHeaders.size(); i ++){
                        System.out.println(responseHeaders.name(i) + ":" + responseHeaders.value(i));
                    }
                    System.out.println(responseData);
//                    showResponse(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String responseData){
//        Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
//        String author = null;
//        String chapterName = null;
//        String superChapterName = null;
//        String title = null;
//        Boolean collect = null;
//        Integer niceDate = null;
//
//        try{
//            JSONArray jsonArray = new JSONArray(responseData);
//            System.out.println(jsonArray);
//            System.out.println(jsonArray.length());
//            for(int i = 0; i < Math.min(jsonArray.length(), 5); i ++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                author = jsonObject.getString("author");
//                niceDate = jsonObject.getInt("niceDate");
//                chapterName = jsonObject.getString("chapterName");
//                superChapterName = jsonObject.getString("superChapterName");
//                title = jsonObject.getString("title");
//                collect = jsonObject.getBoolean("collect");
//                Log.d("HomeFragment_Android", "author is " + author);
//                Log.d("HomeFragment_Android", "chapter is " + chapterName + " / " + superChapterName);
//                Log.d("HomeFragment_Android", "title is " + title);
//                Log.d("HomeFragment_Android", "collect is " + collect);
//                Log.d("HomeFragment_Android", "niceDate is " + niceDate);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                dic_android_content_item.setText(responseData);
            }
        });
    }
}

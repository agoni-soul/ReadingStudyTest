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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        sendRequestWithOkHttp(address);
    }

    private void initView(){
        dic_android_content_item = (TextView) getActivity().findViewById(R.id.dic_android_content_item);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
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
                    String responseData = response.body().string();
                    showResponse(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String responseData){
        Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                String author = null;
                String chapterName = null;
                String superChapterName = null;
                String title = null;
                Boolean collect = null;
                Integer niceDate = null;

                try{
                    JSONArray jsonArray = new JSONArray(responseData);
                    for(int i = 0; i < Math.min(jsonArray.length(), 5); i ++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        author = jsonObject.getString("author");
                        niceDate = jsonObject.getInt("niceDate");
                        chapterName = jsonObject.getString("chapterName");
                        superChapterName = jsonObject.getString("superChapterName");
                        title = jsonObject.getString("title");
                        collect = jsonObject.getBoolean("collect");
                        Log.d("HomeFragment_Android", "author is " + author);
                        Log.d("HomeFragment_Android", "chapter is " + chapterName + " / " + superChapterName);
                        Log.d("HomeFragment_Android", "title is " + title);
                        Log.d("HomeFragment_Android", "collect is " + collect);
                        Log.d("HomeFragment_Android", "niceDate is " + niceDate);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

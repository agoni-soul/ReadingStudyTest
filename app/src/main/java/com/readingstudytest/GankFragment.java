package com.readingstudytest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.GankBodyAdapter;
import com.readingstudytest.adapter.GankHeaderAdapter;
import com.readingstudytest.bean.GankBodyContentBean;
import com.readingstudytest.bean.GankHeaderContentBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankFragment extends Fragment {
    private FloatingActionButton fab;
    private static Activity mActivity;
    private View localView;

    //Header的变量
    private RecyclerView rvGankHeader;
    private ArrayList<String> gankHeaderList = new ArrayList<>();
    private GankHeaderAdapter gankHeaderAdapter;
    private LinearLayoutManager layoutManagerHeader;

    //Body的变量声明
    private RecyclerView rvGankBody;
    private LinearLayoutManager layoutManagerGank;
    private GankBodyAdapter gankBodyAdapter;
    private static ArrayList<GankBodyContentBean.GankBodyDetailBean> gankBodyContent = new ArrayList<>();

    //创建handler机制用于在TodoHeaderAdapter中点击事件发送消息
    public static Handler handlerGank = new Handler(){
        public void handleMessage(Message msg){
            GankFragment gankFragment = new GankFragment();
            if(mActivity != null){
                gankFragment.downloadBodyContent(msg.obj.toString());
            }
        }
    };

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.gank_layout, container, false);
        initView();
        fab.hide();
        downloadHeaderData();
        return localView;
    }

    public void initView(){
        fab = (FloatingActionButton) mActivity.findViewById(R.id.fab_android_home_fragment);
        rvGankHeader = (RecyclerView) mActivity.findViewById(R.id.rv_gank_header);
        layoutManagerHeader = new LinearLayoutManager(mActivity);
    }

    public void initViewBody(){
        rvGankBody = (RecyclerView) mActivity.findViewById(R.id.rv_gank_body);
        layoutManagerGank = new LinearLayoutManager(mActivity);
    }

    private void downloadHeaderData(){
        if(RequestDataByRetrofit.getOkHttpClientInstance() != null){
            retrofit2.Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .client(RequestDataByRetrofit.getOkHttpClientInstance())
                    .build();
            GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
            Call<GankHeaderContentBean> call = getRequestInterface.getGankHeaderContent();
            call.enqueue(new Callback<GankHeaderContentBean>() {
                @Override
                public void onResponse(@NonNull Call<GankHeaderContentBean> call,
                                       @Nullable Response<GankHeaderContentBean> response) {
                    //判断result数据是否为空
                    if (response.body() != null) {
                        Log.d("GankHeaderData", response.body().getCategory().size() + "");
                        for(int i = 0; i < response.body().getCategory().size(); i ++){
                            gankHeaderList.add(response.body().getCategory().get(i));
                            Log.d("GankHeaderData", gankHeaderList.get(i));
                        }
                        updateUiHeaderData();
                    }
                }

                @Override
                public void onFailure(@Nullable Call<GankHeaderContentBean> call, Throwable t) {
                    Log.e("GankHeaderData", t.getMessage());
                }
            });
        }
    }
    private void updateUiHeaderData(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutManagerHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvGankHeader.setLayoutManager(layoutManagerHeader);
                gankHeaderAdapter = new GankHeaderAdapter(gankHeaderList);
                rvGankHeader.setAdapter(gankHeaderAdapter);
                if(gankHeaderList.size() > 0){
                    downloadBodyContent(gankHeaderList.get(0));
                }
            }
        });
    }

    private void downloadBodyContent(String itemCategory){
        Log.d("GankBodyContent", itemCategory);
        if(RequestDataByRetrofit.getOkHttpClientInstance() != null){
            retrofit2.Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .client(RequestDataByRetrofit.getOkHttpClientInstance())
                    .build();
            GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
            Call<GankBodyContentBean> call = getRequestInterface.getGankBodyContent(itemCategory);
            call.enqueue(new Callback<GankBodyContentBean>() {
                @Override
                public void onResponse(@NonNull Call<GankBodyContentBean> call,
                                       @Nullable Response<GankBodyContentBean> response) {
                    //判断result数据是否为空
                    if (response.body() != null) {
                        Log.d("GankBodyContent", response.body().getResults().size() + "");
                        for(int i = 0; i < response.body().getResults().size(); i ++){
                            gankBodyContent.add(response.body().getResults().get(i));
                        }
                        updateUiBodyData();
                    }
                }

                @Override
                public void onFailure(@Nullable Call<GankBodyContentBean> call, Throwable t) {
                    Log.e("GankBodyData", t.getMessage());
                }
            });
        }
    }

    private void updateUiBodyData(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initViewBody();
                layoutManagerGank.setOrientation(RecyclerView.VERTICAL);
                rvGankBody.setLayoutManager(layoutManagerGank);
                gankBodyAdapter = new GankBodyAdapter(gankBodyContent);
                rvGankBody.setAdapter(gankBodyAdapter);
            }
        });
    }
}
package com.readingstudytest.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.HomeAndroidContentAdapter;
import com.readingstudytest.adapter.InfoHeaderAdapter;
import com.readingstudytest.bean.*;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AndroidFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvHomeAndroid;
    private FloatingActionButton fab;

    private ArrayList<ArticleBean.ArticleDetailBean> androidChapterContent =
            new ArrayList<>();
    private HomeAndroidContentAdapter homeAndroidContentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment_android, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initView();
        fab.show();
        if(androidChapterContent == null || androidChapterContent.size() == 0){
            downloadAndroidChapterContent();
        }
    }

    public void downloadAndroidChapterContent(){
//        retrofit2.Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.wanandroid.com/")
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
//                .build();
//        GetRequestInterface service = retrofit.create(GetRequestInterface.class);
//        Call<BaseBean<ArticleBean>> call = service.getAndroidContent(0);
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseBean<ArticleBean>> call = getRequestInterface.getAndroidContent(0);

        call.enqueue(new Callback<BaseBean<ArticleBean>>() {
            @Override
            public void onResponse(Call<BaseBean<ArticleBean>> call,
                                   Response<BaseBean<ArticleBean>> response) {
                BaseBean<ArticleBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("AndroidChapter", result.getData().getDatas().size() + "");
                    androidChapterContent = result.getData().getDatas();
                    updateUiAndroidChapterData();
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("AndroidChapterErrorInfo", t.getMessage());
            }
        });
    }

    private void updateUiAndroidChapterData(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                rvHomeAndroid.setLayoutManager(layoutManager);
                homeAndroidContentAdapter = new HomeAndroidContentAdapter(androidChapterContent);
                rvHomeAndroid.setAdapter(homeAndroidContentAdapter);
            }
        });
    }

    public void initView(){
        rvHomeAndroid = (RecyclerView) getActivity().findViewById(R.id.rl_home_android);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_android_home_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }
}

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
import com.readingstudytest.adapter.HomeAndroidContentAdapter;
import com.readingstudytest.bean.HomeAndroidDataBean;
import com.readingstudytest.bean.HomeAndroidDatasBean;
import com.readingstudytest.bean.HomeAndroidDatasTagsBean;
import com.readingstudytest.bean.BaseBean;
import com.readingstudytest.Util.RequestDataByRetrofit;

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

    //网络请求数据
    private RequestDataByRetrofit retrofit;
    private GetRequestInterface getRequestInterface;

    private ArrayList<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>> androidChapterContent =
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

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rvHomeAndroid.setLayoutManager(layoutManager);
            homeAndroidContentAdapter = new HomeAndroidContentAdapter(androidChapterContent);
            rvHomeAndroid.setAdapter(homeAndroidContentAdapter);
        }
    }

    public void downloadAndroidChapterContent(){
        retrofit = RequestDataByRetrofit.getInstance();
        getRequestInterface = retrofit.getIGetRequestInterface();

        Call<BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>>> call =
                getRequestInterface.getAndroidContent(0);
        call.enqueue(new Callback<BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>>>() {
            @Override
            public void onResponse(Call<BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>>> call,
                                   Response<BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>>> response) {
                BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("AndroidChapter", result.getData().toString());
                    androidChapterContent = result.getData().getDatas();
                }
            }

            @Override
            public void onFailure(Call<BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>>> call, Throwable t) {
                Log.e("tag", t.getMessage());
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

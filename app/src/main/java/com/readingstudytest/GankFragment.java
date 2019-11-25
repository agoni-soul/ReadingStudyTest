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
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankFragment extends Fragment {
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
    private ArrayList<GankBodyContentBean.GankBodyDetailBean> gankBodyContent = new ArrayList<>();

    //Retrofit和RxJava的变量
    private static RequestDataByRetrofit retrofit;
    private static GetRequestInterface getRequestInterface;

    //创建handler机制用于在TodoHeaderAdapter中点击事件发送消息
    public static Handler handlerGank = new Handler(){
        public void handleMessage(Message msg){
            GankFragment gankFragment = new GankFragment();
            gankFragment.downloadBodyContentByRetrofitAndRxJava(msg.obj.toString());
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
        if(retrofit == null || getRequestInterface == null){
            initRequestDataByRetrofitAndIGetRequestInterface();
        }
        downloadHeaderDataByRetrofitAndRxJava();
        return localView;
    }

    public void initView(){
        rvGankHeader = (RecyclerView) mActivity.findViewById(R.id.rv_gank_header);
        layoutManagerHeader = new LinearLayoutManager(mActivity);
        rvGankBody = (RecyclerView) mActivity.findViewById(R.id.rv_gank_body);
        layoutManagerGank = new LinearLayoutManager(mActivity);
    }

    public void initViewBody(){
        rvGankBody = (RecyclerView) mActivity.findViewById(R.id.rv_gank_body);
        layoutManagerGank = new LinearLayoutManager(mActivity);
    }

    private static void initRequestDataByRetrofitAndIGetRequestInterface(){
        //步骤4：创建Retrofit对象
        retrofit = RequestDataByRetrofit.getInstanceCustom(MainActivity.GankFragmentURL,
                RequestDataByRetrofit.getOkHttpClientInstance(),
                RxJava2CallAdapterFactory.create());

        // 步骤5：创建 网络请求接口 的实例
        getRequestInterface = retrofit.getIGetRequestInterface();
    }

    //Retrofit与RxJava结合
    private void downloadHeaderDataByRetrofitAndRxJava(){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<GankHeaderContentBean> observable = getRequestInterface.getGankHeaderContentByRxJava();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<GankHeaderContentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("GankHeader", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(GankHeaderContentBean result) {
                        // 步骤8：对返回的数据进行处理
                        Log.d("GankHeaderData", result.getCategory().size() + "");
                        for(int i = 0; i < result.getCategory().size(); i ++){
                            gankHeaderList.add(result.getCategory().get(i));
//                            Log.d("GankHeaderData", gankHeaderList.get(i));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("GankHeader", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("GankHeader", "请求成功");
                        updateUiHeaderData();
                    }
                });
    }
    private void updateUiHeaderData(){
        layoutManagerHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGankHeader.setLayoutManager(layoutManagerHeader);
        gankHeaderAdapter = new GankHeaderAdapter(gankHeaderList);
        rvGankHeader.setAdapter(gankHeaderAdapter);
        if(gankHeaderList.size() > 0){
            downloadBodyContentByRetrofitAndRxJava(gankHeaderList.get(0));
        }
    }
    //仅仅使用Retrofit
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

    private void downloadBodyContentByRetrofitAndRxJava(String itemCategory){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<GankBodyContentBean> observable = getRequestInterface.getGankBodyContentByRxJava(itemCategory);

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<GankBodyContentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("GankBodyContent", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(GankBodyContentBean result) {
                        // 步骤8：对返回的数据进行处理
                        Log.d("GankBodyContent", result.getResults().size() + "");
                        for(int i = 0; i < result.getResults().size(); i ++){
                            gankBodyContent.add(result.getResults().get(i));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("GankBodyContent", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("GankBodyContent", "请求成功");
                        updateUiBodyData();
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
        initViewBody();
        layoutManagerGank.setOrientation(RecyclerView.VERTICAL);
        rvGankBody.setLayoutManager(layoutManagerGank);
        gankBodyAdapter = new GankBodyAdapter(gankBodyContent);
        rvGankBody.setAdapter(gankBodyAdapter);
    }
}
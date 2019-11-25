package com.readingstudytest.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.MainActivity;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.HomeBodyAdapter;
import com.readingstudytest.bean.*;
import com.readingstudytest.bean.BaseBean;

import java.util.ArrayList;

//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//import io.reactivex.Observable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AndroidFragment extends Fragment implements View.OnClickListener{
    private static View localView;

    private RecyclerView rvHomeAndroid;

    private ArrayList<ArticleBean.ArticleDetailBean> androidChapterContent =
            new ArrayList<>();

    //该变量放在updateUiAndroidChapterData()方法中会报错
    private LinearLayoutManager layoutManagerAndroid;

    private HomeBodyAdapter androidBodyAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.home_fragment_android, container, false);

        initView();
//        downloadAndroidChapterContent(0);
        downloadAndroidChapterContentByRxJava(0);

        return localView;
    }

    public void initView(){
        //若点击公众号模板，再点击home模板，该控件就会报错，为空；但是mActivity不为空，不知道这个bug是什么！！
        rvHomeAndroid = (RecyclerView) localView.findViewById(R.id.rl_home_android);
        layoutManagerAndroid = new LinearLayoutManager(localView.getContext());
    }

    private void downloadAndroidChapterContentByRxJava(int page){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstanceCustom(MainActivity.BaseURL, RxJava2CallAdapterFactory.create());
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Observable<BaseBean<ArticleBean>> observable = getRequestInterface.getAndroidContentByRxJava(page);

        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseBean<ArticleBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("AndroidChapter", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseBean<ArticleBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            Log.d("AndroidChapter", result.getData().getDatas().size() + "");
                            androidChapterContent = result.getData().getDatas();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AndroidChapter", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("AndroidChapter", "请求成功");
                        updateUiAndroidChapterData();
                    }
                });
    }
    private void downloadAndroidChapterContent(int page){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseBean<ArticleBean>> call = getRequestInterface.getAndroidContent(page);

        call.enqueue(new Callback<BaseBean<ArticleBean>>() {
            @Override
            public void onResponse(@Nullable Call<BaseBean<ArticleBean>> call,
                                   @Nullable Response<BaseBean<ArticleBean>> response) {
                BaseBean<ArticleBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("AndroidChapter", result.getData().getDatas().size() + "");
                    androidChapterContent = result.getData().getDatas();
//                    updateUiAndroidChapterData();
                }
            }

            @Override
            public void onFailure(@Nullable Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("AndroidChapterErrorInfo", t.getMessage());
            }
        });
    }
    private void updateUiAndroidChapterData(){
        layoutManagerAndroid.setOrientation(RecyclerView.VERTICAL);
        rvHomeAndroid.setLayoutManager(layoutManagerAndroid);
        androidBodyAdapter = new HomeBodyAdapter(androidChapterContent);
        rvHomeAndroid.setAdapter(androidBodyAdapter);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }
}

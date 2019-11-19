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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AndroidFragment extends Fragment implements View.OnClickListener{
    public static Activity mActivity;
    private View localView;

    private RecyclerView rvHomeAndroid;
    private FloatingActionButton fab;

    private ArrayList<ArticleBean.ArticleDetailBean> androidChapterContent =
            new ArrayList<>();

    //该变量放在updateUiAndroidChapterData()方法中会报错
    private LinearLayoutManager layoutManagerAndroid;

    private HomeBodyAdapter androidBodyAdapter;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.home_fragment_android, container, false);

        initView();
        fab.show();
        downloadAndroidChapterContent(0);
//        useRxJavaUpdateUI();

        return localView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initView(){
        //若点击公众号模板，再点击home模板，该控件就会报错，为空；但是mActivity不为空，不知道这个bug是什么！！
        rvHomeAndroid = (RecyclerView) localView.findViewById(R.id.rl_home_android);
        fab = (FloatingActionButton) MainActivity.mActivity.findViewById(R.id.fab_android_home_fragment);
        layoutManagerAndroid = new LinearLayoutManager(mActivity);
    }

    private void useRxJavaUpdateUI(){
        //使用RxJava第三方库
        //创建被观察者
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                downloadAndroidChapterContent();
//            }
//        });
//
//        //创建观察者
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                updateUiAndroidChapterData();
//            }
//        };
//
//        //subscribeOn()指定的是发送事件的线程，observeOn()指定的是接受事件的线程
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread());
////                .subscribe(consumer);
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
                    updateUiAndroidChapterData();
                }
            }

            @Override
            public void onFailure(@Nullable Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("AndroidChapterErrorInfo", t.getMessage());
            }
        });
    }

    private void updateUiAndroidChapterData(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutManagerAndroid.setOrientation(RecyclerView.VERTICAL);
                rvHomeAndroid.setLayoutManager(layoutManagerAndroid);
                androidBodyAdapter = new HomeBodyAdapter(androidChapterContent);
                rvHomeAndroid.setAdapter(androidBodyAdapter);
            }
        });

//        layoutManagerAndroid.setOrientation(RecyclerView.VERTICAL);
//        rvHomeAndroid.setLayoutManager(layoutManagerAndroid);
//        androidBodyAdapter = new HomeBodyAdapter(androidChapterContent);
//        rvHomeAndroid.setAdapter(androidBodyAdapter);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }
}

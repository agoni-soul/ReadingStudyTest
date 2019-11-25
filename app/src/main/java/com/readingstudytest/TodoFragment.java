package com.readingstudytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.TodoBodyAdapter;
import com.readingstudytest.adapter.TodoHeaderAdapter;
import com.readingstudytest.bean.*;
import com.readingstudytest.home.InfoFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoFragment extends Fragment {
    private static Activity mActivity;
    private View localView;

    //Header的变量
    private RecyclerView rvTodoHeader;
    private ArrayList<ItemNameAndIdBean> todoHeaderList = new ArrayList<>();
    private TodoHeaderAdapter todoHeaderAdapter;
    private LinearLayoutManager layoutManagerHeader;

    //Body的变量声明
    private RecyclerView rvTodoBody;
    private LinearLayoutManager layoutManagerTodo;
    private TodoBodyAdapter todoBodyAdapter;
    private static ArrayList<ArticleBean.ArticleDetailBean> todoBodyContent = new ArrayList<>();

    //Retrofit和RxJava的变量
    private static RequestDataByRetrofit retrofit;
    private static GetRequestInterface getRequestInterface;

    //创建handler机制用于在TodoHeaderAdapter中点击事件发送消息
    public static Handler handlerTodo = new Handler(){
        public void handleMessage(Message msg){
            TodoFragment todoFragment = new TodoFragment();
            todoFragment.downloadBodyContentByRetrofitAndRxJava(msg.arg1);
        }
    };

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.todo_layout, container, false);
        initView();
        if(retrofit == null || getRequestInterface == null){
            initRequestDataByRetrofitAndIGetRequestInterface();
        }
        downloadHeaderDataByRetrofitAndRxJava();
        return localView;
    }

    private static void initRequestDataByRetrofitAndIGetRequestInterface(){
        //步骤4：创建Retrofit对象
        retrofit = RequestDataByRetrofit.getInstanceCustom(MainActivity.BaseURL, RxJava2CallAdapterFactory.create());

        // 步骤5：创建 网络请求接口 的实例
        getRequestInterface = retrofit.getIGetRequestInterface();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void initView(){
        rvTodoHeader = (RecyclerView) mActivity.findViewById(R.id.rv_todo_header);
        layoutManagerHeader = new LinearLayoutManager(mActivity);
    }

    private void initViewBody(){
        rvTodoBody = (RecyclerView) mActivity.findViewById(R.id.rv_todo_body);
        layoutManagerTodo = new LinearLayoutManager(mActivity);
    }

    private void downloadHeaderDataByRetrofitAndRxJava(){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<BaseArrayBean<WxArticleBean>> observable = getRequestInterface.getTodoChaptersContentByRxJava();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseArrayBean<WxArticleBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("TodoHeaderData", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseArrayBean<WxArticleBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            Log.d("TodoHeaderData", result.getData().size() + "");
                            for(int i = 0; i < result.getData().size(); i ++){
                                todoHeaderList.add(new ItemNameAndIdBean(result.getData().get(i).getId(),
                                        result.getData().get(i).getName()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TodoHeaderData", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TodoHeaderData", "请求成功");
                        updateUiHeaderData();
                    }
                });
    }
    private void downloadHeaderData(){
        Call<BaseArrayBean<WxArticleBean>> call = getRequestInterface.getTodoChaptersContent();
        call.enqueue(new Callback<BaseArrayBean<WxArticleBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<WxArticleBean>> call,
                                   Response<BaseArrayBean<WxArticleBean>> response) {
                ArrayList<WxArticleBean> result = response.body().getData();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("TodoHeaderData", result.size() + "");
                    for(int i = 0; i < result.size(); i ++){
                        todoHeaderList.add(new ItemNameAndIdBean(result.get(i).getId(),
                                result.get(i).getName()));
                        Log.d("TodoHeaderData", todoHeaderList.get(i).getName() + "\t" + todoHeaderList.get(i).getId());
                    }
                    updateUiHeaderData();
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<WxArticleBean>> call, Throwable t) {
                Log.e("TodoHeaderData", t.getMessage());
            }
        });
    }
    private void updateUiHeaderData(){
        layoutManagerHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTodoHeader.setLayoutManager(layoutManagerHeader);
        todoHeaderAdapter = new TodoHeaderAdapter(todoHeaderList);
        rvTodoHeader.setAdapter(todoHeaderAdapter);
        if(todoHeaderList.size() > 0){
            downloadBodyContentByRetrofitAndRxJava(todoHeaderList.get(0).getId());
        }
    }

    private void downloadBodyContentByRetrofitAndRxJava(int id){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<BaseBean<ArticleBean>> observable = getRequestInterface.getTodoBodyContentByRxJava(id);

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseBean<ArticleBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("TodoBodyContent", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseBean<ArticleBean> result) {
                        // 步骤8：对返回的数据进行处理
                        //判断result数据是否为空
                        if (result != null) {
                            Log.d("TodoBodyContent", result.getData().getDatas().size() + "");
                            todoBodyContent = result.getData().getDatas();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TodoBodyContent", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TodoBodyContent", "请求成功");
                        if(todoBodyContent.size() > 0) updateUiTodoBodyContent();
                    }
                });
    }
    private void downloadBodyContent(int id){
        Call<BaseBean<ArticleBean>> call = getRequestInterface.getTodoBodyContent(id);
        call.enqueue(new Callback<BaseBean<ArticleBean>>() {
            @Override
            public void onResponse(Call<BaseBean<ArticleBean>> call,
                                   Response<BaseBean<ArticleBean>> response) {
                BaseBean<ArticleBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("TodoBodyContent", result.getData().getDatas().size() + "");
                    todoBodyContent = result.getData().getDatas();
                    updateUiTodoBodyContent();
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("TodoBodyContent", t.getMessage());
            }
        });
    }
    private void updateUiTodoBodyContent(){
        initViewBody();
        layoutManagerTodo.setOrientation(RecyclerView.VERTICAL);
        rvTodoBody.setLayoutManager(layoutManagerTodo);
        todoBodyAdapter = new TodoBodyAdapter(todoBodyContent);
        rvTodoBody.setAdapter(todoBodyAdapter);
    }
}

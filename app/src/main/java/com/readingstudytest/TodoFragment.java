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
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.TodoBodyAdapter;
import com.readingstudytest.adapter.TodoHeaderAdapter;
import com.readingstudytest.bean.*;
import com.readingstudytest.home.InfoFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoFragment extends Fragment {
    private static Activity mActivity;
    private View localView;

    private FloatingActionButton fab;

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

    //创建handler机制用于在InfoHeaderAdapter中点击事件发送消息
    public static Handler handlerInfo = new Handler(){
        public void handleMessage(Message msg){
            TodoFragment todoFragment = new TodoFragment();
            if(mActivity != null){
                todoFragment.downloadBodyContent(msg.arg1);
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
        localView = inflater.inflate(R.layout.todo_layout, container, false);
        return localView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initView();
        downloadHeaderData();
    }

    public void initView(){
        fab = (FloatingActionButton) mActivity.findViewById(R.id.fab_android_home_fragment);
        rvTodoHeader = (RecyclerView) mActivity.findViewById(R.id.rv_todo_header);
        layoutManagerHeader = new LinearLayoutManager(mActivity);
    }

    public void initViewBody(){
        rvTodoBody = (RecyclerView) mActivity.findViewById(R.id.rv_todo_body);
        layoutManagerTodo = new LinearLayoutManager(mActivity);
    }

    private void downloadHeaderData(){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseArrayBean<WxArticleBean>> call = getRequestInterface.getTodoChaptersContent();
        call.enqueue(new Callback<BaseArrayBean<WxArticleBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<WxArticleBean>> call,
                                   Response<BaseArrayBean<WxArticleBean>> response) {
                ArrayList<WxArticleBean> result = response.body().getData();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("InfoProjectTree", result.size() + "");
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
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutManagerHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvTodoHeader.setLayoutManager(layoutManagerHeader);
                todoHeaderAdapter = new TodoHeaderAdapter(todoHeaderList);
                rvTodoHeader.setAdapter(todoHeaderAdapter);
            }
        });
    }

    private void downloadBodyContent(int id){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
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
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initViewBody();
                layoutManagerTodo.setOrientation(RecyclerView.VERTICAL);
                rvTodoBody.setLayoutManager(layoutManagerTodo);
                todoBodyAdapter = new TodoBodyAdapter(todoBodyContent);
                rvTodoBody.setAdapter(todoBodyAdapter);
            }
        });
    }
}

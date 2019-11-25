package com.readingstudytest.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readingstudytest.HomeFragment;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.MainActivity;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.HomeBodyAdapter;
import com.readingstudytest.adapter.InfoHeaderAdapter;
import com.readingstudytest.bean.*;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment implements View.OnClickListener{
    private static View localView;

    //Header的变量声明
    private RecyclerView rvInfoHeader;
    private ArrayList<ItemNameAndIdBean> projectTreeDatas = new ArrayList<>();
    private InfoHeaderAdapter infoHeaderAdapter;
    //该变量放在updateUiAndroidChapterData()方法中会报错
    private LinearLayoutManager layoutManagerHeader;

    //Body的变量声明
    private RecyclerView rvInfoBody;
    private LinearLayoutManager layoutManagerInfo;
    private HomeBodyAdapter infoBodyAdapter;
    private ArrayList<ArticleBean.ArticleDetailBean> infoBodyContent = new ArrayList<>();

    //Retrofit和RxJava的变量
    private static RequestDataByRetrofit retrofit;
    private static GetRequestInterface getRequestInterface;

    //创建handler机制用于在InfoHeaderAdapter中点击事件发送消息
    public static Handler handlerInfo = new Handler(){
        public void handleMessage(Message msg){
            InfoFragment infoFragment = new InfoFragment();
            infoFragment.downloadBodyContentByRxJava(msg.arg1);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.home_fragment_info, container, false);
        initView();
        initRequestDataByRetrofitAndIGetRequestInterface();
        downloadProjectTreeDataByRxJava();
        return localView;
    }

    private static void initRequestDataByRetrofitAndIGetRequestInterface(){
        //步骤4：创建Retrofit对象
        retrofit = RequestDataByRetrofit.getInstanceCustom(MainActivity.BaseURL, RxJava2CallAdapterFactory.create());

        // 步骤5：创建 网络请求接口 的实例
        getRequestInterface = retrofit.getIGetRequestInterface();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }

    private void initView(){
        rvInfoHeader = (RecyclerView) localView.findViewById(R.id.rv_info_header);
        layoutManagerHeader = new LinearLayoutManager(localView.getContext());
        rvInfoBody = (RecyclerView) localView.findViewById(R.id.rv_info_body);
        layoutManagerInfo = new LinearLayoutManager(localView.getContext());
    }

    private void initViewBody(){
        rvInfoBody = (RecyclerView) localView.findViewById(R.id.rv_info_body);
        layoutManagerInfo = new LinearLayoutManager(localView.getContext());
    }

    //获取Header的信息
    private void downloadProjectTreeDataByRxJava(){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<BaseArrayBean<ProjectTreeDataBean>> observable = getRequestInterface.getInfoProjectTreeContentByRxJava();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseArrayBean<ProjectTreeDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("InfoProjectTree", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseArrayBean<ProjectTreeDataBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            for(int i = 0; i < result.getData().size(); i ++){
                                projectTreeDatas.add(new ItemNameAndIdBean(result.getData().get(i).getId(),
                                        result.getData().get(i).getName()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("InfoProjectTree", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("InfoProjectTree", "请求成功");
                        updateUiProjectTreeData();
                    }
                });
    }
    private void downloadProjectTreeData(){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseArrayBean<ProjectTreeDataBean>> call = getRequestInterface.getInfoProjectTreeContent();
        call.enqueue(new Callback<BaseArrayBean<ProjectTreeDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<ProjectTreeDataBean>> call,
                                   Response<BaseArrayBean<ProjectTreeDataBean>> response) {
                //判断response.body()数据是否为空
                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().size(); i ++){
                        projectTreeDatas.add(new ItemNameAndIdBean(response.body().getData().get(i).getId(),
                                response.body().getData().get(i).getName()));
                        Log.d("InfoProjectTree", projectTreeDatas.get(i).getName() + "\t" + projectTreeDatas.get(i).getId());
                    }
//                    updateUiProjectTreeData();
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<ProjectTreeDataBean>> call, Throwable t) {
                Log.e("ProjectTreeErrorInfo", t.getMessage());
            }
        });
    }
    private void updateUiProjectTreeData(){
        layoutManagerHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvInfoHeader.setLayoutManager(layoutManagerHeader);
        infoHeaderAdapter = new InfoHeaderAdapter(projectTreeDatas);
        rvInfoHeader.setAdapter(infoHeaderAdapter);
        if(projectTreeDatas.size() > 0){
            downloadBodyContentByRxJava(projectTreeDatas.get(0).getId());
        }
    }

    //获取Body的信息
    private void downloadBodyContentByRxJava(int id){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<BaseBean<ArticleBean>> observable = getRequestInterface.getInfoBodyContentByRxJava(id);

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseBean<ArticleBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("InfoContent", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseBean<ArticleBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            Log.d("InfoContent", result.getData().getDatas().size() + "");
                            infoBodyContent = result.getData().getDatas();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("InfoContent", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("InfoContent", "请求成功");
                        updateUiInfoBodyContent();
                    }
                });
    }
    private void downloadBodyContent(int id){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseBean<ArticleBean>> call = getRequestInterface.getInfoBodyContent(id);
        call.enqueue(new Callback<BaseBean<ArticleBean>>() {
            @Override
            public void onResponse(Call<BaseBean<ArticleBean>> call,
                                   Response<BaseBean<ArticleBean>> response) {
                BaseBean<ArticleBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("InfoContent", result.getData().getDatas().size() + "");
                    infoBodyContent = result.getData().getDatas();
                    updateUiInfoBodyContent();
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("InfoContent", t.getMessage());
            }
        });
    }
    public  void updateUiInfoBodyContent(){
        initViewBody();
        layoutManagerInfo.setOrientation(RecyclerView.VERTICAL);
        rvInfoBody.setLayoutManager(layoutManagerInfo);
        infoBodyAdapter = new HomeBodyAdapter(infoBodyContent);
        rvInfoBody.setAdapter(infoBodyAdapter);
    }
}

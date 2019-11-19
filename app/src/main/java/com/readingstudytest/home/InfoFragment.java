package com.readingstudytest.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.readingstudytest.HomeFragment;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.HomeBodyAdapter;
import com.readingstudytest.adapter.InfoHeaderAdapter;
import com.readingstudytest.bean.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment implements View.OnClickListener{

    //Header的变量声明
    private RecyclerView rvInfoHeader;
    private ArrayList<ProjectInfoBean> projectTreeDatas = new ArrayList<>();
    private InfoHeaderAdapter infoHeaderAdapter;
    //该变量放在updateUiAndroidChapterData()方法中会报错
    private LinearLayoutManager layoutManagerHeader;

    //Body的变量声明
    private RecyclerView rvInfoBody;
    private LinearLayoutManager layoutManagerInfo;
    private HomeBodyAdapter infoBodyAdapter;
    private static ArrayList<ArticleBean.ArticleDetailBean> infoBodyContent = new ArrayList<>();

    //创建handler机制用于在InfoHeaderAdapter中点击事件发送消息
    public static Handler handlerInfo = new Handler(){
        public void handleMessage(Message msg){
            InfoFragment infoFragment = new InfoFragment();
            if(HomeFragment.mActivity != null){
                infoFragment.downloadBodyContent(msg.arg1);
            }
//            new InfoFragment().downloadBodyContent(msg.arg1);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment_info, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //清空上次的数据，防止rvInfoHeader中出现多份相同的数据。
//        if(projectTreeDatas.size() > 0){
//            projectTreeDatas.removeAll(projectTreeDatas);
//            infoHeaderAdapter.notifyDataSetChanged();
//            rvInfoHeader.setAdapter(infoHeaderAdapter);
//        }
        initView();
        if(projectTreeDatas == null || projectTreeDatas.size() == 0) {
            downloadProjectTreeData();
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }

    private void initView(){
        rvInfoHeader = (RecyclerView) HomeFragment.mActivity.findViewById(R.id.rv_info_header);
        layoutManagerHeader = new LinearLayoutManager(HomeFragment.mActivity);
    }

    private void initViewBody(){
        rvInfoBody = (RecyclerView) HomeFragment.mActivity.findViewById(R.id.rv_info_body);
        layoutManagerInfo = new LinearLayoutManager(HomeFragment.mActivity);
    }

    private void downloadProjectTreeData(){
//        retrofit2.Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.wanandroid.com/")
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
//                .build();
//
//        GetRequestInterface service = retrofit.create(GetRequestInterface.class);
//        Call<BaseArrayBean<ProjectTreeDataBean>> call = service.getInfoProjectTreeContent();
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseArrayBean<ProjectTreeDataBean>> call = getRequestInterface.getInfoProjectTreeContent();
        call.enqueue(new Callback<BaseArrayBean<ProjectTreeDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<ProjectTreeDataBean>> call,
                                   Response<BaseArrayBean<ProjectTreeDataBean>> response) {
                ArrayList<ProjectTreeDataBean> result = response.body().getData();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("InfoProjectTree", result.size() + "");
                    for(int i = 0; i < result.size(); i ++){
                        projectTreeDatas.add(new ProjectInfoBean(result.get(i).getId(),
                                result.get(i).getName()));
                        Log.d("InfoProjectTree", projectTreeDatas.get(i).getName() + "\t" + projectTreeDatas.get(i).getId());
                    }
                    updateUiProjectTreeData();
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<ProjectTreeDataBean>> call, Throwable t) {
                Log.e("ProjectTreeErrorInfo", t.getMessage());
            }
        });
    }

    private void updateUiProjectTreeData(){
        HomeFragment.mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutManagerHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvInfoHeader.setLayoutManager(layoutManagerHeader);
                infoHeaderAdapter = new InfoHeaderAdapter(projectTreeDatas);
                rvInfoHeader.setAdapter(infoHeaderAdapter);
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
        HomeFragment.mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initViewBody();
                layoutManagerInfo.setOrientation(RecyclerView.VERTICAL);
                rvInfoBody.setLayoutManager(layoutManagerInfo);
                infoBodyAdapter = new HomeBodyAdapter(infoBodyContent);
                rvInfoBody.setAdapter(infoBodyAdapter);
            }
        });
    }
}

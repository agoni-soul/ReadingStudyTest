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

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.adapter.InfoHeaderAdapter;
import com.readingstudytest.bean.BaseArrayBean;
import com.readingstudytest.bean.HotKeyDataBean;
import com.readingstudytest.bean.ProjectTreeDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvInfoHeader;

    private ArrayList<String> projectTreeDatas = new ArrayList<>();
    private InfoHeaderAdapter infoHeaderAdapter;

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
        if(projectTreeDatas == null || projectTreeDatas.size() == 0){
            downloadProjectTreeData();
//            initProjectTreeData();

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvInfoHeader.setLayoutManager(layoutManager);
            infoHeaderAdapter = new InfoHeaderAdapter(projectTreeDatas);
            rvInfoHeader.setAdapter(infoHeaderAdapter);
        }

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

    private void initProjectTreeData(){
        projectTreeDatas.add("完整项目");
        projectTreeDatas.add("跨平台应用");
        projectTreeDatas.add("资源聚合类");
        projectTreeDatas.add("动画");
        projectTreeDatas.add("RV列表动效");
        projectTreeDatas.add("项目基础功能");
        projectTreeDatas.add("网络&amp;文件下载");
        projectTreeDatas.add("TextView");
        projectTreeDatas.add("键盘");
        projectTreeDatas.add("快应用");
        projectTreeDatas.add("日历&amp;时钟");
        projectTreeDatas.add("K线图");
        projectTreeDatas.add("硬件相关");
        projectTreeDatas.add("表格类");
    }

    private void initView(){
        rvInfoHeader = (RecyclerView) getActivity().findViewById(R.id.HomeInfo_header);
    }

    private void downloadProjectTreeData(){
        retrofit2.Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        GetRequestInterface service = retrofit.create(GetRequestInterface.class);
        Call<BaseArrayBean<ProjectTreeDataBean>> call = service.getInfoProjectTreeContent();
        call.enqueue(new Callback<BaseArrayBean<ProjectTreeDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<ProjectTreeDataBean>> call,
                                   Response<BaseArrayBean<ProjectTreeDataBean>> response) {
                BaseArrayBean<ProjectTreeDataBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("successful", result.getData().size() + "");
                    for(int i = 0; i < result.getData().size(); i ++){
                        projectTreeDatas.add(result.getData().get(i).getName());
                        Log.d("successful", result.getData().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<ProjectTreeDataBean>> call, Throwable t) {
                Log.e("ProjectTreeErrorInfo", t.getMessage());
            }
        });
    }
}

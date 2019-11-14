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

import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.InfoHeaderAdapter;
import com.readingstudytest.bean.ArticleBean;
import com.readingstudytest.bean.BaseArrayBean;
import com.readingstudytest.bean.BaseBean;
import com.readingstudytest.bean.ProjectTreeDataBean;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvInfoHeader;

    private ArrayList<String> projectTreeDatas = new ArrayList<>();
    private InfoHeaderAdapter infoHeaderAdapter;

    //网络请求数据
    private RequestDataByRetrofit retrofit;
    private GetRequestInterface getRequestInterface;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
        }
    }

    private void initView(){
        rvInfoHeader = (RecyclerView) getActivity().findViewById(R.id.HomeInfo_header);
    }

    private void downloadProjectTreeData(){
<<<<<<< HEAD
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

=======
        retrofit = RequestDataByRetrofit.getInstance();
        getRequestInterface = retrofit.getIGetRequestInterface();

        Call<BaseArrayBean<ProjectTreeDataBean>> call = getRequestInterface.getInfoProjectTreeContent();
>>>>>>> be71a583044f2add1e394f5a35581a66e7347de2
        call.enqueue(new Callback<BaseArrayBean<ProjectTreeDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<ProjectTreeDataBean>> call,
                                   Response<BaseArrayBean<ProjectTreeDataBean>> response) {
                BaseArrayBean<ProjectTreeDataBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("InfoProjectTree", result.getData().size() + "");
                    for(int i = 0; i < result.getData().size(); i ++){
                        projectTreeDatas.add(result.getData().get(i).getName());
                        Log.d("InfoProjectTree", projectTreeDatas.get(i));
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvInfoHeader.setLayoutManager(layoutManager);
                infoHeaderAdapter = new InfoHeaderAdapter(projectTreeDatas);
                rvInfoHeader.setAdapter(infoHeaderAdapter);
            }
        });
    }
}

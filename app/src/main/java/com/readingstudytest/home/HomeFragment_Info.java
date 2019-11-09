package com.readingstudytest.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.readingstudytest.R;
import com.readingstudytest.adapter.InfoHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment_Info extends Fragment implements View.OnClickListener{

    private List<String> InfoHeader = new ArrayList<>();
    private RecyclerView rvInfoHeader;
    private InfoHeaderAdapter infoHeaderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dic_layout_info, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //清空上次的数据，防止rvInfoHeader中出现多份相同的数据。
        if(InfoHeader.size() > 0){
            InfoHeader.removeAll(InfoHeader);
            infoHeaderAdapter.notifyDataSetChanged();
            rvInfoHeader.setAdapter(infoHeaderAdapter);
        }
        initInfoHeader();
        initView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvInfoHeader.setLayoutManager(layoutManager);
        infoHeaderAdapter = new InfoHeaderAdapter(InfoHeader);
        rvInfoHeader.setAdapter(infoHeaderAdapter);
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

    private void initInfoHeader(){
        InfoHeader.add("完整项目");
        InfoHeader.add("跨平台应用");
        InfoHeader.add("资源聚合类");
        InfoHeader.add("动画");
        InfoHeader.add("RV列表动效");
        InfoHeader.add("项目基础功能");
        InfoHeader.add("网络&amp;文件下载");
        InfoHeader.add("TextView");
        InfoHeader.add("键盘");
        InfoHeader.add("快应用");
        InfoHeader.add("日历&amp;时钟");
        InfoHeader.add("K线图");
        InfoHeader.add("硬件相关");
        InfoHeader.add("表格类");
    }

    private void initView(){
        rvInfoHeader = (RecyclerView) getActivity().findViewById(R.id.HomeInfo_header);
    }
}

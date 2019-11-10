package com.readingstudytest.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import okhttp3.Headers;

public class HotFragment extends Fragment implements View.OnClickListener{
    private List<String> HotHeader = new ArrayList<>();
    private LinearLayout llHotHeader;
    private TextView interview;
    private TextView studio3;
    private TextView animation;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dic_layout_hot, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initHotHeader();
        initView();
        initListener();

        addHeaderContent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.interview:
                Toast.makeText(getActivity(), "You clicked view interview", Toast.LENGTH_SHORT).show();
                break;
            case R.id.studio3:
                Toast.makeText(getActivity(), "You clicked view studio3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.animation:
                Toast.makeText(getActivity(), "You clicked view animation", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initHotHeader(){
        HotHeader.add("面试");
        HotHeader.add("STUDIO3");
        HotHeader.add("动画");
    }

    private void initView(){
        llHotHeader = (LinearLayout) getActivity().findViewById(R.id.HomeHot_header);
        interview = (TextView) getActivity().findViewById(R.id.interview);
        studio3 = (TextView) getActivity().findViewById(R.id.studio3);
        animation = (TextView) getActivity().findViewById(R.id.animation);
    }

    private void addHeaderContent(){
        interview.setText(HotHeader.get(0));
        studio3.setText(HotHeader.get(1));
        animation.setText(HotHeader.get(2));
    }

    private void initListener(){
        interview.setOnClickListener(this);
        studio3.setOnClickListener(this);
        animation.setOnClickListener(this);
    }
}

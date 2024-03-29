package com.readingstudytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.readingstudytest.adapter.PersonListAdapter;
import com.readingstudytest.bean.ListContentPersonBean;
import com.readingstudytest.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends Fragment implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener{
    private View localView;

    private AppBarLayout mineAppbarLayout;
    private TextView mineToolbarTitle;
    private LinearLayout llPersonHeader;

    private RecyclerView rvPersonList;
    private CircleImageView personImage;

    private List<ListContentPersonBean> mPersonList = new ArrayList<>();
    private PersonListAdapter personListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.person_fragment, container, false);

        if(mPersonList.size() > 0){
            mPersonList.removeAll(mPersonList);
            personListAdapter.notifyDataSetChanged();
            rvPersonList.setAdapter(personListAdapter);
        }

        initPersonList();
        initView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(localView.getContext());
        rvPersonList.setLayoutManager(layoutManager);
        personListAdapter = new PersonListAdapter(mPersonList);
        rvPersonList.setAdapter(personListAdapter);
        return localView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initPersonList(){
        mPersonList.add(new ListContentPersonBean(R.mipmap.icon_person_star, "我收藏的"));
        mPersonList.add(new ListContentPersonBean(R.mipmap.icon_person_list_theme, "主题风格"));
        mPersonList.add(new ListContentPersonBean(R.mipmap.icon_person_db, "数据恢复"));
        mPersonList.add(new ListContentPersonBean(R.mipmap.icon_person_set_list, "设置"));
        mPersonList.add(new ListContentPersonBean(R.mipmap.icon_drawer_exit, "注销"));
    }

    public void initView(){
        mineAppbarLayout = (AppBarLayout) localView.findViewById(R.id.app_bar);
        mineToolbarTitle = (TextView) localView.findViewById(R.id.tv_person_title);
        llPersonHeader = (LinearLayout) localView.findViewById(R.id.ll_person_header);
        rvPersonList = (RecyclerView) localView.findViewById(R.id.mine_person_list);
        personImage = (CircleImageView) localView.findViewById(R.id.iv_person_avatar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        personImage.setOnClickListener(this);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onStart(){
        super.onStart();
        mineAppbarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        mineAppbarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mineAppbarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_person_avatar:
                Intent intent = new Intent(localView.getContext(), LoginActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if(i == 0){
            mineToolbarTitle.setVisibility(View.GONE);
            llPersonHeader.setVisibility(View.VISIBLE);
        }else if(Math.abs(i) >= appBarLayout.getTotalScrollRange()){
            mineToolbarTitle.setVisibility(View.VISIBLE);
            llPersonHeader.setVisibility(View.GONE);
        }else if(Math.abs(i) > appBarLayout.getTotalScrollRange()/2){
            mineToolbarTitle.setVisibility(View.VISIBLE);
            llPersonHeader.setVisibility(View.VISIBLE);
        }
    }
}

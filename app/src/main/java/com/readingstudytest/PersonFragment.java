package com.readingstudytest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.readingstudytest.login.LoginActivity;

import butterknife.BindView;

public class PersonFragment extends Fragment implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener{

    @BindView(R.id.mine_appbar_layout)
    AppBarLayout mineAppbarLayout;
    @BindView(R.id.mine_toolbar_title)
    TextView mineToolbarTitle;
    @BindView(R.id.mine_toolbar_exchange)
    ImageView mineToolbarExchange;
    @BindView(R.id.mine_toolbar_feedback)
    ImageView mineToolbarFeedback;
    @BindView(R.id.mine_toolbar_setting)
    ImageView mineToolbarSetting;
    @BindView(R.id.user_image)
    ImageView userImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mineAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if(i >= appBarLayout.getTotalScrollRange() * 2 / 3){
                    mineToolbarTitle.setVisibility(View.INVISIBLE);
                }else{
                    mineToolbarTitle.setVisibility(View.VISIBLE);
                }
            }
        });
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        mineAppbarLayout.addOnOffsetChangedListener(this);
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//        mineAppbarLayout.addOnOffsetChangedListener(this);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mineAppbarLayout.removeOnOffsetChangedListener(this);
//    }

    @Override
    public void onClick(View view){

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if(i >= appBarLayout.getTotalScrollRange() * 2 / 3){
            mineToolbarTitle.setVisibility(View.INVISIBLE);
        }else{
            mineToolbarTitle.setVisibility(View.VISIBLE);
        }
    }
}

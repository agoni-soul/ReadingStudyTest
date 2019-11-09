package com.readingstudytest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.readingstudytest.adapter.MyPagerAdapter;
import com.readingstudytest.home.HomeFragment_Android;
import com.readingstudytest.home.HomeFragment_Hot;
import com.readingstudytest.home.HomeFragment_Info;
import com.readingstudytest.login.LoginActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView android;
    private ImageView hot;
    private ImageView info;
    private ViewPager dic_viewPager;

    //侧滑栏
    private DrawerLayout drawerLayout;

    private MenuItem menuItem;
    private ArrayList<View> dic_ArrayList;
    private MyPagerAdapter mAdapter;

    //fragment
    private HomeFragment_Android homeFragment_android;
    private HomeFragment_Hot homeFragment_hot;
    private HomeFragment_Info homeFragment_info;
    private Fragment[] fragments;
    private int lastfragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dic_layout, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        setupViewPager(dic_viewPager);
        initFragment();
        initListener();

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_category);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.dic_layout, null);
        android.app.ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
                default:
                    break;
        }
        return true;
    }

    private void initView(){
        android = (ImageView) getActivity().findViewById(R.id.android);
        hot = (ImageView) getActivity().findViewById(R.id.hot);
        info = (ImageView) getActivity().findViewById(R.id.info);

        dic_viewPager = (ViewPager) getActivity().findViewById(R.id.dic_viewpager);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    private void initListener(){
        android.setOnClickListener(this);
        hot.setOnClickListener(this);
        info.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.android:
                dic_viewPager.setCurrentItem(0);
                if(lastfragment != 0) {
                    switchFragment(lastfragment,0);
                    lastfragment = 0;
                }
                Toast.makeText(getActivity(), "android", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hot:
                dic_viewPager.setCurrentItem(1);
                if(lastfragment != 1) {
                    switchFragment(lastfragment,1);
                    lastfragment = 1;
                }
                Toast.makeText(getActivity(), "hot", Toast.LENGTH_SHORT).show();
                break;
            case R.id.info:
                dic_viewPager.setCurrentItem(2);
                if(lastfragment != 2) {
                    switchFragment(lastfragment,2);
                    lastfragment = 2;
                }
                Toast.makeText(getActivity(), "info", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //将子布局导入到viewPager中
    private void setupViewPager(ViewPager viewPager) {
        dic_ArrayList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        dic_ArrayList.add(li.inflate(R.layout.dic_layout_android,null,false));
        dic_ArrayList.add(li.inflate(R.layout.dic_layout_hot,null,false));
        dic_ArrayList.add(li.inflate(R.layout.dic_layout_info,null,false));
        mAdapter = new MyPagerAdapter(dic_ArrayList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
    }

    //加载home中的子fragment替换
    private void initFragment()
    {
        homeFragment_android = new HomeFragment_Android();
        homeFragment_hot = new HomeFragment_Hot();
        homeFragment_info = new HomeFragment_Info();
        fragments = new Fragment[]{homeFragment_android, homeFragment_hot, homeFragment_info};
        lastfragment = 1;
        FragmentManager childFragmentManager = getChildFragmentManager();

        childFragmentManager.beginTransaction()
                .replace(R.id.dic_viewpager, homeFragment_android)
                .show(homeFragment_android)
                .commitAllowingStateLoss();
    }

    //子fragment替换
    private void switchFragment(int lastfragment, int index)
    {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.remove(fragments[lastfragment]);
        if(fragments[index].isAdded() == false) {
            transaction.replace(R.id.dic_viewpager,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}

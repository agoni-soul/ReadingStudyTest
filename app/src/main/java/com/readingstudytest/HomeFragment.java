package com.readingstudytest;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.readingstudytest.adapter.MyPagerAdapter;
import com.readingstudytest.home.AndroidFragment;
import com.readingstudytest.home.HotFragment;
import com.readingstudytest.home.InfoFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView android;
    private ImageView hot;
    private ImageView info;
    private ViewPager dicViewPager;
    private DrawerLayout drawerLayout;

    private MenuItem menuItem;
    private ArrayList<View> dicArrayList;
    private MyPagerAdapter mAdapter;

    //fragment
    private AndroidFragment androidFragment;
    private HotFragment hotFragment;
    private InfoFragment infoFragment;
    private Fragment[] fragments;
    private int lastFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        setupViewPager(dicViewPager);
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
        View v = inflator.inflate(R.layout.home_fragment, null);
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

        dicViewPager = (ViewPager) getActivity().findViewById(R.id.dic_viewpager);
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
                dicViewPager.setCurrentItem(0);
                if(lastFragment != 0) {
                    switchFragment(lastFragment,0);
                    lastFragment = 0;
                }
                Toast.makeText(getActivity(), "android", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hot:
                dicViewPager.setCurrentItem(1);
                if(lastFragment != 1) {
                    switchFragment(lastFragment,1);
                    lastFragment = 1;
                }
                Toast.makeText(getActivity(), "hot", Toast.LENGTH_SHORT).show();
                break;
            case R.id.info:
                dicViewPager.setCurrentItem(2);
                if(lastFragment != 2) {
                    switchFragment(lastFragment,2);
                    lastFragment = 2;
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
        dicArrayList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        dicArrayList.add(li.inflate(R.layout.home_fragment_android,null,false));
        dicArrayList.add(li.inflate(R.layout.home_fragment_hot,null,false));
        dicArrayList.add(li.inflate(R.layout.home_fragment_info,null,false));
        mAdapter = new MyPagerAdapter(dicArrayList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
    }

    //加载home中的子fragment替换
    private void initFragment()
    {
        androidFragment = new AndroidFragment();
        hotFragment = new HotFragment();
        infoFragment = new InfoFragment();
        fragments = new Fragment[]{androidFragment, hotFragment, infoFragment};
        lastFragment = 0;
        FragmentManager childFragmentManager = getChildFragmentManager();

        childFragmentManager.beginTransaction()
                .replace(R.id.dic_viewpager, androidFragment)
                .show(androidFragment)
                .commitAllowingStateLoss();
    }

    //子fragment替换
    private void switchFragment(int lastFragment, int index)
    {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.remove(fragments[lastFragment]);
        if(fragments[index].isAdded() == false) {
            transaction.replace(R.id.dic_viewpager,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
        mAdapter.notifyDataSetChanged();//要通知adater更新一下
    }
}

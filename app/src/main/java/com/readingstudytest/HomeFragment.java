package com.readingstudytest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.readingstudytest.adapter.MainFragmentPagerAdapter;
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
    private int lastIndexFragment;

    private View localView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        localView = inflater.inflate(R.layout.home_fragment, container, false);
        setHasOptionsMenu(true);

        initView();
        setFragmentPager();
        initListener();
        setActionBar();
        return localView;
    }

    private void setFragmentPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AndroidFragment());
        fragments.add(new HotFragment());
        fragments.add(new InfoFragment());
        dicViewPager.setAdapter(new MainFragmentPagerAdapter(getChildFragmentManager(), fragments));
        lastIndexFragment = 1;
        dicViewPager.setCurrentItem(1);
    }

    private void setActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_category);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
        return true;
    }

    private void initView() {
        android = (ImageView) localView.findViewById(R.id.android);
        hot = (ImageView) localView.findViewById(R.id.hot);
        info = (ImageView) localView.findViewById(R.id.info);

        dicViewPager = (ViewPager) localView.findViewById(R.id.dic_viewpager);
        drawerLayout = (DrawerLayout) localView.findViewById(R.id.drawer_layout);
    }
    private void initListener() {
        android.setOnClickListener(this);
        hot.setOnClickListener(this);
        info.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.android:
                replaceFragment(0);
                break;
            case R.id.hot:
                replaceFragment(1);
                break;
            case R.id.info:
                replaceFragment(2);
                break;
        }
    }

    private void replaceFragment(int curIndexFragment) {
        if(lastIndexFragment != curIndexFragment){
            lastIndexFragment = curIndexFragment;
            dicViewPager.setCurrentItem(curIndexFragment);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        menuItem.setChecked(true);
        Log.d("HomeFragment", position + "");
        replaceFragment(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}

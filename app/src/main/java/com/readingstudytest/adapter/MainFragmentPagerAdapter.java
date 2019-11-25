package com.readingstudytest.adapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentArrayList;

    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList){
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    //设置每页的内容
    @Override
    public Fragment getItem(int indexFragment){
        return fragmentArrayList.get(indexFragment);
    }

    //获得Fragment的个数
    @Override
    public int getCount(){
        return fragmentArrayList.size();
    }
}

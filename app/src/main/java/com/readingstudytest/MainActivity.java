package com.readingstudytest;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.readingstudytest.adapter.MyPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dic:
                    viewPager.setCurrentItem(0);
                    if(lastfragment != 0) {
                        switchFragment(lastfragment,0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_gank:
                    viewPager.setCurrentItem(1);
                    if(lastfragment != 1) {
                        switchFragment(lastfragment,1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_todo:
                    viewPager.setCurrentItem(2);
                    if(lastfragment != 2) {
                        switchFragment(lastfragment,2);
                        lastfragment = 2;
                    }
                    return true;
                case R.id.navigation_tran:
                    viewPager.setCurrentItem(3);
                    if(lastfragment != 3) {
                        switchFragment(lastfragment,3);
                        lastfragment = 3;
                    }
                    return true;
            }
            return false;
        }
    };

    private ViewPager viewPager;
    private MenuItem menuItem;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;

    private  HomeFragment dicFragment;
    private ArticleFragment gankFragment;
    private TencentFragment todoFragment;
    private PersonFragment personFragment;
    private Fragment[] fragments;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        initFragment();

        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //注意这个方法滑动时会调用多次，下面是参数解释：
                //position当前所处页面索引,滑动调用的最后一次绝对是滑动停止所在页面
                //positionOffset:表示从位置的页面偏移的[0,1]的值。
                //positionOffsetPixels:以像素为单位的值，表示与位置的偏移
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navView.getMenu().getItem(0).setChecked(false);
                }
                //该方法只在滑动停止时调用，position滑动停止所在页面位置当滑动到某一位置，导航栏对应位置被按下
                menuItem = navView.getMenu().getItem(position);
                menuItem.setChecked(true);
                //这里使用navigation.setSelectedItemId(position);无效，
                //setSelectedItemId(position)的官网原句：Set the selected
                // menu item ID. This behaves the same as tapping on an item
                //未找到原因
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //这个方法在滑动是调用三次，分别对应下面三种状态
                // 这个方法对于发现用户何时开始拖动，
                // 何时寻呼机自动调整到当前页面，或何时完全停止/空闲非常有用。
                // state表示新的滑动状态，有三个值：
                // SCROLL_STATE_IDLE：开始滑动（空闲状态->滑动），实际值为0
                // SCROLL_STATE_DRAGGING：正在被拖动，实际值为1
                // SCROLL_STATE_SETTLING：拖动结束,实际值为2
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.dic_layout,null,false));
        aList.add(li.inflate(R.layout.gank_layout,null,false));
        aList.add(li.inflate(R.layout.todo_layout,null,false));
        aList.add(li.inflate(R.layout.tran_layout,null,false));
        mAdapter = new MyPagerAdapter(aList);
        viewPager.setAdapter(mAdapter);
    }

    private void initFragment()
    {
        dicFragment = new HomeFragment();
        gankFragment = new ArticleFragment();
        todoFragment = new TencentFragment();
        personFragment = new PersonFragment();
        fragments = new Fragment[]{gankFragment, dicFragment, todoFragment, personFragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewpager, gankFragment)
                .show(gankFragment)
                .commitAllowingStateLoss();
    }

    private void switchFragment(int lastfragment, int index)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastfragment]);
        if(fragments[index].isAdded() == false) {
            transaction.add(R.id.viewpager,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}

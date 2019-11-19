package com.readingstudytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener{
    public static Activity mActivity;
    public final static String TAG_REQUESTURL= "requestUrl";

    private ViewPager viewPager;
    private FloatingActionButton fab;

    private MenuItem menuItem;
    private ArrayList<View> addFragmentList;
    private MyPagerAdapter mAdapter;

    private  HomeFragment dicFragment;
    private ArticleFragment gankFragment;
    private TodoFragment todoFragment;
    private PersonFragment personFragment;
    private Fragment[] fragments;
    private int lastIndexFragment;

    private BottomNavigationView navView;

    //用于跳转到ContentShowActivity
    public static void jumpContentShowActivity(String tag, String requestAddress){
        Intent intent = new Intent(mActivity, ContentShowActivity.class);
        intent.putExtra(tag, requestAddress);
        mActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        initView();
        setView();
        initFragment();
        initListener();
    }

    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab_android_home_fragment);
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
    }

    private void setView(){
        setupViewPager(viewPager);
        fab.show();
    }

    private void initListener(){
        navView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        addFragmentList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        addFragmentList.add(li.inflate(R.layout.home_fragment,null,false));
        addFragmentList.add(li.inflate(R.layout.gank_layout,null,false));
        addFragmentList.add(li.inflate(R.layout.todo_layout,null,false));
        addFragmentList.add(li.inflate(R.layout.person_fragment,null,false));
        mAdapter = new MyPagerAdapter(addFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
    }

    private void initFragment() {
        dicFragment = new HomeFragment();
        gankFragment = new ArticleFragment();
        todoFragment = new TodoFragment();
        personFragment = new PersonFragment();
        fragments = new Fragment[]{dicFragment, gankFragment, todoFragment, personFragment};
        lastIndexFragment = 0;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewpager, dicFragment)
                .show(dicFragment)
                .commitAllowingStateLoss();
    }

    private void switchFragment(int lastIndex, int curIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastIndex]);
        if(!fragments[curIndex].isAdded()) {
            transaction.add(R.id.viewpager,fragments[curIndex]);
        }
        transaction.show(fragments[curIndex]).commitAllowingStateLoss();
        mAdapter.notifyDataSetChanged();//要通知adater更新一下
    }

    //BottomNavigationView.OnNavigationItemSelectedListener重写
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_dic:
                return replaceFragmentByNavigation(0);
            case R.id.navigation_gank:
                return replaceFragmentByNavigation(1);
            case R.id.navigation_todo:
                return replaceFragmentByNavigation(2);
            case R.id.navigation_tran:
                return replaceFragmentByNavigation(3);
        }
        return false;
    }

    private boolean replaceFragmentByNavigation(int curIndexFragment){
        viewPager.setCurrentItem(curIndexFragment);
        if(lastIndexFragment != curIndexFragment) {
            switchFragment(lastIndexFragment,curIndexFragment);
            lastIndexFragment = curIndexFragment;
        }
        return true;
    }

    //以下三个方法是ViewPager.OnPageChangeListener的重写
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
}

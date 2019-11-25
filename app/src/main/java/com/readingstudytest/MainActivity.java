package com.readingstudytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.MainFragmentPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener{
    public static Activity mActivity;
    public final static String TAG_REQUESTURL= "requestUrl";
    public final static String GankFragmentURL = "https://gank.io/api/";
    public final static String BaseURL = "https://www.wanandroid.com/";

    private ViewPager viewPager;
    private FloatingActionButton fab;

    private MenuItem menuItem;
    private int lastIndexFragment;

    private BottomNavigationView navView;

    //Retrofit和RxJava的变量
    public static RequestDataByRetrofit retrofit;
    public static GetRequestInterface getRequestInterface;

    private static Handler handlerMainActivity = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.obj.toString()){
                case "gankFragment":
                    break;
            }
        }
    };

    //用于跳转到ContentShowActivity
    public static void jumpContentShowActivity(String tag, String requestAddress){
        Intent intent = new Intent(mActivity, ContentShowActivity.class);
        intent.putExtra(tag, requestAddress);
        mActivity.startActivity(intent);
    }

    //后续将所有使用Retrofit和RxJava合并，减少变量的使用。
    private static void initRequestDataByRetrofitAndIGetRequestInterface(String url){
        //步骤4：创建Retrofit对象
        retrofit = RequestDataByRetrofit.getInstanceCustom(url,
                RequestDataByRetrofit.getOkHttpClientInstance(),
                RxJava2CallAdapterFactory.create());

        // 步骤5：创建 网络请求接口 的实例
        getRequestInterface = retrofit.getIGetRequestInterface();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        initView();
        setView();
        initListener();
    }

    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab_android_home_fragment);
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
    }

    private void setView(){
        setFragmentPager();
        fab.show();
    }

    private void setFragmentPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new GankFragment());
        fragments.add(new TodoFragment());
        fragments.add(new PersonFragment());
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        lastIndexFragment = 0;
        viewPager.setCurrentItem(0);
    }

    private void initListener(){
        navView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    //BottomNavigationView.OnNavigationItemSelectedListener重写
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_dic:
                fab.show();
                return replaceFragmentByNavigation(0);
            case R.id.navigation_gank:
                fab.hide();
                return replaceFragmentByNavigation(1);
            case R.id.navigation_todo:
                fab.hide();
                return replaceFragmentByNavigation(2);
            case R.id.navigation_tran:
                fab.hide();
                return replaceFragmentByNavigation(3);
        }
        return false;
    }

    private boolean replaceFragmentByNavigation(int curIndexFragment){
//        viewPager.setCurrentItem(curIndexFragment);
//        if(lastIndexFragment != curIndexFragment) {
//            switchFragment(lastIndexFragment,curIndexFragment);
//            lastIndexFragment = curIndexFragment;
//        }
        if(lastIndexFragment != curIndexFragment){
            lastIndexFragment = curIndexFragment;
            viewPager.setCurrentItem(curIndexFragment);
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

        //用于左右滑动时进行相应
        if(position != 0){
            fab.hide();
        } else{
            fab.show();
        }
        replaceFragmentByNavigation(position);
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

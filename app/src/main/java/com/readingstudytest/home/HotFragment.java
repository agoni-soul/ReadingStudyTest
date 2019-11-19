package com.readingstudytest.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.readingstudytest.ContentShowActivity;
import com.readingstudytest.HomeFragment;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.HomeBodyAdapter;
import com.readingstudytest.bean.*;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotFragment extends Fragment implements View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    //得到该fragment的View实例对象
    private View localView;

    //home中的悬浮按钮
    private FloatingActionButton floatingActionButton;

    //hotKey布局
    private TextView interview;
    private TextView studio3;
    private TextView animation;
    private List<HotKeyDataBean> hotKeyDatas = new ArrayList<>();

    //banner布局
    private RelativeLayout rlInterviewSlider;
    private SliderLayout sliderInterview;
    private PagerIndicator customInterviewPagerIndicator;
    private ArrayList<BannerDataBean> bannerDatas = new ArrayList<>();
    private int positionSliderView = 0;

    //hotBody布局
    private RecyclerView rvHotBody;
    private LinearLayoutManager layoutManagerHot;
    private HomeBodyAdapter hotBodyAdapter;
    private ArrayList<ArticleBean.ArticleDetailBean> hotBodyContent = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.home_fragment_hot, container, false);
        return localView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initView();
        //避免重复加载
        if(hotKeyDatas == null || hotKeyDatas.size() == 0){
            downloadHotKeyData();
        }
        if(bannerDatas == null || bannerDatas.size() == 0){
            downloadHotBannerData();
            initHotBannerContent();
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.interview:
                floatingActionButton.show();
                rlInterviewSlider.setVisibility(View.VISIBLE);
                downloadHotBodyContent(hotKeyDatas.get(0).getName());
                Log.d("You clicked HotKey", hotKeyDatas.get(0).getName());
                break;
            case R.id.studio3:
                floatingActionButton.hide();
                Toast.makeText(getActivity(), "You clicked view studio3", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.GONE);
                downloadHotBodyContent(hotKeyDatas.get(1).getName());
                Log.d("You clicked HotKey", hotKeyDatas.get(1).getName());
                break;
            case R.id.animation:
                floatingActionButton.hide();
                Toast.makeText(getActivity(), "You clicked view animation", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.GONE);
                downloadHotBodyContent(hotKeyDatas.get(2).getName());
                Log.d("You clicked HotKey", hotKeyDatas.get(2).getName());
                break;
        }
    }

    private void initView(){
        //悬浮按钮初始化
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_android_home_fragment);

        //面试 PagerView初始化
        interview = (TextView) getActivity().findViewById(R.id.interview);
        sliderInterview = (SliderLayout) getActivity().findViewById(R.id.interview_slide);
        rlInterviewSlider = (RelativeLayout) getActivity().findViewById(R.id.interview_slide_rl);
        customInterviewPagerIndicator = (PagerIndicator) getActivity().findViewById(R.id.interview_custom_indicator);

        //studio3 PagerView初始化
        studio3 = (TextView) getActivity().findViewById(R.id.studio3);

        //动画 PagerView初始化
        animation = (TextView) getActivity().findViewById(R.id.animation);

        //HotBody内容
        rvHotBody = (RecyclerView) getActivity().findViewById(R.id.rv_hot_body);
        layoutManagerHot = new LinearLayoutManager(getActivity());
    }

    private void initListener(){
        interview.setOnClickListener(this);
        studio3.setOnClickListener(this);
        animation.setOnClickListener(this);
    }

    private void downloadHotBodyContent(String name){
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("k",name);
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseBean<ArticleBean>> call = getRequestInterface.getHotBodyContent(name);
        call.enqueue(new Callback<BaseBean<ArticleBean>>() {
            @Override
            public void onResponse(Call<BaseBean<ArticleBean>> call, Response<BaseBean<ArticleBean>> response) {
                BaseBean<ArticleBean> result = response.body();
                if(result != null){
                    Log.d("HotContent", result.getData().getDatas().size() + "");
                    hotBodyContent = result.getData().getDatas();
                    updateUiHotBodyContent();
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("HotContent", t.getMessage());
            }
        });
    }

    private void updateUiHotBodyContent(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutManagerHot.setOrientation(RecyclerView.VERTICAL);
                rvHotBody.setLayoutManager(layoutManagerHot);
                hotBodyAdapter = new HomeBodyAdapter(hotBodyContent);
                rvHotBody.setAdapter(hotBodyAdapter);
            }
        });
    }

    private void downloadHotKeyData(){
//        retrofit2.Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.wanandroid.com/")
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
//                .build();
//
//        GetRequestInterface service = retrofit.create(GetRequestInterface.class);
//        Call<BaseArrayBean<HotKeyDataBean>> call = service.getHotKeyContent();

        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();
        Call<BaseArrayBean<HotKeyDataBean>> call = getRequestInterface.getHotKeyContent();
        call.enqueue(new Callback<BaseArrayBean<HotKeyDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<HotKeyDataBean>> call,
                                   Response<BaseArrayBean<HotKeyDataBean>> response) {
                BaseArrayBean<HotKeyDataBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("HotKey", result.getData().size() + "");
                    hotKeyDatas = result.getData();

                    //子线程更新UI
                    updateUiHotKeyData();
                    for(int i = 0; i < hotKeyDatas.size(); i ++){
                        Log.d("HotKey", hotKeyDatas.get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<HotKeyDataBean>> call, Throwable t) {
                Log.e("HotKeyErrorInfo", t.getMessage());
            }
        });
    }

    //使用runonUIThread更新UI
    private void updateUiHotKeyData(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initHotKeyContent();
            }
        });
    }

    private void initHotKeyContent(){
        interview.setText(hotKeyDatas.get(0).getName());
        studio3.setText(hotKeyDatas.get(1).getName());
        animation.setText(hotKeyDatas.get(2).getName());
    }

    //网络中下载数据添加到ArrayList中
    private void downloadHotBannerData(){
        RequestDataByRetrofit retrofit = RequestDataByRetrofit.getInstance();
        GetRequestInterface getRequestInterface = retrofit.getIGetRequestInterface();

        Call<BaseArrayBean<BannerDataBean>> call = getRequestInterface.getHotBannerContent();
        call.enqueue(new Callback<BaseArrayBean<BannerDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<BannerDataBean>> call,
                                   Response<BaseArrayBean<BannerDataBean>> response) {
                BaseArrayBean<BannerDataBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("HotBanner", result.getData().size() + "");
                    bannerDatas = result.getData();
                    updateUiBannerData();
                    for(int i = 0; i < bannerDatas.size(); i ++){
                        Log.d("HotBanner", bannerDatas.get(i).getTitle());
                        Log.d("HotBanner", bannerDatas.get(i).getImagePath());
                        Log.d("HotBanner", bannerDatas.get(i).getUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<BannerDataBean>> call, Throwable t) {
                Log.e("HotBannerErrorInfo", t.getMessage());
            }
        });
    }

    private void updateUiBannerData(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initHotBannerContent();
            }
        });
    }

    private void initHotBannerContent(){
        for(int i = 0; i < bannerDatas.size(); i ++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(bannerDatas.get(i).getTitle())
                    .image(bannerDatas.get(i).getImagePath())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", bannerDatas.get(i).getDesc());
            sliderInterview.addSlider(textSliderView);
        }
        sliderInterview.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
        sliderInterview.setCustomIndicator(customInterviewPagerIndicator);//自定义指示器
        sliderInterview.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        sliderInterview.setDuration(4000);//设置滚动时间，也是计时器时间
        sliderInterview.addOnPageChangeListener(this);
    }

    //BaseSliderView.OnSliderClickListener重写方法
    @Override
    public void onSliderClick(BaseSliderView slider) {
        HomeFragment.jumpContentShowActivity(HomeFragment.TAG_REQUESTURL,
                bannerDatas.get(positionSliderView).getUrl());
    }

    //ViewPagerEx.OnPageChangeListener中三个重写方法
    /*
     * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。
     * 其中三个参数的含义分别为：
     * position :当前页面，及你点击滑动的页面
     * positionOffset:当前页面偏移的百分比
     * positionOffsetPixels:当前页面偏移的像素位置
     * */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        positionSliderView = position;
    }

    /*
     * 状态改变时调用，
     * state有三种状态（0，1，2）。
     * state ==1的时辰默示正在滑动，
     * state==2的时辰默示滑动完毕了，
     * state==0的时辰默示什么都没做。
     * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）
     * */
    @Override
    public void onPageScrollStateChanged(int state) {}
}

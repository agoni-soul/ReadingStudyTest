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
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readingstudytest.ContentShowActivity;
import com.readingstudytest.HomeFragment;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.MainActivity;
import com.readingstudytest.R;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.adapter.HomeBodyAdapter;
import com.readingstudytest.bean.*;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotFragment extends Fragment implements View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    //得到该fragment的View实例对象
    private static View localView;

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

    //Retrofit和RxJava的变量
    private static RequestDataByRetrofit retrofit;
    private static GetRequestInterface getRequestInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        localView = inflater.inflate(R.layout.home_fragment_hot, container, false);
        initView();
        initListener();
        initRequestDataByRetrofitAndIGetRequestInterface();
        //避免重复加载
        downloadHotKeyDataByRxJava();
//        if(hotKeyDatas == null || hotKeyDatas.size() == 0){
//        }
//        if(bannerDatas == null || bannerDatas.size() == 0){
//        }
        return localView;
    }

    private static void initRequestDataByRetrofitAndIGetRequestInterface(){
        //步骤4：创建Retrofit对象
        retrofit = RequestDataByRetrofit.getInstanceCustom(MainActivity.BaseURL, RxJava2CallAdapterFactory.create());

        // 步骤5：创建 网络请求接口 的实例
        getRequestInterface = retrofit.getIGetRequestInterface();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.interview:
                rlInterviewSlider.setVisibility(View.VISIBLE);
                downloadHotBodyContentByRxJava(hotKeyDatas.get(0).getName());
                Log.d("You clicked HotKey", hotKeyDatas.get(0).getName());
                break;
            case R.id.studio3:
                Toast.makeText(localView.getContext(), "You clicked view studio3", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.GONE);
                downloadHotBodyContentByRxJava(hotKeyDatas.get(1).getName());
                Log.d("You clicked HotKey", hotKeyDatas.get(1).getName());
                break;
            case R.id.animation:
                Toast.makeText(localView.getContext(), "You clicked view animation", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.GONE);
                downloadHotBodyContentByRxJava(hotKeyDatas.get(2).getName());
                Log.d("You clicked HotKey", hotKeyDatas.get(2).getName());
                break;
        }
    }

    private void initView(){
        //面试 PagerView初始化
        interview = (TextView) localView.findViewById(R.id.interview);
        sliderInterview = (SliderLayout) localView.findViewById(R.id.interview_slide);
        rlInterviewSlider = (RelativeLayout) localView.findViewById(R.id.interview_slide_rl);
        customInterviewPagerIndicator = (PagerIndicator) localView.findViewById(R.id.interview_custom_indicator);

        //studio3 PagerView初始化
        studio3 = (TextView) localView.findViewById(R.id.studio3);

        //动画 PagerView初始化
        animation = (TextView) localView.findViewById(R.id.animation);

        //HotBody内容
        rvHotBody = (RecyclerView) localView.findViewById(R.id.rv_hot_body);
        layoutManagerHot = new LinearLayoutManager(localView.getContext());
    }

    private void initListener(){
        interview.setOnClickListener(this);
        studio3.setOnClickListener(this);
        animation.setOnClickListener(this);
    }

    private void downloadHotBodyContentByRxJava(String name){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<BaseBean<ArticleBean>> observable = getRequestInterface.getHotBodyContentRxJava(name);

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseBean<ArticleBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("HotContent", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseBean<ArticleBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            Log.d("HotContent", result.getData().getDatas().size() + "");
                            hotBodyContent = result.getData().getDatas();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HotContent", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("HotContent", "请求成功");
                        updateUiHotBodyContent();
                    }
                });

    }
    private void downloadHotBodyContent(String name){
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
//                    updateUiHotBodyContent();
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArticleBean>> call, Throwable t) {
                Log.e("HotContent", t.getMessage());
            }
        });
    }
    private void updateUiHotBodyContent(){
        layoutManagerHot.setOrientation(RecyclerView.VERTICAL);
        rvHotBody.setLayoutManager(layoutManagerHot);
        hotBodyAdapter = new HomeBodyAdapter(hotBodyContent);
        rvHotBody.setAdapter(hotBodyAdapter);
    }

    private void downloadHotKeyDataByRxJava(){
        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<BaseArrayBean<HotKeyDataBean>> observable = getRequestInterface.getHotKeyContentByRxJava();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.newThread())     // 在常规线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<BaseArrayBean<HotKeyDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("HotKey", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseArrayBean<HotKeyDataBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            Log.d("HotKey", result.getData().size() + "");
                            hotKeyDatas = result.getData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HotKey", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("HotKey", "请求成功");
                        updateUiHotKeyData();
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
//                    updateUiHotKeyData();
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
    private void updateUiHotKeyData(){
        if(hotKeyDatas.size() > 0){
            interview.setText(hotKeyDatas.get(0).getName());
            rlInterviewSlider.setVisibility(View.VISIBLE);
            downloadHotBodyContentByRxJava(hotKeyDatas.get(0).getName());
            downloadHotBannerDataByRxJava();
            initHotBannerContent();

            if(hotKeyDatas.size() >= 3){
                studio3.setText(hotKeyDatas.get(1).getName());
                animation.setText(hotKeyDatas.get(2).getName());
            }
        }
    }

    private void downloadHotBannerDataByRxJava(){
        Observable<BaseArrayBean<BannerDataBean>> observable = getRequestInterface.getHotBannerContentRxJava();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseArrayBean<BannerDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("HotBanner", "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(BaseArrayBean<BannerDataBean> result) {
                        // 步骤8：对返回的数据进行处理
                        if (result != null) {
                            Log.d("HotBanner", result.getData().size() + "");
                            bannerDatas = result.getData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HotBanner", "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("HotBanner", "请求成功");
                        updateUiBannerData();
                    }
                });
    }
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
//                    updateUiBannerData();
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
            TextSliderView textSliderView = new TextSliderView(localView.getContext());
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
        MainActivity.jumpContentShowActivity(MainActivity.TAG_REQUESTURL,
                bannerDatas.get(positionSliderView).getUrl());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //ViewPagerEx.OnPageChangeListener中三个重写方法
        /*
         * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。
         * 其中三个参数的含义分别为：
         * position :当前页面，及你点击滑动的页面
         * positionOffset:当前页面偏移的百分比
         * positionOffsetPixels:当前页面偏移的像素位置
         * */
    }

    @Override
    public void onPageSelected(int position) {
        positionSliderView = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        /*
         * 状态改变时调用，
         * state有三种状态（0，1，2）。
         * state ==1的时辰默示正在滑动，
         * state==2的时辰默示滑动完毕了，
         * state==0的时辰默示什么都没做。
         * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）
         * */
    }
}

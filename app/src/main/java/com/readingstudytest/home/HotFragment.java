package com.readingstudytest.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.bean.BannerDataBean;
import com.readingstudytest.bean.BaseArrayBean;
import com.readingstudytest.bean.HomeAndroidDataBean;
import com.readingstudytest.bean.HomeAndroidDatasBean;
import com.readingstudytest.bean.HomeAndroidDatasTagsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotFragment extends Fragment implements View.OnClickListener{
    private LinearLayout llHotHeader;
    private TextView interview;
    private TextView studio3;
    private TextView animation;

    private RelativeLayout rlInterviewSlider;
    private SliderLayout sliderInterview;
    private PagerIndicator customInterviewPagerIndicator;

    //banner布局
    private ArrayList<BannerDataBean> bannerDatas = new ArrayList<>();
    private BaseSliderView.OnSliderClickListener onSliderClickListener=new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView slider) {
            Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",
                    Toast.LENGTH_SHORT).show();
        }
    };
    private ViewPagerEx.OnPageChangeListener onPageChangeListener=new ViewPagerEx.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            Log.d("ansen", "Page Changed: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private List<String> HotHeader = new ArrayList<>();

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment_hot, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initHotHeader();
        initView();
        initListener();
        initView();

        addHeaderContent();
    }

    private void initBannerContent(){
        downloadHotBannerData();

        for(int i = 0; i < bannerDatas.size(); i ++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(bannerDatas.get(i).getTitle())
                    .image(bannerDatas.get(i).getImagepath())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(onSliderClickListener);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", bannerDatas.get(i).getDesc());
            sliderInterview.addSlider(textSliderView);
        }
        sliderInterview.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
        sliderInterview.setCustomIndicator(customInterviewPagerIndicator);//自定义指示器
        sliderInterview.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        sliderInterview.setDuration(4000);//设置滚动时间，也是计时器时间
        sliderInterview.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.interview:
                Toast.makeText(getActivity(), "You clicked view interview", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.VISIBLE);
                initBannerContent();
                break;
            case R.id.studio3:
                Toast.makeText(getActivity(), "You clicked view studio3", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.GONE);
                break;
            case R.id.animation:
                Toast.makeText(getActivity(), "You clicked view animation", Toast.LENGTH_SHORT).show();
                rlInterviewSlider.setVisibility(View.GONE);
                break;
        }
    }

    private void initHotHeader(){
        HotHeader.add("面试");
        HotHeader.add("STUDIO3");
        HotHeader.add("动画");
    }

    private void initView(){
        llHotHeader = (LinearLayout) getActivity().findViewById(R.id.HomeHot_header);

        //面试 PagerView初始化
        interview = (TextView) getActivity().findViewById(R.id.interview);
        sliderInterview = (SliderLayout) getActivity().findViewById(R.id.interview_slide);
        rlInterviewSlider = (RelativeLayout) getActivity().findViewById(R.id.interview_slide_rl);
        customInterviewPagerIndicator = (PagerIndicator) getActivity().findViewById(R.id.interview_custom_indicator);

        //studio3 PagerView初始化
        studio3 = (TextView) getActivity().findViewById(R.id.studio3);

        //动画 PagerView初始化
        animation = (TextView) getActivity().findViewById(R.id.animation);
    }

    private void addHeaderContent(){
        interview.setText(HotHeader.get(0));
        studio3.setText(HotHeader.get(1));
        animation.setText(HotHeader.get(2));
    }

    private void initListener(){
        interview.setOnClickListener(this);
        studio3.setOnClickListener(this);
        animation.setOnClickListener(this);
    }

    private void downloadHotBannerData(){
        retrofit2.Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        HashMap map = new HashMap<>();

        GetRequestInterface service = retrofit.create(GetRequestInterface.class);
        Call<BaseArrayBean<BannerDataBean>> call = service.getHotBannerContent();
        call.enqueue(new Callback<BaseArrayBean<BannerDataBean>>() {
            @Override
            public void onResponse(Call<BaseArrayBean<BannerDataBean>> call,
                                   Response<BaseArrayBean<BannerDataBean>> response) {
                BaseArrayBean<BannerDataBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    Log.d("successful", result.getData().size() + "");
                    bannerDatas = result.getData();
                    for(int i = 0; i < bannerDatas.size(); i ++){
                        Log.d("successful", bannerDatas.get(i).getTitle());
                        Log.d("successful", bannerDatas.get(i).getImagepath());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseArrayBean<BannerDataBean>> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    public void initData(){
//        urlMaps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        urlMaps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        urlMaps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        urlMaps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
    }
}

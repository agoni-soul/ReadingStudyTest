package com.readingstudytest.IInterface;

import com.readingstudytest.bean.BannerDataBean;
import com.readingstudytest.bean.BaseArrayBean;
import com.readingstudytest.bean.BaseBean;
import com.readingstudytest.bean.HomeAndroidDataBean;
import com.readingstudytest.bean.HomeAndroidDatasBean;
import com.readingstudytest.bean.HomeAndroidDatasTagsBean;
import com.readingstudytest.bean.UserInformationBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GetRequestInterface {
    @GET("article/list/{page}/json")
    Call<BaseBean<HomeAndroidDataBean<HomeAndroidDatasBean<HomeAndroidDatasTagsBean>>>> getAndroidContent(@Path("page") int page);

    @GET("banner/json")
    Call<BaseArrayBean<BannerDataBean>> getHotBannerContent();

    @FormUrlEncoded//百度一下这个标签的使用
    @POST//("user/login")//一定要有接口的路径
    Call<BaseBean<UserInformationBean>> getInfoAddUrl(@Url String url, @FieldMap HashMap<String, Object> map);//看一下@FieldMap 和@Querymap的区别

    @FormUrlEncoded//百度一下这个标签的使用
    @POST("user/login")//一定要有接口的路径
    Call<BaseBean<UserInformationBean>> getInfo(@FieldMap HashMap<String, Object> map);//看一下@FieldMap 和@Querymap的区别
}

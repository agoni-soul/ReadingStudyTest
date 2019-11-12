package com.readingstudytest.IInterface;

import com.readingstudytest.bean.BeanBase;
import com.readingstudytest.bean.UserBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IGetRequestInterface {
    @GET("show")
    Call<BeanBase> getMenuById(@Query("id") String id);

//    @POST //错误情况
//    Call<BeanBase<UserBean>> getInfo(@Url String url, @QueryMap Map<String, String> map);

    @FormUrlEncoded//百度一下这个标签的使用
    @POST//("user/login")//一定要有接口的路径
    Call<BeanBase<UserBean>> getInfo(@Url String url, @FieldMap HashMap<String, Object> map);//看一下@FieldMap 和@Querymap的区别

}

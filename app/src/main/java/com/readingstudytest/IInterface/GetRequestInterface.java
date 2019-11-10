package com.readingstudytest.IInterface;

import com.readingstudytest.bean.BeanBase;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface GetRequestInterface {
    @GET("show")
    Call<BeanBase> getMenuById(@Query("id") String id);

    @POST
    Call<BeanBase> getInfo(@Url String url, @QueryMap Map<String, String> map);
}

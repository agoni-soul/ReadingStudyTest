package com.readingstudytest.IInterface;

import com.readingstudytest.bean.BeanAndroidContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequestInterface {
    @GET("show")
    Call<BeanAndroidContent> getMenuById(@Query("id") String id);
}

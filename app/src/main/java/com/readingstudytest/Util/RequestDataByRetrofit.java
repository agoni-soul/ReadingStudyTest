package com.readingstudytest.Util;

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;

import retrofit2.converter.gson.GsonConverterFactory;

public class RequestDataByRetrofit {
    private GetRequestInterface getRequestInterface;

    public static RequestDataByRetrofit getInstance(){
        return new RequestDataByRetrofit();
    }

    private RequestDataByRetrofit(){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        getRequestInterface = retrofit.create(GetRequestInterface.class);
    }

    /**
     * 获得GetRequestInterface实例
     * @return
     * */
    public GetRequestInterface getIGetRequestInterface(){
        return getRequestInterface;
    }
}

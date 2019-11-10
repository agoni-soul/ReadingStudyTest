package com.readingstudytest.Util;

import com.readingstudytest.IInterface.GetRequestInterface;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private GetRequestInterface getRequestInterface;

    public static Retrofit getInstance(){
        return new Retrofit();
    }

    private Retrofit(){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getRequestInterface = retrofit.create(GetRequestInterface.class);
    }

    /**
     * 获得IBeanService实例
     * @return
     * */
    public GetRequestInterface getGetRequestInterface(){
        return getRequestInterface;
    }
}

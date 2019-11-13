package com.readingstudytest.Util;

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private GetRequestInterface igetRequestInterface;

    public static Retrofit getInstance(){
        return new Retrofit();
    }

    private Retrofit(){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        igetRequestInterface = retrofit.create(GetRequestInterface.class);
    }

    /**
     * 获得GetRequestInterface实例
     * @return
     * */
    public GetRequestInterface getIGetRequestInterface(){
        return igetRequestInterface;
    }
}

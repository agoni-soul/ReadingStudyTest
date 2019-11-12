package com.readingstudytest.Util;

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.IGetRequestInterface;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private IGetRequestInterface iGetRequestInterface;

    public static Retrofit getInstance(){
        return new Retrofit();
    }

    private Retrofit(){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        iGetRequestInterface = retrofit.create(IGetRequestInterface.class);
    }

    /**
     * 获得GetRequestInterface实例
     * @return
     * */
    public IGetRequestInterface getIGetRequestInterface(){
        return iGetRequestInterface;
    }
}

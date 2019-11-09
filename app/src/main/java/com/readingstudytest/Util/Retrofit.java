package com.readingstudytest.Util;

import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.bean.BeanAndroidContent;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private GetRequestInterface getRequestInterface;

    public static Retrofit getInstance(String address){
        return new Retrofit(address);
    }

    private Retrofit(String address){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(address)
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

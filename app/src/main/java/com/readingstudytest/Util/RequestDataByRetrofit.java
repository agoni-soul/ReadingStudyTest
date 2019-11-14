package com.readingstudytest.Util;

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;

import retrofit2.converter.gson.GsonConverterFactory;

public class RequestDataByRetrofit {
<<<<<<< HEAD:app/src/main/java/com/readingstudytest/Util/RequestDataByRetrofit.java
    private GetRequestInterface igetRequestInterface;
=======
    private GetRequestInterface getRequestInterface;
>>>>>>> be71a583044f2add1e394f5a35581a66e7347de2:app/src/main/java/com/readingstudytest/Util/RequestDataByRetrofit.java

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

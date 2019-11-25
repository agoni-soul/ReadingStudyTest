package com.readingstudytest.Util;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestDataByRetrofit {
    private GetRequestInterface getRequestInterface;
    private Retrofit retrofit;

    //获得默认baseUrl的实例
    public static RequestDataByRetrofit getInstance(){
        return new RequestDataByRetrofit();
    }

    //获得自定义baseUrl的实例
    public static RequestDataByRetrofit getInstanceCustom(final String baseUrl){
        return new RequestDataByRetrofit(baseUrl);
    }
    public static RequestDataByRetrofit getInstanceCustom(final String baseUrl, OkHttpClient client){
        return new RequestDataByRetrofit(baseUrl, client);
    }
    public static RequestDataByRetrofit getInstanceCustom(final String baseUrl, CallAdapter.Factory factory){
        return new RequestDataByRetrofit(baseUrl, factory);
    }
    public static RequestDataByRetrofit getInstanceCustom(final String baseUrl, OkHttpClient client, CallAdapter.Factory factory){
        return new RequestDataByRetrofit(baseUrl, client,  factory);
    }

    private RequestDataByRetrofit(){
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        getRequestInterface = retrofit.create(GetRequestInterface.class);
    }
    private RequestDataByRetrofit(final String baseUrl){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        getRequestInterface = retrofit.create(GetRequestInterface.class);
    }
    //自定义baseUrl，自定义OkHttpClient
    private RequestDataByRetrofit(final String baseUrl, OkHttpClient client){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(client)
                .build();
        getRequestInterface = retrofit.create(GetRequestInterface.class);
    }
    //自定义baseUrl，自定义CallAdapter.Factory
    private RequestDataByRetrofit(final String baseUrl, CallAdapter.Factory factory){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(factory)
                .build();
        getRequestInterface = retrofit.create(GetRequestInterface.class);
    }
    //自定义baseUrl，自定义OkHttpClient，自定义CallAdapter.Factory
    private RequestDataByRetrofit(final String baseUrl, OkHttpClient client, CallAdapter.Factory factory){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(factory)
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

    //客户端不对服务器证书做任何验证
    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.getSocketFactory();
    }

    //获得无需验证任何证书的OkHttpClient实例对象
    public static OkHttpClient getOkHttpClientInstance(){
        try{
            return new okhttp3.OkHttpClient.Builder()
                    .sslSocketFactory(getSSLSocketFactory())
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }catch (Exception e){
            Log.e("OkHttpClientError", e.getMessage());
        }
        return null;
    }
}

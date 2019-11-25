package com.readingstudytest.IInterface;

import com.readingstudytest.bean.*;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.*;

public interface GetRequestInterface {
    //home版块
    //获得home的android版块的网络请求信息
    @GET("article/list/{page}/json")
    Call<BaseBean<ArticleBean>> getAndroidContent(@Path("page") int page);
    @GET("article/list/{page}/json")
    Observable<BaseBean<ArticleBean>> getAndroidContentByRxJava(@Path("page") int page);

    //home的hot版块
    @FormUrlEncoded
    @POST("article/query/0/json")
    Call<BaseBean<ArticleBean>> getHotBodyContent(@Field("k") String name);
    //获得interview的banner的网络请求信息
    @GET("banner/json")
    Call<BaseArrayBean<BannerDataBean>> getHotBannerContent();
    //获得studio3的key的网络请求信息
    @GET("hotkey/json")
    Call<BaseArrayBean<HotKeyDataBean>> getHotKeyContent();
    @FormUrlEncoded
    @POST("article/query/0/json")
    Observable<BaseBean<ArticleBean>> getHotBodyContentRxJava(@Field("k") String name);
    //获得interview的banner的网络请求信息
    @GET("banner/json")
    Observable<BaseArrayBean<BannerDataBean>> getHotBannerContentRxJava();
    //获得studio3的key的网络请求信息
    @GET("hotkey/json")
    Observable<BaseArrayBean<HotKeyDataBean>> getHotKeyContentByRxJava();

    //获得info的header的网络请求信息
    @GET("project/tree/json")
    Call<BaseArrayBean<ProjectTreeDataBean>> getInfoProjectTreeContent();
    @GET("article/list/0/json")
    Call<BaseBean<ArticleBean>> getInfoBodyContent(@Query("cid") int id);
    @GET("project/tree/json")
    Observable<BaseArrayBean<ProjectTreeDataBean>> getInfoProjectTreeContentByRxJava();
    @GET("article/list/0/json")
    Observable<BaseBean<ArticleBean>> getInfoBodyContentByRxJava(@Query("cid") int id);


    //gank版块
    @GET("today")
    Call<GankHeaderContentBean> getGankHeaderContent();
    @GET("data/{cate}/20/0")
    Call<GankBodyContentBean> getGankBodyContent(@Path("cate") String category);
    //使用RxJava
    @GET("today")
    Observable<GankHeaderContentBean> getGankHeaderContentByRxJava();
    @GET("data/{cate}/20/0")
    Observable<GankBodyContentBean> getGankBodyContentByRxJava(@Path("cate") String category);


    //todo版块
    //获得header的content
    @GET("wxarticle/chapters/json")
    Call<BaseArrayBean<WxArticleBean>> getTodoChaptersContent();
    @GET("wxarticle/list/{id}/0/json")//对应于chapters中getId()
    Call<BaseBean<ArticleBean>> getTodoBodyContent(@Path("id") int id);
    //使用RxJava
    @GET("wxarticle/chapters/json")
    Observable<BaseArrayBean<WxArticleBean>> getTodoChaptersContentByRxJava();
    @GET("wxarticle/list/{id}/0/json")//对应于chapters中getId()
    Observable<BaseBean<ArticleBean>> getTodoBodyContentByRxJava(@Path("id") int id);


    //person版块
    // 登录和注册界面的网络请求信息
    @FormUrlEncoded//百度一下这个标签的使用
    @POST//("user/login")//一定要有接口的路径
    Call<BaseBean<UserInformationBean>> getInfoAddUrl(@Url String url, @FieldMap HashMap<String, Object> map);//看一下@FieldMap 和@Querymap的区别

    //该部分只是用于测试
    @FormUrlEncoded//百度一下这个标签的使用
    @POST("user/login")//一定要有接口的路径
    Call<BaseBean<UserInformationBean>> getInfo(@FieldMap HashMap<String, Object> map);//看一下@FieldMap 和@Querymap的区别
}

package com.readingstudytest.login;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.MainActivity;
import com.readingstudytest.Util.RequestDataByRetrofit;
import com.readingstudytest.bean.BaseBean;
import com.readingstudytest.bean.UserInformationBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterIsSuccessfulInformation{
    private final String email;
    private final String password;
    private final String url;
    private Activity activity;

    private GetRequestInterface getRequestInterface;
    private RequestDataByRetrofit retrofit;

    private HashMap userInfo;

    public LoginRegisterIsSuccessfulInformation(String email, String password, Activity activity){
        this.email = email;
        this.password = password;
        this.activity = activity;
        userInfo = new HashMap<>();
        userInfo.put("username", email);
        userInfo.put("password", password);
        if(activity.getClass().equals(LoginActivity.class)){
            url = "user/login/";
        }else if(activity.getClass().equals(RegisterActivity.class)){
            userInfo.put("repassword", password);
            url = "user/register/";
        }else{
            url = "";
        }
    }

    public void getRequestReturnInformation(){
        //当输入的email和password不为空时执行
        if(judgeEmailPasswordIsEmpty()){
            postLoginRequestInformation();
        }
    }

    private boolean judgeEmailPasswordIsEmpty(){
        if(email.isEmpty()){
            Toast.makeText(activity, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.isEmpty()){
            Toast.makeText(activity, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void postLoginRequestInformation(){
        retrofit = RequestDataByRetrofit.getInstance();
        getRequestInterface = retrofit.getIGetRequestInterface();

        //只允许为登录或注册
        if(url == null || url.equals("")){
            Toast.makeText(activity, "url信息为空或\"\"", Toast.LENGTH_SHORT).show();
            return;
        }

        retrofit2.Call<BaseBean<UserInformationBean>> call = getRequestInterface.getInfoAddUrl(url, userInfo);
        call.enqueue(new Callback<BaseBean<UserInformationBean>>() {
            @Override
            public void onResponse(retrofit2.Call<BaseBean<UserInformationBean>> call, Response<BaseBean<UserInformationBean>> response) {
                if (response.isSuccessful()) {
                    BaseBean<UserInformationBean> result = response.body();//关键
                    //判断result数据是否为空
                    if (result != null) {
                        String content = "email="+email+"\npassword="+password+"\nLoginInfo=" +
                                result.getData() + "\t" + result.getErrorCode() + "\t" + result.getErrorMsg();
                        Log.d("login", content);
                        //登录成功时返回0
                        if(result.getErrorCode() == 0){
                            loginSuccess();
                        }else{
                            loginFailure(result.getErrorMsg());
                        }
                    }else{
                        Log.d("login", "result Null");
                    }
                }else{
                    Log.d("login", "response Not Successful");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<UserInformationBean>> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    private void loginSuccess(){
        Intent intent = new Intent(activity, new MainActivity().getClass());
        intent.putExtra("email", email);
        activity.startActivity(intent);
        activity.finish();
    }

    private void loginFailure(String errorMsg){
        if(activity.getClass().equals(LoginActivity.class)){
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
        }else if(activity.getClass().equals(RegisterActivity.class)){
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
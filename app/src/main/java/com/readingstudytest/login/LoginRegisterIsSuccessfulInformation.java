package com.readingstudytest.login;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.MainActivity;
import com.readingstudytest.Util.Retrofit;
import com.readingstudytest.bean.BeanBase;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterIsSuccessfulInformation{
    private final String email;
    private final String password;
    private final String url;
    private Activity activity;

    private GetRequestInterface getRequestInterface;
    private com.readingstudytest.Util.Retrofit retrofit;

    private Map<String, String> userInfo;

    public LoginRegisterIsSuccessfulInformation(String email, String password, Activity activity){
        this.email = email;
        this.password = password;
        this.activity = activity;
        userInfo = new HashMap<>();
        userInfo.put(email, password);
        if(activity.getClass().equals(LoginActivity.class)){
            url = "user/login/";
        }else if(activity.getClass().equals(RegisterActivity.class)){
            url = "user/register/";
        }else{
            url = "";
        }
    }

    public void getRequestReturnInformation(){
        JudgeEmailPasswordIsEmpty();
        postLoginRequestInformation();
    }

    private void JudgeEmailPasswordIsEmpty(){
        if(email.isEmpty()){
            Toast.makeText(activity, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(activity, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void postLoginRequestInformation(){
        retrofit = Retrofit.getInstance();
        getRequestInterface = retrofit.getGetRequestInterface();

        retrofit2.Call<BeanBase> call = getRequestInterface.getInfo(url, userInfo);
        call.enqueue(new Callback<BeanBase>() {
            @Override
            public void onResponse(retrofit2.Call<BeanBase> call, Response<BeanBase> response) {
                if (response.isSuccessful()) {
                    BeanBase result = response.body();//关键
                    //判断result数据是否为空
                    if (result != null) {
                        String content = "email="+email+"\npassword="+password+"\nLoginInfo=" +
                                result.getData() + "\t" + result.getErrorCode() + "\t" + result.getErrorMsg();
                        Log.d("login", content);
                        if(result.getErrorCode() != 0){
                            loginFailure();
                        }else{
                            loginSuccess();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BeanBase> call, Throwable t) {
                loginFailure();
            }
        });
    }

    private void loginSuccess(){
        Intent intent = new Intent(activity, new MainActivity().getClass());
        intent.putExtra("email", email);
        activity.startActivity(intent);
        activity.finish();
    }

    private void loginFailure(){
        if(activity.getClass().equals(LoginActivity.class)){
            Toast.makeText(activity, "邮箱或密码输入错误", Toast.LENGTH_SHORT).show();
        }else if(activity.getClass().equals(RegisterActivity.class)){
            Toast.makeText(activity, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}
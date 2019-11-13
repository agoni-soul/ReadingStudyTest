package com.readingstudytest.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.readingstudytest.IInterface.GetRequestInterface;
import com.readingstudytest.R;
import com.readingstudytest.bean.BaseBean;
import com.readingstudytest.bean.UserInformationBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login;
    private EditText user;
    private EditText password;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }

    private void initView(){
        login = (Button) findViewById(R.id.login_button);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);
    }

    private void initListener(){
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        LoginRegisterIsSuccessfulInformation loginRegisterIsSuccessfulInformation;
        switch (view.getId()){
            case R.id.login_button:
                final String userContent = user.getText().toString();
                final String passwordContent = password.getText().toString();

                loginRegisterIsSuccessfulInformation =
                        new LoginRegisterIsSuccessfulInformation(userContent, passwordContent, this);

                loginRegisterIsSuccessfulInformation.getRequestReturnInformation();

//                sendRequest(userContent, passwordContent);
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
                default:
                    break;
        }
    }

    public void sendRequest(String userContent, String passwordContent){

        retrofit2.Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        HashMap map = new HashMap<>();
        map.put("username", userContent);
        map.put("password", passwordContent);

        GetRequestInterface service = retrofit.create(GetRequestInterface.class);
        Call<BaseBean<UserInformationBean>> call = service.getInfo(map);
        call.enqueue(new Callback<BaseBean<UserInformationBean>>() {
            @Override
            public void onResponse(Call<BaseBean<UserInformationBean>> call, Response<BaseBean<UserInformationBean>> response) {
                BaseBean<UserInformationBean> result = response.body();//关键
                //判断result数据是否为空
                if (result != null) {
                    //成功后台返回的是OK
                    if (result != null) {
                        String content = "LoginInfo=" +
                                result.getData() + "\t" + result.getErrorCode() + "\t" + result.getErrorMsg();
                        Log.d("login", content);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseBean<UserInformationBean>> call, Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }
}

package com.readingstudytest.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.readingstudytest.MainActivity;
import com.readingstudytest.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String login_address = "http://www.wanandroid.com/user/login";

    private EditText user;
    private EditText password;
    private Button login;
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
        switch (view.getId()){
            case R.id.login_button:
                String user_text = user.getText().toString();
                String psw_text = password.getText().toString();
                String postData = "username=" + user_text + "&" + "password=" + psw_text;
                System.out.println(postData);

//                SendPostUtil.sendRequestHttp(login_address, "GET", null, new HttpCallBackListener() {
//                    @Override
//                    public void onFinish(String response) {
//                        //在这里根据返回内容执行具体的逻辑
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        //在这里对异常清空进行处理
//                    }
//                });

                if(user_text.equals("123456") && psw_text.equals("123456")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "用户名或密码输入错误！！！", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
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
}

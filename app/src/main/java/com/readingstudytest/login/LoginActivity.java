package com.readingstudytest.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.readingstudytest.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

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
        user.setText("");
        password.setText("");
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

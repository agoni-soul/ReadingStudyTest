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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button register;
    private EditText email;
    private EditText password;
    private EditText repassword;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initListener();
    }

    private void initView(){
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password_first);
        repassword = (EditText) findViewById(R.id.password_second);
        register = (Button) findViewById(R.id.register_button);
        back = (TextView) findViewById(R.id.back_login);
    }

    private void initListener(){
        register.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        LoginRegisterIsSuccessfulInformation loginRegisterIsSuccessfulInformation;
        Intent intent;
        switch (view.getId()){
            case R.id.register_button:
                String passwordText = password.getText().toString();
                String repasswordText = repassword.getText().toString();
                if(!passwordText.equals(repasswordText)){
                    Toast.makeText(this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                    break;
                }
                String userName = email.getText().toString();

                loginRegisterIsSuccessfulInformation =
                        new LoginRegisterIsSuccessfulInformation(passwordText, repasswordText, this);
                loginRegisterIsSuccessfulInformation.getRequestReturnInformation();
                break;
            case R.id.back_login:
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
                default:
        }
    }
}

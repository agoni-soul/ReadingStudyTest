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
    private EditText psw_First;
    private EditText psw_Second;
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
        psw_First = (EditText) findViewById(R.id.password_first);
        psw_Second = (EditText) findViewById(R.id.password_second);
        register = (Button) findViewById(R.id.register_button);
        back = (TextView) findViewById(R.id.back_login);
    }

    private void initListener(){
        register.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.register_button:
                String passwordFirst = psw_First.getText().toString();
                String passwordSecond = psw_Second.getText().toString();
                if(!passwordFirst.equals(passwordSecond)){
                    Toast.makeText(this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                    break;
                }
                String userName = email.getText().toString();
                intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);
                finish();
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

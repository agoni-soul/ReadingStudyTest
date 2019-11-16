package com.readingstudytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ContentShowActivity extends AppCompatActivity {
    private WebView showContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_content_show);

        initView();
        showRequestDataContent(getAddress());
    }

    private void initView(){
        showContent = (WebView) findViewById(R.id.wv_show_content);
    }

    private String getAddress(){
        return getIntent().getStringExtra("request_address");
    }

    private void showRequestDataContent(final String address){
        showContent.getSettings().setJavaScriptEnabled(true);
        showContent.setWebViewClient(new WebViewClient());
        showContent.loadUrl(address);
    }
}

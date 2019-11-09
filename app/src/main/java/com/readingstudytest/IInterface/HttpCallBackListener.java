package com.readingstudytest.IInterface;

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}

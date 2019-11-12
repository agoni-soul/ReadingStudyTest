package com.readingstudytest.bean;

public class BeanBase<T> {
    T data;
    int errorCode;
    String errorMsg;

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public int getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }
}

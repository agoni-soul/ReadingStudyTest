package com.readingstudytest.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BeanBase {
    private ArrayList data;
    private int errorCode;
    private String errorMsg;

    public List getData(){
        return data;
    }

    public void setData(List data){
        this.data = new ArrayList(data.size());
        for(int i = 0; i < data.size(); i ++){
            this.data.add(data.get(i));
        }
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

package com.readingstudytest.bean;

public class ListContentPersonBean {
    private int itemImageSrc;
    private String itemName;

    public ListContentPersonBean(int itemImageSrc, String itemName){
        this.itemImageSrc = itemImageSrc;
        this.itemName = itemName;
    }

    public int getItemImageSrc(){
        return itemImageSrc;
    }

    public String getItemName(){
        return itemName;
    }
}

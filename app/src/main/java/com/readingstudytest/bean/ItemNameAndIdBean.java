package com.readingstudytest.bean;

public class ItemNameAndIdBean {
    private int id;
    private String name;

    public ItemNameAndIdBean(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
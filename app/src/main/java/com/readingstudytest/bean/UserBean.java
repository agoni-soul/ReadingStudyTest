package com.readingstudytest.bean;

import java.util.ArrayList;

public class UserBean {
    public int id;
    public String email;
    public String password;
    public String icon;
    public String username;
    public int type;
    public ArrayList<String> collectIds;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public ArrayList<String> getCollectIds(){
        return collectIds;
    }

    public void setCollectIds(ArrayList<String> collectIds){
        this.collectIds = new ArrayList<String>();
        for(int i = 0; i < collectIds.size(); i ++){
            this.collectIds.add(collectIds.get(i));
        }
    }
}

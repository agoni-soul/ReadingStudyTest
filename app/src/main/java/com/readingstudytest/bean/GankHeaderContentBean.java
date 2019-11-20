package com.readingstudytest.bean;

import java.util.List;

public class GankHeaderContentBean {
    private List<String> category;
    private boolean error;
//    private List<GankDetailBean> results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

//    public List<GankDetailBean> getResults() {
//        return results;
//    }
//
//    public void setResults(List<GankDetailBean> results) {
//        this.results = results;
//    }
//
//    public class GankDetailBean{
//        String _id;
//        String createdAt;
//        String desc;
//        String web;
//        String type;
//        String url;
//
//        public String get_id() {
//            return _id;
//        }
//
//        public void set_id(String _id) {
//            this._id = _id;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getDesc() {
//            return desc;
//        }
//
//        public void setDesc(String desc) {
//            this.desc = desc;
//        }
//
//        public String getWeb() {
//            return web;
//        }
//
//        public void setWeb(String web) {
//            this.web = web;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public boolean isUsed() {
//            return used;
//        }
//
//        public void setUsed(boolean used) {
//            this.used = used;
//        }
//
//        public String getWho() {
//            return who;
//        }
//
//        public void setWho(String who) {
//            this.who = who;
//        }
//
//        boolean used;
//        String who;
//
//    }
}

package com.readingstudytest.bean;

import java.util.ArrayList;
import java.util.List;

public class HomeAndroidDataBean<T> {

    private int curpage;
    private ArrayList<T> datas;
    private int offset;
    private boolean over;
    private int pagecount;
    private int size;
    private int total;
    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }
    public int getCurpage() {
        return curpage;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }
    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
    public boolean getOver() {
        return over;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }
    public int getPagecount() {
        return pagecount;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotal() {
        return total;
    }
}


//public class HomeDatasBean {
//    private int curPage;
//    private ArrayList datas;
//    private int offset;
//    private boolean over;
//    private int pageCount;
//    private int size;
//    private int total;
//
//    public int getCurPage(){
//        return curPage;
//    }
//
//    public void setCurPage(int curPage){
//        this.curPage = curPage;
//    }
//
//    public ArrayList getDatas(){
//        return datas;
//    }
//
//    public void setDatas(ArrayList datas){
//        this.datas = new ArrayList(datas.size());
//        for(int i = 0; i < datas.size(); i ++){
//            this.datas.add(datas.get(i));
//        }
//    }
//
//    public int getOffset(){
//        return offset;
//    }
//
//    public void setOffset(int curPage){
//        this.offset = offset;
//    }
//
//    public boolean getOver(){
//        return over;
//    }
//
//    public void setOver(boolean over){
//        this.over = over;
//    }
//
//    public int getPageCount(){
//        return pageCount;
//    }
//
//    public void setPageCount(int pageCount){
//        this.pageCount = pageCount;
//    }
//
//    public void setSize(int size){
//        this.size = size;
//    }
//
//    public int getSize(){
//        return size;
//    }
//
//    public void setTotal(int total){
//        this.total = total;
//    }
//
//    public int getTotal(){
//        return total;
//    }
//}
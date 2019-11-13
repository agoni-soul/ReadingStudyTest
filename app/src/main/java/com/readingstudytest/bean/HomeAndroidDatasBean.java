package com.readingstudytest.bean;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeAndroidDatasBean<T> {
    private String apkLink;
    private int audit;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private long publishTime;
    private int selfVisible;
    private long shareDate;
    private String shareUser;
    private int superChapterId;
    private String superChapterName;
    private ArrayList<T> tags;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }
    public String getApkLink() {
        return apkLink;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }
    public int getAudit() {
        return audit;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public String getChapterName() {
        return chapterName;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
    public boolean getCollect() {
        return collect;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getCourseId() {
        return courseId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }
    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }
    public boolean getFresh() {
        return fresh;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getLink() {
        return link;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }
    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }
    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getOrigin() {
        return origin;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getPrefix() {
        return prefix;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }
    public String getProjectLink() {
        return projectLink;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
    public long getPublishTime() {
        return publishTime;
    }

    public void setSelfVisible(int selfVisible) {
        this.selfVisible = selfVisible;
    }
    public int getSelfVisible() {
        return selfVisible;
    }

    public void setShareDate(long shareDate) {
        this.shareDate = shareDate;
    }
    public long getShareDate() {
        return shareDate;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }
    public String getShareUser() {
        return shareUser;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }
    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }
    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setTags(ArrayList<T> tags) {
        this.tags = tags;
    }
    public ArrayList<T> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getVisible() {
        return visible;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
    public int getZan() {
        return zan;
    }

//    private String apkLink;
//    private int audit;
//    private String author;
//    private int chapterId;
//    private String chapterName;
//    private boolean collect;
//    private int courseId;
//    private String desc;
//    private String envelopPic;
//    private boolean fresh;
//    private int id;
//    private String link;
//    private String niceDate;
//    private String niceShareDate;
//    private String origin;
//    private String prefix;
//    private String projectLink;
//    private long publicTime;
//    private int selfVisible;
//    private long shareDate;
//    private String shareUser;
//    private int superChapterId;
//    private String superChapterName;
//    private String[] tags;
//    private String title;
//    private int type;
//    private int userId;
//    private int visible;
//    private int zan;
//
//    public String getApkLink(){
//        return apkLink;
//    }
//
//    public void setApkLink(String apkLink){
//        this.apkLink = apkLink;
//    }
//
//    public int getAudit(){
//        return audit;
//    }
//
//    public void setAudit(int audit){
//        this.audit = audit;
//    }
//
//    public String getAuthor(){
//        return author;
//    }
//
//    public void setAuthor(String author){
//        this.author = author;
//    }
//
//    public int getChapterId(){
//        return chapterId;
//    }
//
//    public void setChapterId(int chapterId){
//        this.chapterId = chapterId;
//    }
//
//    public String getChapterName(){
//        return chapterName;
//    }
//
//    public void setChapterName(String chapterName){
//        this.chapterName = chapterName;
//    }
//
//    public boolean getCollect(){
//        return collect;
//    }
//
//    public void setCollect(boolean collect){
//        this.collect = collect;
//    }
//
//    public int getCourseId(){
//        return courseId;
//    }
//
//    public void setCourseId(int courseId){
//        this.courseId = courseId;
//    }
//
//    public String getDesc(){
//        return desc;
//    }
//
//    public void setDesc(String desc){
//        this.desc = desc;
//    }
//
//    //对图片如何进行保存
//    public String getEnvelopPic(){
//        return envelopPic;
//    }
//
//    public void setEnvelopPic(String envelopPic){
//        this.envelopPic = envelopPic;
//    }
//
//    public boolean getFresh(){
//        return fresh;
//    }
//
//    public void setFresh(Boolean fresh){
//        this.fresh = fresh;
//    }
//
//    public int getId(){
//        return id;
//    }
//
//    public void setId(int id){
//        this.id = id;
//    }
//
//    public String getLink(){
//        return link;
//    }
//
//    public void setLink(String link){
//        this.link = link;
//    }
//
//    public String getNiceDate(){
//        return niceDate;
//    }
//
//    public void setNiceDate(String niceDate){
//        this.niceDate = niceDate;
//    }
//
//    public String getNiceShareDate(){
//        return niceShareDate;
//    }
//
//    public void setNiceShareDate(String niceShareDate){
//        this.niceShareDate = niceShareDate;
//    }
//
//    public String getOrigin(){
//        return origin;
//    }
//
//    public void setOrigin(String origin){
//        this.origin = origin;
//    }
//
//    public String getPrefix(){
//        return prefix;
//    }
//
//    public void setPrefix(String prefix){
//        this.prefix = prefix;
//    }
//
//    public String getProjectLink(){
//        return projectLink;
//    }
//
//    public void setProjectLink(String projectLink){
//        this.projectLink = projectLink;
//    }
//
//    public long getPublicTime(){
//        return publicTime;
//    }
//
//    public void setPublicTime(long publicTime){
//        this.publicTime = publicTime;
//    }
//
//    public int getSelfVisible(){
//        return  selfVisible;
//    }
//
//    public void setSelfVisible(int selfVisible){
//        this.selfVisible = selfVisible;
//    }
//
//    public long getShareDate(){
//        return shareDate;
//    }
//
//    public void setShareDate(long shareDate){
//        this.shareDate = shareDate;
//    }
//
//    public String getShareUser(){
//        return shareUser;
//    }
//
//    public void setShareUser(String shareUser){
//        this.shareUser = shareUser;
//    }
//
//    public int getSuperChapterId(){
//        return superChapterId;
//    }
//
//    public void setSuperChapterId(int superChapterId){
//        this.superChapterId = superChapterId;
//    }
//
//    public String getSuperChapterName(){
//        return superChapterName;
//    }
//
//    public void setSuperChapterName(String superChapterName){
//        this.superChapterName = superChapterName;
//    }
//
//    public String[] getTags(){
//        return tags;
//    }
//
//    public void setTags(String[] tags){
//        if(tags == null){
//            this.tags = null;
//            return;
//        }
//        if(tags.length == 0){
//            this.tags = tags;
//            return;
//        }
//        this.tags = new String[tags.length];
//        int k = 0;
//        for(String content : tags){
//            tags[k ++] = content;
//        }
//    }
//
//    public String getTitle(){
//        return title;
//    }
//
//    public void setTitle(String title){
//        this.title = title;
//    }
//
//    public int getType(){
//        return type;
//    }
//
//    public void setType(int type){
//        this.type = type;
//    }
//
//    public int getUserId(){
//        return userId;
//    }
//
//    public void setUserId(int userId){
//        this.userId = userId;
//    }
//
//    public int getVisible(){
//        return visible;
//    }
//
//    public void setVisible(int visible){
//        this.visible = visible;
//    }
//
//    public int getZan(){
//        return zan;
//    }
//
//    public void setZan(int zan){
//        this.zan = zan;
//    }
}

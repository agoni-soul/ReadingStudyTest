package com.readingstudytest.bean;

import java.util.ArrayList;
import java.util.Date;

public class ArticleBean {

    int curPage;
    int offset;
    boolean over;
    int pageCount;
    int size;
    int total;
    ArrayList<ArticleDetailBean> datas;

    public void setCurpage(int curpage) {
        this.curPage = curpage;
    }
    public int getCurpage() {
        return curPage;
    }

    public void setDatas(ArrayList<ArticleDetailBean> datas) {
        this.datas = datas;
    }
    public ArrayList<ArticleDetailBean> getDatas() {
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

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public int getPageCount() {
        return pageCount;
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

    public class ArticleDetailBean {

        String author;
        int chapterId;
        String chapterName;
        boolean collect;
        int courseId;
        String desc;
        String envelopePic;
        boolean fresh;
        int id;
        String link;
        String niceDate;
        String origin;
        String projectLink;
        long publishTime;
        int superChapterId;
        String superChapterName;
        ArrayList<TagBean> tags;
        String title;
        int type;
        int userId;
        int visible;
        int zan;
        String name;
        int originId;

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

        public void setOrigin(String origin) {
            this.origin = origin;
        }
        public String getOrigin() {
            return origin;
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

        public void setTags(ArrayList<TagBean> tags) {
            this.tags = tags;
        }
        public ArrayList<TagBean> getTags() {
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

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }
        public int getOriginId() {
            return originId;
        }
    }

    public class TagBean {
        String name;
        String url;
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }
    }
}

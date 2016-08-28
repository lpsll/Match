package com.macth.match.mine.entity;

/**
 * Created by Administrator on 2016/8/28.
 * "id": "1",
 * "notice_title": "日本为什么侵略中国?",
 * "notice_ctime": "2016-08-17",
 * "notice_content": "为什么日本会侵略中国?为什么日本会侵略中国?为什么日本会侵略中国?为什么日本会侵略中国?为什么日本会…",
 * "notice_url": "http://cuohe.damaimob.com/Home/Notices/info?id=1"
 */
public class NewsEntity {

    private  String id;
    private  String notice_title;
    private  String notice_ctime;
    private  String notice_content;
    private  String notice_url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_ctime() {
        return notice_ctime;
    }

    public void setNotice_ctime(String notice_ctime) {
        this.notice_ctime = notice_ctime;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_url() {
        return notice_url;
    }

    public void setNotice_url(String notice_url) {
        this.notice_url = notice_url;
    }
}

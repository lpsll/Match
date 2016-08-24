package com.macth.match.notice.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/24.
 */
public class NoticeEntity extends BaseEntity{

    /**
     * "id": "1",---------------------------------公告id
     "message_title": "为什么日本会侵略中国?",--公告标题
     "message_ctime": "2016-08-09"--------------发布时间
     "message_url":"http://cuohe.damaimob.com/Home/Messages/info?id=1"
     }
     ],
     "count": "1"
     */

    private int id;
    private String message_title;
    private String message_ctime;
    private String message_content;
    private String message_url;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_ctime() {
        return message_ctime;
    }

    public void setMessage_ctime(String message_ctime) {
        this.message_ctime = message_ctime;
    }

    public String getMessage_url() {
        return message_url;
    }

    public void setMessage_url(String message_url) {
        this.message_url = message_url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    @Override
    public String toString() {
        return "NoticeEntity{" +
                "id=" + id +
                ", message_title='" + message_title + '\'' +
                ", message_ctime='" + message_ctime + '\'' +
                ", message_url='" + message_url + '\'' +
                ", count=" + count +
                '}';
    }
}

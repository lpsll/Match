package com.macth.match.mine.entity;

/**
 * Created by John_Libo on 2016/9/21.
 */
public class NewsEvent {
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public NewsEvent(String msg) {
        super();
        this.msg = msg;
    }
}

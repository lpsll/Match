package com.macth.match.group.entity;

/**
 * Created by John_Libo on 2016/9/30.
 */
public class GroupNewsEvent {
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GroupNewsEvent(String msg) {
        super();
        this.msg = msg;
    }
}

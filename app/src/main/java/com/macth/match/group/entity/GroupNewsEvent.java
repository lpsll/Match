package com.macth.match.group.entity;

/**
 * Created by John_Libo on 2016/9/30.
 */
public class GroupNewsEvent {
    private String msg;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GroupNewsEvent(String msg,int count) {
        super();
        this.msg = msg;
        this.count = count;
    }
}

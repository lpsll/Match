package com.macth.match.recommend.entity;

/**
 * Created by John_Libo on 2016/9/22.
 */
public class AddUseEvent {
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public AddUseEvent(String msg) {
        super();
        this.msg = msg;
    }

}

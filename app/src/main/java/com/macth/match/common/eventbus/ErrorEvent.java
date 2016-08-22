package com.macth.match.common.eventbus;

/**
 * Created by John_Libo on 2016/8/15.
 */
public class ErrorEvent {

    private String status;
    private String msg;
    private Object tag;

    public ErrorEvent(String status, String msg, Object tag) {
        super();
        this.status = status;
        this.msg = msg;
        this.tag = tag;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}

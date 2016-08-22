package com.macth.match.common.entity;

import java.io.Serializable;

/**
 实体类基类
 */
public class BaseEntity implements Serializable {
    private String status;
    private String msg;
    private String page_total;

    public String getPage_total() {
        return page_total;
    }

    public void setPage_total(String page_total) {
        this.page_total = page_total;
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
}

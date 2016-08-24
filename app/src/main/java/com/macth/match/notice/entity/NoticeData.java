package com.macth.match.notice.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class NoticeData extends BaseEntity {

    List<NoticeEntity> list;
    private String count;

    public List<NoticeEntity> getList() {
        return list;
    }

    public void setList(List<NoticeEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}

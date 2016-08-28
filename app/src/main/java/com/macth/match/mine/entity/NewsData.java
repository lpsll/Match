package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 */
public class NewsData extends BaseEntity {

    List<NewsEntity> list;
    private String count;

    public List<NewsEntity> getList() {
        return list;
    }

    public void setList(List<NewsEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

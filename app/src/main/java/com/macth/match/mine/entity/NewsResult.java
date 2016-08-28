package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/28.
 */
public class NewsResult extends BaseEntity {

    NewsData data;

    public NewsData getData() {
        return data;
    }

    public void setData(NewsData data) {
        this.data = data;
    }
}

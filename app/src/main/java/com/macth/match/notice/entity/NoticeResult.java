package com.macth.match.notice.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/24.
 */
public class NoticeResult extends BaseEntity {

    NoticeData data;
    public NoticeData getData() {
        return data;
    }

    public void setData(NoticeData data) {
        this.data = data;
    }

}

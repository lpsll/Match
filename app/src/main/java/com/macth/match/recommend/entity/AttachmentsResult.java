package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class AttachmentsResult extends BaseEntity {
    List<AttachmentsEntity> data;

    public List<AttachmentsEntity> getData() {
        return data;
    }

    public void setData(List<AttachmentsEntity> data) {
        this.data = data;
    }
}

package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/25.
 */
public class MineProjectsResult extends BaseEntity {

    MineProjectsData data;

    public MineProjectsData getData() {
        return data;
    }

    public void setData(MineProjectsData data) {
        this.data = data;
    }
}

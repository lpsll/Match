package com.macth.match.find.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/24.
 */
public class FindResult extends BaseEntity {

    FindData data;
    public FindData getData() {
        return data;
    }

    public void setData(FindData data) {
        this.data = data;
    }

}

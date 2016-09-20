package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/20.
 */
public class UpdateResult extends BaseEntity {
    public List<UpdateEntity> getData() {
        return data;
    }

    public void setData(List<UpdateEntity> data) {
        this.data = data;
    }

    List<UpdateEntity> data;
}

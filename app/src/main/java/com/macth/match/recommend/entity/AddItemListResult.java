package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/8/25.
 */
public class AddItemListResult extends BaseEntity {
    List<AddItemListEntity> data;
    public List<AddItemListEntity> getData() {
        return data;
    }

    public void setData(List<AddItemListEntity> data) {
        this.data = data;
    }




}

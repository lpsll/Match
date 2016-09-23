package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/22.
 */
public class AddUseResult extends BaseEntity {
    List<AddUseEntity> data;
    public List<AddUseEntity> getData() {
        return data;
    }

    public void setData(List<AddUseEntity> data) {
        this.data = data;
    }


}

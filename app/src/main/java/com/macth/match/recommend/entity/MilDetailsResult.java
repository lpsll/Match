package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/9.
 */
public class MilDetailsResult extends BaseEntity{
    List<MilDetailsEntity> data;
    public List<MilDetailsEntity> getData() {
        return data;
    }

    public void setData(List<MilDetailsEntity> data) {
        this.data = data;
    }


}

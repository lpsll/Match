package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John_Libo on 2016/8/23.
 */
public class RecommendResult extends BaseEntity{
    RecommendData data;
    public RecommendData getData() {
        return data;
    }

    public void setData(RecommendData data) {
        this.data = data;
    }



}

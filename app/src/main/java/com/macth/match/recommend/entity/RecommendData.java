package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/8/23.
 */
public class RecommendData extends BaseEntity{
    List<RecommendEntity> list;
    private String count;

    public List<RecommendEntity> getList() {
        return list;
    }

    public void setList(List<RecommendEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}

package com.macth.match.mine.fragment.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class MineProjectsData extends BaseEntity {

    List<MineProjectsEntity> list;
    private String count;

    public List<MineProjectsEntity> getList() {
        return list;
    }

    public void setList(List<MineProjectsEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

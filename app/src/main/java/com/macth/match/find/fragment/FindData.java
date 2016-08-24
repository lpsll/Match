package com.macth.match.find.fragment;

import com.macth.match.common.entity.BaseEntity;
import com.macth.match.find.fragment.FindEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class FindData extends BaseEntity {

    List<FindEntity> list;
    private String count;

    public List<FindEntity> getList() {
        return list;
    }

    public void setList(List<FindEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

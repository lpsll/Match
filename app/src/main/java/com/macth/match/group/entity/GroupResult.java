package com.macth.match.group.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/19.
 */
public class GroupResult extends BaseEntity {
    List<GroupEntity> data;
    public List<GroupEntity> getData() {
        return data;
    }

    public void setData(List<GroupEntity> data) {
        this.data = data;
    }


}

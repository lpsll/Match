package com.macth.match.group.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/25.
 */
public class MembersResult extends BaseEntity {
    List<MembersEntity> data;
    public List<MembersEntity> getData() {
        return data;
    }

    public void setData(List<MembersEntity> data) {
        this.data = data;
    }





}

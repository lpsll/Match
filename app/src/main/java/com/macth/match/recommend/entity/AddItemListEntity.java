package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/8/25.
 */
public class AddItemListEntity extends BaseEntity {
    private String id;
    private String constant_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConstant_name() {
        return constant_name;
    }

    public void setConstant_name(String constant_name) {
        this.constant_name = constant_name;
    }


}

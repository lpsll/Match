package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/9/20.
 */
public class UpdateEntity extends BaseEntity {
    private String id;
    private String cooperative_name;
    private String currentStatus;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCooperative_name() {
        return cooperative_name;
    }

    public void setCooperative_name(String cooperative_name) {
        this.cooperative_name = cooperative_name;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }


}

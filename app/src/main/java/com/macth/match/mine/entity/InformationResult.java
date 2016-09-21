package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/9/21.
 */
public class InformationResult extends BaseEntity {
    InformationEntity data;
    public InformationEntity getData() {
        return data;
    }

    public void setData(InformationEntity data) {
        this.data = data;
    }


}

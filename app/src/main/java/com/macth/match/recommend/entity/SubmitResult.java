package com.macth.match.recommend.entity;

import com.macth.match.common.dto.BaseDTO;
import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/9/22.
 */
public class SubmitResult extends BaseEntity {
    SubmitEntity data;
    public SubmitEntity getData() {
        return data;
    }

    public void setData(SubmitEntity data) {
        this.data = data;
    }


}

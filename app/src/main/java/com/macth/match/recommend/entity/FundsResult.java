package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/8/25.
 */
public class FundsResult extends BaseEntity {
    FundsData data;
    public FundsData getData() {
        return data;
    }

    public void setData(FundsData data) {
        this.data = data;
    }


}

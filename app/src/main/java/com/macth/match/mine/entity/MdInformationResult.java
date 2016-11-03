package com.macth.match.mine.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by John_Libo on 2016/9/23.
 */
public class MdInformationResult extends BaseEntity{
    MdInformationEntity data;
    public MdInformationEntity getData() {
        return data;
    }

    public void setData(MdInformationEntity data) {
        this.data = data;
    }


}

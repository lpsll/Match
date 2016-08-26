package com.macth.match.register.entity;

import com.macth.match.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 * "id": "7",
 * "name": "企业"
 */
public class ShenFenEntity extends BaseEntity {

   private List<Data> data;


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}

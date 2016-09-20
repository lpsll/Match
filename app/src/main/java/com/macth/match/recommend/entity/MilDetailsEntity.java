package com.macth.match.recommend.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/9/9.
 */
public class MilDetailsEntity extends BaseEntity {
    private String mfileid;
    private String mid;
    private String name;
    private String finsh; //是否完成1 未完成 2 完成
    private String downfilepath;

    public String getMfileid() {
        return mfileid;
    }

    public void setMfileid(String mfileid) {
        this.mfileid = mfileid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinsh() {
        return finsh;
    }

    public void setFinsh(String finsh) {
        this.finsh = finsh;
    }

    public String getDownfilepath() {
        return downfilepath;
    }

    public void setDownfilepath(String downfilepath) {
        this.downfilepath = downfilepath;
    }



}

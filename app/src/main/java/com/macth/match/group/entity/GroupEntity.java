package com.macth.match.group.entity;

import com.macth.match.common.entity.BaseEntity;

/**
 * Created by John_Libo on 2016/9/19.
 */
public class GroupEntity extends BaseEntity {
    private String groupid;
    private String groupname;
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }


}

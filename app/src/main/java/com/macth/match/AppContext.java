package com.macth.match;


import android.content.Context;
import android.content.SharedPreferences;

import com.macth.match.common.base.BaseApplication;
import com.macth.match.common.entity.BaseEntity;
import com.macth.match.group.entity.GroupEntity;

import java.util.Collections;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by John_Libo on 2016/8/15.
 */
public class AppContext extends BaseApplication {
    private static AppContext instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private List<GroupEntity> groupEntityList = Collections.emptyList();

    public List<GroupEntity> getGroupEntityList() {
        return groupEntityList;
    }

    public void setGroupEntityList(List<GroupEntity> groupEntityList) {
        this.groupEntityList = groupEntityList;
    }

    /**
     * 清单文件名称f
     */
    public static final String SP_NAME = "match_app";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.sp = this.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        this.editor = this.sp.edit();

        ShareSDK.initSDK(this,"17533b6d06b7c");
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }


}

package com.macth.match.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 根据文件对应类型打开文件
 * Created by Administrator on 2016/9/25.
 */
public class OpenFileUtils {

    private Activity act;

    public OpenFileUtils(Activity act) {
        this.act = act;
    }

    //建立一个MIME类型与文件后缀名的匹配表
    private final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".doc",    "application/msword"},
            {".jpg",    "image/jpeg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".txt",    "text/plain"},
            {".wps",    "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml",    "text/plain"},
            {".zip",    "application/zip"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"",        "*/*"}
    };

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    private String getMIMEType(File file)
    {
        String type="*/*";
        String fName=file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }
    /**
     * 打开文件
     * @param file
     */
    public void openFile(File file){
        //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转 如果手机上未装对应的软件，就会报错
        act.startActivity(intent);

    }
}

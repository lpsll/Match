package com.macth.match;

/**
 * Created by John_Libo on 2016/8/15.
 */
public class AppConfig {
    /**
     * 调试模式
     */
    public static final boolean DEBUG = true;


    public static final String ERROR_PARSER = "1001";
    public static final String ERROR_IO = "1002";
    public static final String ERROR_NONET = "1003";
    public static final String ERROR_REQ = "1004";
    public static final String ERROR_PARSER_MSG = "数据解析失败";
    public static final String ERROR_IO_MSG = "系统繁忙，请您稍后再试";
    public static final String ERROR_NONET_MSG = "请检查您的网络设置";
    public static final String ERROR_REQ_MSG = "请求失败";

    public static final String SUCCESS = "1";
    public static final String CODE = "0";
    public static final String BASE_URL = "http://cuohe.damaimob.com/";
    public static final String ADD_H5_URL = "http://cuohe.damaimob.com/CuoHeApp/html/AddItem.html?userid=";//新增项目
    public static final String DETAILS_H5_URL = "http://cuohe.damaimob.com/CuoHeApp/html/ItemDec.html?userid=";//项目详情
    public static final String MILEPOST_H5_URL = "http://cuohe.damaimob.com/CuoHeApp/html/Milestone.html?pid=";//里程碑详情
    public static final String MDPROJECT_H5_URL = "http://cuohe.damaimob.com/CuoHeApp/html/UpdateItem.html?userid=";//修改项目
    public static final String MYMILEPOST_H5_URL = "http://cuohe.damaimob.com/CuoHeApp/html/MyMilestone.html?pid=";//我的项目中里程碑




}

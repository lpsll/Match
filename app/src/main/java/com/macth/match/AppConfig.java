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
    public static final String BASE_URL = "http://cuohe.damaimob.com/";

    /**
     * 用户信息全局变量
     * "username": "崔丹锋",---------用户名
     * "usermobile": "18210013505",---用户手机号
     * "useridentity": "内部用户",-----用户身份
     * "usercompany": "醉美国际网络技术有限责任公司",
     * "userwork": "IOS工程师",
     * "usertoken": "3"-----------用户Token，
     * "cooperativeid":"1"--------协同角色id
     */
    public static String username;
    public static String usermobile;
    public static String useridentity;
    public static String usercompany;
    public static String userwork;
    public static String usertoken;
    public static String cooperativeid;
    public static boolean isLogin  = false;  //默认是false


}

package com.zhl.control;

/**
 * Created by Administrator on 2017/9/5.
 */
public class ControlMessage {


    public static final int NET_GETINGWEBPRO = 1;//正在从网站获取最新程序
    public static final int NET_GETWEBPRO_ERROR = 2;//从网站获取最新程序失败
    public static final int UPDATE_CONTROL_BLEERROR = 3;//向控制器下程序失败
    public static final int UPDATE_CONTROL_CORE_SENDING = 4;//正在向控制器下载固件程序
    public static final int UPDATE_CONTROL_CORE_FINISH = 5;//向控制器下载固件程序完成

    public static final int CON_NOCONNECT = 11;         //尚未与控制器连接
    public static final int CON_DISABLE = 12;           //控制器蓝牙未开启
    public static final int CON_WAITINGENABLE = 13;     //等待用户开启蓝牙
    public static final int CON_SCANING = 14;           //正在扫描控制器
    public static final int CON_CONNECTING = 15;        //正在连接控制器
    public static final int CON_BONDING = 16;           //正在绑定控制器,并验证密码
    public static final int CON_BONDING_ERR = 17;           //绑定控制器失败
    public static final int CON_BONDING_ERR_MIMA = 18;           //绑定控制器密码错误
    public static final int CON_SCAN_NOTFOUND = 19;           //没有扫描到控制器
    public static final int CON_CONNECT_FAIL = 20;           //连接控制器失败
    public static final int CON_WAITING_RECONNECT = 21;           //连接控制器失败
    public static final int CON_CONNECTED = 22;           //控制器连接成功

    public static final int COLLECT_CON_DATA = 31;           //返回采集数据
    public static final int COLLECT_CON_STOP = 31;           //返回采集数据

    public static final int Begin_BTN_MOVEED = 100;           //开始设计页面中按钮移动结束
}

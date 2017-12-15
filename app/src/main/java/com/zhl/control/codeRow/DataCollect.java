package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class DataCollect extends CodeRow{
    public static final int 超声测距 = 111;
    public static final int 声音检测 = 112;
    public static final int 温度检测 = 113;
    public static final int 亮度检测 = 114;
    public static final int 按键检测 = 115;
    public static final int 灰度检测 = 116;
    public static final int 电量检测 = 117;
    public static final int 电流检测 = 118;

    private int code;
    private ParameterInteger port;
    private ParameterVar resultVar;



    public String[] getParameterNames(){
        return new String[]{"port","resultVar"};
    }

    /**
     *传感器信号采集
     * @param sensor  传感器类型
     * @param port      1-8，
     */
    public DataCollect(int sensor, ParameterInteger port) {
        this.code = sensor;
        this.port = port;
        this.resultVar = new ParameterVar(40+port.getValue());//41到48
    }

    public int getCode() {
        return code;
    }

    public ParameterInteger getPort() {
        return port;
    }
    public ParameterVar getResultVar() {
        return resultVar;
    }
}

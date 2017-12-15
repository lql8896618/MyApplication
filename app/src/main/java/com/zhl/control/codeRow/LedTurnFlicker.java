package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class LedTurnFlicker  extends CodeRow{
    private int code = 103;
    private ParameterInteger port;
    private Parameter color;
    private Parameter interval;
    public String[] getParameterNames(){
        return new String[]{"port","color","interval"};
    }
    /**
     * @param port
     * @param color 1红；2绿；3蓝；4黄；5青；6紫；7白
     * @param interval 闪烁间隔 单位ms
     */
    public LedTurnFlicker(ParameterInteger port, Parameter color,Parameter interval) {
        this.port = port;
        this.color = color;
        this.interval = interval;
    }

    public int getCode() {
        return code;
    }

    public ParameterInteger getPort() {
        return port;
    }

    public Parameter getColor() {
        return color;
    }

    public Parameter getInterval() {
        return interval;
    }
}

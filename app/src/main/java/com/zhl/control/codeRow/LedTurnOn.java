package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class LedTurnOn  extends CodeRow{
    private int code = 101;
    private ParameterInteger port;
    private Parameter color;

    /**
     * @param port //5-8
     * @param color 1红；2绿；3蓝；4黄；5青；6紫；7白
     */
    public LedTurnOn(ParameterInteger port, Parameter color) {
        this.port = port;
        this.color = color;
    }
    public String[] getParameterNames(){
        return new String[]{"port","color"};
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
}

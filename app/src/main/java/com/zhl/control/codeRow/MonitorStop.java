package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class MonitorStop  extends CodeRow{
    private int code = 106;
    private ParameterInteger port;
    private Parameter method;

    public String[] getParameterNames(){
        return new String[]{"port","method"};
    }

    public int getCode() {
        return code;
    }

    public ParameterInteger getPort() {
        return port;
    }

    public Parameter getMethod() {
        return method;
    }

    /**
     *
     * @param port 1-4
     * @param method 1：刹车    0：停车
     */
    public MonitorStop(ParameterInteger port, Parameter method) {

        this.port = port;
        this.method = method;
    }
}

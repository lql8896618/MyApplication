package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class MonitorSetup  extends CodeRow{
    private int code = 104;
    private ParameterInteger port;
    private Parameter direct;
    private Parameter speed;

    public String[] getParameterNames(){
        return new String[]{"port","direct","speed"};
    }

    /**
     * @param port  1-4
     * @param direct 1为正向    2为反向
     * @param speed 30到200  转/分
     */
    public MonitorSetup(ParameterInteger port, Parameter direct, Parameter speed) {
        this.port = port;
        this.direct = direct;
        this.speed = speed;
    }

    public int getCode() {
        return code;
    }

    public ParameterInteger getPort() {
        return port;
    }

    public Parameter getDirect() {
        return direct;
    }

    public Parameter getSpeed() {
        return speed;
    }
}

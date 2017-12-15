package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class MonitorStart  extends CodeRow{
    private int code = 105;
    private ParameterInteger port;
    private Parameter method;
    private Parameter value;

    public String[] getParameterNames(){
        return new String[]{"port","method","value"};
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

    public Parameter getValue() {
        return value;
    }

    /**
     *
     * @param port 1-4
     * @param method 1：角度    2：时间不刹车    3：时间刹车    4：永久
     * @param value 角度时：0到36000    时间时：0到50000毫秒    永久时：无效
     */
    public MonitorStart(ParameterInteger port, Parameter method, Parameter value) {

        this.port = port;
        this.method = method;
        this.value = value;
    }
}

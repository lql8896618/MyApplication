package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class LedTurnOff  extends CodeRow{
    private int code = 102;
    private ParameterInteger port;
    public String[] getParameterNames(){
        return new String[]{"port"};
    }

    /**
     * @param port //5-8
     */
    public LedTurnOff(ParameterInteger port) {
        this.port = port;
    }

    public int getCode() {
        return code;
    }

    public ParameterInteger getPort() {
        return port;
    }
}

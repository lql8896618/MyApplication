package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Delay extends CodeRow{
    private int code = 411;
    private Parameter time;

    public String[] getParameterNames(){
        return new String[]{"time"};
    }

    public Parameter getTime() {
        return time;
    }

    @Override
    public int getCode() {
        return code;
    }

    /**
     *
     * @param time 单位毫秒 1-60000  即60秒
     */
    public Delay(Parameter time) {

        this.time = time;
    }
}

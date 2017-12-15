package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class TimerStop extends CodeRow{
    private int code = 503;
    @Override
    public int getCode() {
        return code;
    }
    public String[] getParameterNames(){
        return new String[]{"time"};
    }

    /**
     * 取消定时，定时到时将不再触发
     */
    public TimerStop() {

    }


}

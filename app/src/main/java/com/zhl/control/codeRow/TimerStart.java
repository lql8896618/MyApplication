package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 * 启动定时器
 */
public class TimerStart extends CodeRow{
    private int code = 502;
    private Parameter time;
    @Override
    public int getCode() {
        return code;
    }
    public String[] getParameterNames(){
        return new String[]{"time"};
    }

    /**
     * 启动定时器
     * @param time     定时时间，毫秒  1-50000，即最大50秒
     */
    public TimerStart(Parameter time) {
        this.time = time;
    }


}

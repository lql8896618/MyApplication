package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class SoundPlay  extends CodeRow{
    private int code = 121;
    private Parameter index;
    private Parameter times;
    private Parameter interval;

    public String[] getParameterNames(){
        return new String[]{"index","times","interval"};
    }

    /**
     * 播放单音
     * @param index     声音编号	0-32（待定）
     * @param times     循环播放时表示循环次数（1到100）；
     * @param interval  间隔循环播放时表示间隔时间（0到5000毫秒）
     */
    public SoundPlay(Parameter index, Parameter times, Parameter interval) {
        this.index = index;
        this.times = times;
        this.interval = interval;
    }

    public Parameter getIndex() {
        return index;
    }

    @Override
    public int getCode() {
        return code;
    }

    public Parameter getTimes() {
        return times;
    }

    public Parameter getInterval() {
        return interval;
    }
}

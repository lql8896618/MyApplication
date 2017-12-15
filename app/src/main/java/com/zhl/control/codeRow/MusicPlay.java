package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 *
 */
public class MusicPlay extends CodeRow{
    private int code = 122;
    private Parameter arrayIndex;
    private Parameter method;
    private Parameter speed;

    public String[] getParameterNames(){
        return new String[]{"arrayIndex","method","speed"};
    }

    /**
     * 播放音乐
     * @param arrayIndex     数组索引，1－4共四个每个为1024字节
     * @param method    1播放一次    2循环播放
     * @param speed     播放速度（1到10）；数字越大越快
     */
    public MusicPlay(Parameter arrayIndex, Parameter method, Parameter speed) {
        this.arrayIndex = arrayIndex;
        this.method = method;
        this.speed = speed;
    }

    @Override
    public int getCode() {
        return code;
    }

    public Parameter getSpeed() {
        return speed;
    }

    public Parameter getArrayIndex() {
        return arrayIndex;
    }

    public Parameter getMethod() {
        return method;
    }
}

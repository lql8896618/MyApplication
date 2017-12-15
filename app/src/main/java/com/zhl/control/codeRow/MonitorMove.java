package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 * 控制双电机移动
 */
public class MonitorMove extends CodeRow{
    private int code = 107;
    private Parameter speed;
    private Parameter proportion;
    private Parameter dir;

    public String[] getParameterNames(){
        return new String[]{"speed","proportion","dir"};
    }

    public int getCode() {
        return code;
    }

    public Parameter getSpeed() {
        return speed;
    }

    public Parameter getProportion() {
        return proportion;
    }

    public Parameter getDir() {
        return dir;
    }

    /**
     *
     * @param speed 速度(0为停止) 0-200 转/分
     * @param proportion 左右轮转速比(0-200 100表示直行 0为左转 200为右转)
     * @param dir 方向 1为前进  2为后退
     */
    public MonitorMove(Parameter speed, Parameter proportion, Parameter dir) {

        this.speed = speed;
        this.proportion = proportion;
        this.dir = dir;
    }
}

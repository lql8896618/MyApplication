package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 * 计次循环
 */
public class LoopBeginTimes extends CodeRow{
    private int code = 403;
    private Parameter times;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID","times"};
    }

    public LoopBeginTimes(Parameter times, ParameterInteger groupID) {
        this.times = times;
        this.groupID = groupID;
    }

    @Override
    public int getCode() {
        return code;
    }

    public Parameter getTimes() {
        return times;
    }

    public ParameterInteger getGroupID() {
        return groupID;
    }
}

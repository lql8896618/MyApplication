package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 * 永久循环
 */
public class LoopBeginLimitless extends CodeRow{
    private int code = 402;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID"};
    }

    public LoopBeginLimitless(ParameterInteger groupID) {
        this.groupID = groupID;
    }

    @Override
    public int getCode() {
        return code;
    }
    public ParameterInteger getGroupID() {
        return groupID;
    }
}

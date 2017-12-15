package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 * 条件循环
 */
public class LoopBegin extends CodeRow{
    private int code = 401;
    private ParameterVar conditionVar;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID","conditionVar"};
    }

    public LoopBegin(ParameterVar conditionVar, ParameterInteger groupID) {
        this.conditionVar = conditionVar;
        this.groupID = groupID;
    }

    @Override
    public int getCode() {
        return code;
    }

    public ParameterVar getConditionVar() {
        return conditionVar;
    }

    public ParameterInteger getGroupID() {
        return groupID;
    }
}

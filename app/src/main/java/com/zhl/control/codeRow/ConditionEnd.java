package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ConditionEnd extends CodeRow{
    private int code = 304;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID"};
    }

    public ConditionEnd(ParameterInteger groupID) {
        this.groupID = groupID;
    }

    public ParameterInteger getGroupID() {
        return groupID;
    }

    @Override
    public int getCode() {
        return code;
    }
}

package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ConditionElse extends CodeRow{
    private int code = 302;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID"};
    }

    @Override
    public int getCode() {
        return code;
    }

    public ParameterInteger getGroupID() {
        return groupID;
    }

    public ConditionElse(ParameterInteger groupID) {

        this.groupID = groupID;
    }
}

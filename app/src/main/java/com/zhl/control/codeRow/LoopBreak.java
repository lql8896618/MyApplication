package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class LoopBreak extends CodeRow{
    private int code = 405;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID"};
    }

    public LoopBreak(ParameterInteger groupID) {
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

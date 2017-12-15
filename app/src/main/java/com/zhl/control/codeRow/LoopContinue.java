package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class LoopContinue extends CodeRow{
    private int code = 406;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID"};
    }

    public LoopContinue(ParameterInteger groupID) {
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

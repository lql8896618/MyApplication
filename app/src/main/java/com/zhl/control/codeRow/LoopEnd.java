package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class LoopEnd extends CodeRow{
    private int code = 404;
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

    public LoopEnd(ParameterInteger groupID) {

        this.groupID = groupID;
    }
}

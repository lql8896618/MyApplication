package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ConditionIf extends CodeRow{
    private int code = 301;
    private ParameterVar conditionVar;
    private ParameterInteger groupID;

    public String[] getParameterNames(){
        return new String[]{"groupID","conditionVar"};
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

    /**
     * if
     * @param conditionVar  指定一个变量
     * @param groupID  每一个条件都会对应一个条件组，if else end看成是一个组
     */
    public ConditionIf(ParameterVar conditionVar, ParameterInteger groupID) {

        this.conditionVar = conditionVar;
        this.groupID = groupID;
    }
}

package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Parameter {
    public static final int PARAM_VALUE = 0;
    public static final int PARAM_VAR = 1;
    private int paramType;
    private int value;

    public Parameter(int paramType, int value) {
        this.paramType = paramType;
        this.value = value;
    }

    public int getParamType() {
        return paramType;
    }

    public int getValue() {
        return value;
    }
}

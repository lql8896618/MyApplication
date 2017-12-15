package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class VarFromInt  extends CodeRow{
    private int code = 222;
    private ParameterVar resultVar;
    private Parameter object;
    private Parameter bitIndex;

    public String[] getParameterNames(){
        return new String[]{"resultVar","object","bitIndex"};
    }

    public int getCode() {
        return code;
    }

    public ParameterVar getResultVar() {
        return resultVar;
    }

    public Parameter getObject() {
        return object;
    }

    public Parameter getBitIndex() {
        return bitIndex;
    }

    /**
     * 从一个16位整数中取出一个位的值
     * @param resultVar 取出结果存储的变量
     * @param object    一个整数
     * @param bitIndex  位索引  0-15
     */
    public VarFromInt(ParameterVar resultVar, Parameter object, Parameter bitIndex) {
        this.resultVar = resultVar;
        this.object = object;
        this.bitIndex = bitIndex;
    }
}

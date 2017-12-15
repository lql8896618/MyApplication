package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class VarFromArray  extends CodeRow{
    private int code = 221;
    private ParameterVar resultVar;
    private Parameter arrayIndex;
    private Parameter elementIndex;

    public String[] getParameterNames(){
        return new String[]{"resultVar","arrayIndex","elementIndex"};
    }

    public int getCode() {
        return code;
    }

    public ParameterVar getResultVar() {
        return resultVar;
    }

    public Parameter getArrayIndex() {
        return arrayIndex;
    }

    public Parameter getElementIndex() {
        return elementIndex;
    }

    /**
     * 从一个数组内取出一个元素的值
     * @param resultVar 取出结果存储的变量
     * @param arrayIndex    第几个数组 1-4
     * @param elementIndex  数组的第几个元素
     */
    public VarFromArray(ParameterVar resultVar, Parameter arrayIndex, Parameter elementIndex) {

        this.resultVar = resultVar;
        this.arrayIndex = arrayIndex;
        this.elementIndex = elementIndex;
    }
}

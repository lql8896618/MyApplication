package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class VarCalculator  extends CodeRow{
    public static final int 赋值 = 201;
    public static final int 加 = 202;
    public static final int 减 = 203;
    public static final int 乘 = 204;
    public static final int 除 = 205;
    public static final int 求余 = 206;
    public static final int 与 = 207;
    public static final int 或 = 208;
    public static final int 非 = 209;
    public static final int 大于 = 210;
    public static final int 大于等于 = 211;
    public static final int 小于 = 212;
    public static final int 小于等于 = 213;
    public static final int 等于 = 214;
    public static final int 不等于 = 215;
    public static final int 逻辑与 = 216;
    public static final int 逻辑或 = 217;
    public static final int 逻辑非 = 218;
    public static final int 矩阵 = 210;


    private int code;
    private ParameterVar resultVar;
    private Parameter param1;
    private Parameter param2;

    public String[] getParameterNames(){
        return new String[]{"resultVar","param1","param2"};
    }

    /**
     * @param operator  运算符
     * @param resultVar 存储结果的变量
     * @param param1    运算数
     * @param param2    运算数
     */
    public VarCalculator(int operator, ParameterVar resultVar, Parameter param1, Parameter param2) {
        this.code = operator;
        this.resultVar = resultVar;
        this.param1 = param1;
        this.param2 = param2;
    }

    public int getCode() {
        return code;
    }

    public ParameterVar getResultVar() {
        return resultVar;
    }

    public Parameter getParam1() {
        return param1;
    }

    public Parameter getParam2() {
        return param2;
    }
}

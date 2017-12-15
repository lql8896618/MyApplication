package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 * 设置随机数的范围，范围如果为9，表示0到9的随机数
 */
public class Random extends CodeRow{
    private int code = 501;
    private Parameter scope;

    public String[] getParameterNames(){
        return new String[]{"scope"};
    }

    @Override
    public int getCode() {
        return code;
    }

    public Parameter getScope() {
        return scope;
    }

    /**
     * @param scope  2-12
     */
    public Random(Parameter scope) {
        this.scope = scope;
    }
}

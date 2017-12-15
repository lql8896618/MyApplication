package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public abstract class CodeRow {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public abstract String[] getParameterNames();
}

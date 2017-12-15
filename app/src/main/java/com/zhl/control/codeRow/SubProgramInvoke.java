package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class SubProgramInvoke extends CodeRow{
    private int code = 412;
    private Parameter programIndex;
    public String[] getParameterNames(){
        return new String[]{"programIndex"};
    }
    public int getCode() {
        return code;
    }

    public Parameter getProgramIndex() {
        return programIndex;
    }

    public SubProgramInvoke(Parameter programIndex) {

        this.programIndex = programIndex;
    }
}

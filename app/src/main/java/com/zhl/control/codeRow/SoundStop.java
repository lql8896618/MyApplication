package com.zhl.control.codeRow;

/**
 * Created by Administrator on 2017/9/28.
 */
public class SoundStop  extends CodeRow{
    private int code = 123;

    public String[] getParameterNames(){
        return new String[]{};
    }

    public SoundStop(){
    }

    @Override
    public int getCode() {
        return code;
    }
}

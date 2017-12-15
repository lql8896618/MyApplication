package com.zhl.design.data;

import com.zhl.control.codeRow.CodeRow;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/27 0027
 * 描述：
 */
public abstract class CodeRowConvertor {
    private List<CodeRow> list = new ArrayList<>();

    public static final int VAR_TEMP31 = 31;
    public static final int VAR_TEMP32 = 32;
    public static final int VAR_TEMP33 = 33;
    public static final int VAR_TEMP34 = 34;
    public static final int VAR_TEMP35 = 35;
    public static final int VAR_TEMP36 = 36;
    public static final int VAR_TEMP37 = 37;
    public static final int VAR_TEMP38 = 38;
    public static final int VAR_TEMP39 = 39;
    public static final int VAR_TEMP40 = 40;

    public static final int VAR_TIMER54 = 54;

    public void add(CodeRow codeRow){
        list.add(codeRow);
    }

    public List<CodeRow> getList() {
        List<CodeRow> copylist = new ArrayList<CodeRow>(list);
        list.clear();
        return copylist;
    }

    public void addAll(List<CodeRow> list){
        this.list.addAll(list);
    }

    public abstract void convert(NData nData, int group_index);
}

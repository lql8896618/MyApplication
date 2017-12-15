package com.zhl.design.node.editor;

/**
 * Created by LQL on 2017/12/15.
 * 描述：
 */

public interface ValueChangeListener {

    int ValueType_Image = 0;
    int ValueTlype_Number = 1;
    int ValueType_Variable = 2;

    void changeValue(int value, int type);

}

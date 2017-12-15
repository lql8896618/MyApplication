package com.zhl.design.node.n1;

import android.content.Context;

import com.zhl.design.data.NData;
import com.zhl.design.node.base.Node;
import com.zhl.design.node.editor.ImageFieldEditor;
import com.zhl.design.node.editor.ValueChangeListener;
import com.zhl.design.tools.NodeDefinition;
import com.zhl.design.tools.NodeResources;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/13 0013
 * 描述：
 */
public class N101 extends Node {

    public static final int _Port_Index = 0;
    public static final int _Direct_Index = 1;
    public static final int _Model_Index = 2;
    public static final int _Value_Index = 3;

    public N101(Context context) {
        super(context, NodeDefinition.N101电机控制);
    }

    @Override
    public void initProperties(NData nData) {
        NData.PropertyData port_data = nData.getPropertyData(_Port_Index);
        NData.PropertyData direct_data = nData.getPropertyData(_Direct_Index);
        NData.PropertyData model_data = nData.getPropertyData(_Model_Index);
        NData.PropertyData value_data = nData.getPropertyData(_Value_Index);

        ImageFieldEditor port_editor = new ImageFieldEditor(getContext(), "端口", NodeResources.PORT_Default, NodeResources.PORT_CHECKED, new Region(65, 76));
        port_editor.setChecked(0);
        port_editor.setValueChangeListener(new ValueChangeListener() {
            @Override
            public void changeValue(int value, int type) {

            }
        });
        addEditor(port_editor);
    }

}

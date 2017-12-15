package com.zhl.design.node;

import android.content.Context;

import com.zhl.design.data.NData;
import com.zhl.design.node.base.CNode;
import com.zhl.design.tools.NodeDefinition;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/7 0007
 * 描述：
 */
public class NRoot extends CNode {
    public NRoot(Context context) {
        super(context,
                NodeDefinition.N根节点,
                new Region(0, 0, 240, 60),
                new FlexRegion(0, 60, 60, 20, 240, 60));
    }

    @Override
    public void initProperties(NData nData) {

    }
}

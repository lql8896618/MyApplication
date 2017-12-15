package com.zhl.design.node.n3;

import android.content.Context;

import com.zhl.design.data.NData;
import com.zhl.design.node.base.CNode;
import com.zhl.design.tools.NodeDefinition;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/13 0013
 * 描述：
 */
public class N401IF extends CNode {
    public N401IF(Context context) {
        super(context, NodeDefinition.N401变量判断, new Region(300, 120), new FlexRegion(0, 120, 60, 20, 300, 40));
    }

    @Override
    public void initProperties(NData nData) {

    }
}

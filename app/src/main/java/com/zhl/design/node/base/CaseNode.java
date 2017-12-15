package com.zhl.design.node.base;

import android.content.Context;

import com.zhl.design.data.NData;
import com.zhl.design.tools.NodeDefinition;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/11 0011
 * 描述：
 */
public class CaseNode extends CNode {

    public CaseNode(Context context, NodeDefinition definition, Region headerRegion, FlexRegion bottomRegion) {
        super(context, definition,
                headerRegion,
                bottomRegion);
    }

    @Override
    public void initProperties(NData nData) {

    }
}

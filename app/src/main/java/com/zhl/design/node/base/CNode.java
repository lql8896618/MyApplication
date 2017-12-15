package com.zhl.design.node.base;

import android.content.Context;

import com.zhl.design.tools.FocusRegion;
import com.zhl.design.tools.NodeContainer;
import com.zhl.design.tools.NodeDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/7 0007
 * 描述：复合节点超类
 */
public abstract class CNode extends Node {

    private List<NodeContainer> nodeContainerList = new ArrayList<NodeContainer>(0);
    private NodeContainer bottomNodeContainer;

    public CNode(Context context, NodeDefinition definition, Region defaultHeaderRegion, FlexRegion defaultBottomRegion) {
        super(context, definition, defaultHeaderRegion);
        bottomNodeContainer = new NodeContainer(this, defaultBottomRegion);
    }

    @Override
    public List<FocusRegion> getFocusRegins() {
        List<FocusRegion> list = super.getFocusRegins();
        list.addAll(bottomNodeContainer.getFocusRegions());
        return list;
    }

    @Override
    public void translationVertical(int height) {
        //节点本身垂直平移
        super.translationVertical(height);

        //节点中的子节点垂直平移
        for(int i = 0; i < nodeContainerList.size(); i++){
            NodeContainer nodeContainer = nodeContainerList.get(i);
            for(int j = 0; j < nodeContainer.getChildNodes().size(); j++){
                Node node = nodeContainer.getChildNodes().get(j);
                node.translationVertical(height);
                nodeContainer.getChildNodes().get(j).translationVertical(height);
            }
        }
        //节点中的子节点垂直平移
        for(int j = 0; j < bottomNodeContainer.getChildNodes().size(); j++){
            bottomNodeContainer.getChildNodes().get(j).translationVertical(height);
        }
    }

    @Override
    public void delete() {
        //节点中的子节点删除
        for(int i = 0; i < nodeContainerList.size(); i++){
            NodeContainer nodeContainer = nodeContainerList.get(i);
            for(int j = nodeContainer.getChildNodes().size() - 1; j >= 0; j--){
                Node node = nodeContainer.getChildNodes().get(j);
                node.delete();
            }
            for(int j = 0; j < nodeContainer.getChildNodes().size(); j++){

            }
        }
        //节点中的子节点删除
        for(int j = bottomNodeContainer.getChildNodes().size() - 1; j >= 0; j--){
            bottomNodeContainer.getChildNodes().get(j).delete();
        }
        super.delete();
    }

    /**
     * 获取复合型节点所包含的子列表
     * @return
     */
    public List<NodeContainer> getNodeContainerList() {
        return nodeContainerList;
    }

    /**
     * 获取最下边的关节对象
     * @return
     */
    public NodeContainer getBottomNodeContainer() {
        return bottomNodeContainer;
    }

    /*
    ******************虚方法
     */



    /**
     * 关节部分的区域范围
     */
    public static class FlexRegion{
        public int xPos; //顶点相对于节点的X坐标
        public int yPos; //顶点相对于节点的Y坐标
        public int leftWidth; //左侧区域的宽度
        public int leftHeight; //左侧区域的高度
        public int bottomWidth; //底部区域的宽度
        public int bottomHeight; //底部区域的高度

        public FlexRegion(int xPos, int yPos, int leftWidth, int leftHeight, int bottomWidth, int bottomHeight){
            this.xPos = xPos;
            this.yPos = yPos;
            this.leftHeight = leftHeight;
            this.leftWidth = leftWidth;
            this.bottomHeight = bottomHeight;
            this.bottomWidth = bottomWidth;
        }
    }
}

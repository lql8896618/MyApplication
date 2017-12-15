package com.zhl.design.tools;

import android.content.ClipDescription;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;

import com.zhl.design.node.base.Node;
import com.zhl.design.node.base.CNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/7 0007
 * 描述：
 */
public class NodeContainer implements View.OnDragListener {

    private FrameLayout leftCover;
    private FrameLayout bottomCover;
    private CNode belongNode;

    private List<Node> childNodes = new ArrayList<Node>();
    private CNode.FlexRegion defaultCoverRegion;

    public NodeContainer(CNode belongNode, CNode.FlexRegion flexRegion){
        this.belongNode = belongNode;
        this.defaultCoverRegion = flexRegion;
        leftCover = new FrameLayout(belongNode.getContext());

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(0, 0);
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = flexRegion.leftWidth;
        layoutParams.height = flexRegion.leftHeight;
        layoutParams.leftMargin = flexRegion.xPos;
        layoutParams.topMargin = flexRegion.yPos;
        leftCover.setLayoutParams(layoutParams);
        leftCover.setBackgroundColor(Color.RED);


        bottomCover = new FrameLayout(belongNode.getContext());
        layoutParams = new FrameLayout.LayoutParams(0, 0);
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = flexRegion.bottomWidth;
        layoutParams.height = flexRegion.bottomHeight;
        layoutParams.leftMargin = flexRegion.xPos;
        layoutParams.topMargin = flexRegion.yPos + flexRegion.leftHeight;
        bottomCover.setLayoutParams(layoutParams);
        bottomCover.setBackgroundColor(Color.WHITE);

        belongNode.addView(leftCover, false, Color.GREEN);
        belongNode.addView(bottomCover, false, Color.GREEN);

        leftCover.setOnDragListener(this);
        bottomCover.setOnDragListener(this);

        leftCover.setAlpha(.0f);
        bottomCover.setAlpha(.0f);

    }

    /**
     * 获取可获焦的区域
     * @return
     */
    public List<FocusRegion> getFocusRegions(){
        List<FocusRegion> list = new ArrayList<FocusRegion>();
        list.add(new FocusRegion(
                belongNode.getX1() + (int)leftCover.getX(),
                belongNode.getY1() + (int)leftCover.getY(),
                leftCover.getWidth(),
                leftCover.getHeight(),
                1
        ));
        list.add(new FocusRegion(
                belongNode.getX1() + (int) bottomCover.getX(),
                belongNode.getY1() + (int) bottomCover.getY(),
                bottomCover.getWidth(),
                bottomCover.getHeight(),
                1
        ));
        return list;
    }

    /**
     * 获取当前节点容器相对于当前节点的Y轴位置
     * @return
     */
    public int getY_InNode(){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) leftCover.getLayoutParams();
        return layoutParams.topMargin;
    }

    /**
     * 获取当前节点容器相对于滚动面板的Y轴位置
     * @return
     */
    public int getY_InScrollView(){
        return belongNode.getY1() + getY_InNode();
    }

    public void stretch(int offset_height){
        AbsoluteLayout.LayoutParams aparams = (AbsoluteLayout.LayoutParams) belongNode.getLayoutParams();
        aparams.height = aparams.height + offset_height;
        belongNode.setLayoutParams(aparams);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) leftCover.getLayoutParams();
        layoutParams.height += offset_height;
        leftCover.setLayoutParams(layoutParams);
        layoutParams = (FrameLayout.LayoutParams) bottomCover.getLayoutParams();
        layoutParams.topMargin += offset_height;
        bottomCover.setLayoutParams(layoutParams);
    }

    /**
     * 改变高度
     * @param offset_height
     */
    public void changeHeight(int offset_height){
        stretch(offset_height);

//        if(this != belongNode.getBottomNodeContainer()){
//            int index = belongNode.getNodeContainerList().indexOf(this);
//            for(int i = index + 1; i < belongNode.getNodeContainerList().size(); i++){
//                NodeContainer next_content = belongNode.getNodeContainerList().get(i);
//                next_content.translationVertical(offset_height);
//            }
//        }

        LinkedList<Node> list = new LinkedList<Node>();

        Node temp_node = belongNode;
        while(true){
            if(temp_node.getContainer() == null) break;
            list.addLast(temp_node);
            temp_node.getContainer().stretch(offset_height);
            temp_node = temp_node.getContainer().getBelongNode();
        }

        for(int i = 0; i < list.size(); i++){
            temp_node = list.get(i);
            while(true){
                if(temp_node.getNextNode() == null) break;
                temp_node = temp_node.getNextNode();
                temp_node.translationVertical(offset_height);
            }
        }
    }

    /**
     * 获取水平坐标的偏移量
     * @return
     */
    private int getOffsetX(){
        return belongNode.getX1() + ((FrameLayout.LayoutParams) leftCover.getLayoutParams()).width;
    }
    /**
     * 添加一个节点到当前容器最后
     * @param node
     */
    public void addLast(Node node){
        insertBefore(node, null);
    }

    /**
     * 插入一个节点到指定节点之前
     * @param node
     * @param targetNode
     */
    public void insertBefore(Node node, Node targetNode){
        belongNode.getNodePanel().addView(node, true);
        int add_index = 0; //新节点需要插入到列表的下标位置
        int node_y = 0; //新节点最终的Y轴位置
        int offset_height = 0; //需要最终增加的高度

        if(childNodes.isEmpty()){
            add_index = 0;
            node_y = getY_InScrollView();
            offset_height = node.getHeight1() - defaultCoverRegion.leftHeight - Node.SlotHeight;
        }else{
            Node pre_node = null;
            offset_height = node.getHeight1() - Node.SlotHeight;
            if(targetNode == null){ //添加到最后
                add_index = childNodes.size();
                pre_node = childNodes.get(add_index - 1);
                node_y = pre_node.getY1() + pre_node.getHeight1() - Node.SlotHeight;
            }else{
                add_index = childNodes.indexOf(targetNode);
                pre_node = targetNode.getPreviousNode();
                if(pre_node == null){ //添加到第一个位置（注：需要为新增节点下边的节点增加一个分割线）
                    node_y = getY_InScrollView() - Node.SlotHeight;
                } else{ //插入到中间(注：需要为新增节点增加一个分割线)
                    node_y = pre_node.getY1() + pre_node.getHeight1() - Node.SlotHeight;
                }
            }

            if(pre_node != null) {
                node.setPreviousNode(pre_node);
                pre_node.setNextNode(node);
            }

            if(targetNode != null){
                node.setNextNode(targetNode);
                targetNode.setPreviousNode(node);
                //从目标节点开始依次向下串 且目标节点上边的分割线也跟着往下串
                Node temp_node = targetNode;
                while(true){
                    temp_node.translationVertical(offset_height);
                    if(temp_node.getNextNode() == null) break;
                    temp_node = temp_node.getNextNode();
                }
            }
        }

        node.setLocation(getOffsetX(), node_y);
        changeHeight(offset_height);
        node.setContainer(this);
        childNodes.add(add_index, node);

    }

    public void removeChild(Node node){
        int offset_height = 0;
        if(childNodes.size() == 1){
            offset_height = node.getHeight1() - defaultCoverRegion.leftHeight - Node.SlotHeight;
        }else{
            offset_height = node.getHeight1() - Node.SlotHeight;
            Node next_node = node.getNextNode();
            Node pre_node = node.getPreviousNode();
            if(next_node != null){
                if(pre_node != null) {
                    pre_node.setNextNode(next_node);
                }
                next_node.setPreviousNode(pre_node);
                Node temp_node = next_node;
                while(true){
                    temp_node.translationVertical(offset_height * -1);
                    if(temp_node.getNextNode() == null) break;
                    temp_node = temp_node.getNextNode();
                }
            }
            if(pre_node != null) pre_node.setNextNode(next_node);
        }
        childNodes.remove(node);
        belongNode.getNodePanel().removeView(node);
        changeHeight(offset_height * -1);
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        String type = "";
        String value = "";
        try {
            ClipDescription description = event.getClipDescription();
            String label = description.getLabel().toString();
            String[] arr = label.split("_");
            type = arr[0];
            value = arr[1];
        } catch (Exception e) {
            return true;
        }
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_ENTERED:{
                leftCover.setAlpha(.7f);
                bottomCover.setAlpha(.7f);
                break;
            }
            case DragEvent.ACTION_DRAG_EXITED:{
                leftCover.setAlpha(.0f);
                bottomCover.setAlpha(.0f);
                break;
            }
            case DragEvent.ACTION_DROP:{
                leftCover.setAlpha(.0f);
                bottomCover.setAlpha(.0f);
                if(type.equals("menu")){
                    String code = value;
                    Node new_node =  NodeDefinition.find(code).create(belongNode.getContext());
                    new_node.setNodePanel(belongNode.getNodePanel());
                    addLast(new_node);
                }else if(type.equals("node")){

                }
                break;
            }
            case DragEvent.ACTION_DRAG_ENDED:{
                leftCover.setAlpha(.0f);
                bottomCover.setAlpha(.0f);
                break;
            }
        }
        return true;
    }



    public List<Node> getChildNodes() {
        return childNodes;
    }

    public CNode getBelongNode() {
        return belongNode;
    }
}

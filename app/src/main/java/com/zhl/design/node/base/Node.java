package com.zhl.design.node.base;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.common.ZHLApplication;
import com.zhl.design.data.NData;
import com.zhl.design.dialog.AttributeEditorDialog;
import com.zhl.design.node.NodeView;
import com.zhl.design.DesignHolder;
import com.zhl.design.node.editor.FieldEditor;
import com.zhl.design.tools.FocusRegion;
import com.zhl.design.tools.NodeContainer;
import com.zhl.design.tools.NodeDefinition;
import com.zhl.design.tools.NodeHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/6 0006
 * 描述： 节点超类，提供拖动、删除、属性设定
 */
public abstract class Node extends FrameLayout {

    private Region defaultHeaderRegion;
    public static final int SlotHeight = 4;
    private TextView text;
    private ImageView icon;
    private List<FieldEditor> editorList = new ArrayList<>();

    public Node(Context context, NodeDefinition definition, Region defaultHeaderRegion) {
        super(context);
        this.nodeDefinition = definition;
        this.defaultHeaderRegion = defaultHeaderRegion;
        setLayoutParams(new AbsoluteLayout.LayoutParams(definition.getNodeResources().getRegion().width, definition.getNodeResources().getRegion().height, 0, 0));
        setBackgroundResource(nodeDefinition.getNodeResources().getBackgroundResource());
        text = new TextView(getContext());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
        text.setTextColor(Color.RED);
        text.getPaint().setFakeBoldText(true);
        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        layoutParams.leftMargin = 10;
        text.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        layoutParams.leftMargin = 10;
        icon = new ImageView(context);
        icon.setImageResource(definition.getNodeResources().getIconResources());
        icon.setLayoutParams(layoutParams);

        addView(text);
        addView(icon);

        nodeHeader = new NodeHeader(this, defaultHeaderRegion);
    }

    public Node(Context context, NodeDefinition definition){
        this(context, definition, definition.getNodeResources().getRegion().copy());
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        text.setText("ID " + id);
    }

    public void startDrag(int x, int y){
        ClipData.Item item = new ClipData.Item(getId() + "");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData dragData = new ClipData("node_" + getId(), mimeTypes, item);
        ZHLApplication.vibrate(200);

        this.startDrag(dragData, new View.DragShadowBuilder(this), null, 0);
    }

    public void delete(){
        container.removeChild(this);
    }

    /**
     * 设置节点位置
     * @param x
     * @param y
     */
    public void setLocation(int x, int y){
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) getLayoutParams();
        layoutParams.x = x;
        layoutParams.y = y;
        setLayoutParams(layoutParams);
    }

    /**
     * 平移到指定位置
     * @param x
     * @param y
     */
    public void transationTo(int x, int y){

    }

    public void addView(View child, boolean test) {
        super.addView(child);
        if(test) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) child.getLayoutParams();
            FrameLayout.LayoutParams layoutParams1 = new LayoutParams(1000, 2);
            layoutParams1.topMargin = layoutParams.topMargin;
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(layoutParams1);
            imageView.setBackgroundColor(Color.RED);
            addView(imageView);
        }
    }

    public void addView(View child, boolean test, int baseLineColor){
        super.addView(child);
        if(test) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) child.getLayoutParams();
            FrameLayout.LayoutParams layoutParams1 = new LayoutParams(1000, 2);
            layoutParams1.topMargin = layoutParams.topMargin;
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(layoutParams1);
            imageView.setBackgroundColor(baseLineColor);
            addView(imageView);
        }
    }

    /**
     * 上下平移
     * @param height
     */
    public void translationVertical(int height){
//        NodeContainer.SeparatorLine separatorLine = getPreviousSeparatorLine();
//        if(separatorLine != null) separatorLine.moveY(height);
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) getLayoutParams();
        layoutParams.y = layoutParams.y + height;
        setLayoutParams(layoutParams);
    }

    /**
     * 获取节点当前高度
     * @return
     */
    public int getHeight1(){
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) getLayoutParams();
        return layoutParams.height;
    }

    /**
     * 获取当前节点上边的分割线
     * @return
     */
//    public NodeContainer.SeparatorLine getPreviousSeparatorLine(){
//        if(separatorLineIndex == -1) return null;
//        return getContainer().getSeparatorLines().get(separatorLineIndex);
//    }

    /**
     * 获取节点当前位置的X坐标
     * @return
     */
    public int getX1(){
        return ((AbsoluteLayout.LayoutParams) getLayoutParams()).x;
    }

    /**
     * 获取当前节点位置的Y坐标
     * @return
     */
    public int getY1(){
        return ((AbsoluteLayout.LayoutParams) getLayoutParams()).y;
    }

    public void showPropertiesEditor(){
//        if(list != null && list.size() > 0) {
            AttributeEditorDialog dlg = new AttributeEditorDialog(this, editorList);
            dlg.show();
//        }
    }

    public void addEditor(FieldEditor editor){
        editorList.add(editor);
    }

    /*
     * ***************虚方法列表
     */
    public abstract void initProperties(NData nData);
    /*
     * 属性列表*********************************************************************************
     */

    private NodeDefinition nodeDefinition;
    private NodeContainer container;
    private Node previousNode;
    private Node nextNode;
    private NodeView nodePanel;
    private NodeHeader nodeHeader;
    private int separatorLineIndex = -1;

    public List<FocusRegion> getFocusRegins() {
        List<FocusRegion> list = new ArrayList<FocusRegion>();
        list.add(new FocusRegion(
                getX1() + defaultHeaderRegion.xPos,
                getY1() + defaultHeaderRegion.yPos,
                defaultHeaderRegion.width,
                defaultHeaderRegion.height,
                0
        ));
        return list;
    }

    public NodeHeader getNodeHeader() {
        return nodeHeader;
    }

    public NodeDefinition getNodeDefinition() {
        return nodeDefinition;
    }

    public NodeView getNodePanel() {
        return nodePanel;
    }
    public void setNodePanel(NodeView nodePanel) {
        this.nodePanel = nodePanel;
    }

    public void setContainer(NodeContainer container) {
        this.container = container;
    }
    public NodeContainer getContainer() {
        return container;
    }

    public Node getPreviousNode() {
        return previousNode;
    }
    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Node getNextNode() {
        return nextNode;
    }
    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void setSeparatorLineIndex(int separatorLineIndex) {
        this.separatorLineIndex = separatorLineIndex;
    }
    public int getSeparatorLineIndex() {
        return separatorLineIndex;
    }

    /*
     * 静态方法列表**************************************************************
     */
    public static int convertSize(int sourceLen){
        float scale = ZHLApplication.widthScreen / DesignHolder.width;
        return (int)(sourceLen * scale);
    }

    public static class Region{
        public int xPos;
        public int yPos;
        public int width;
        public int height;
        public Region(int xPos, int yPos, int width, int height){
            this.xPos = xPos;
            this.yPos = yPos;
            this.width = width;
            this.height = height;
        }

        public Region(int width, int height){
            this(0, 0, width, height);
        }

        public Region copy(){
            return new Region(xPos, yPos, width, height);
        }
    }
}

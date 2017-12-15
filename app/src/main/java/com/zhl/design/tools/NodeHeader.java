package com.zhl.design.tools;

import android.content.ClipDescription;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.zhl.design.node.base.Node;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/11 0011
 * 描述：
 */
public class NodeHeader implements View.OnDragListener {

    private Node belongNode;
    private FrameLayout focusLayout;

    public NodeHeader(Node node, Node.Region defaultRegion){
        this.belongNode = node;
        focusLayout = new FrameLayout(node.getContext());
        focusLayout.setBackgroundColor(Color.GRAY);
        focusLayout.setAlpha(0f);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(0, 0);
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = defaultRegion.width;
        layoutParams.height = defaultRegion.height;
        layoutParams.topMargin = defaultRegion.yPos;
        layoutParams.leftMargin = defaultRegion.xPos;
        focusLayout.setLayoutParams(layoutParams);
        focusLayout.setOnDragListener(this);

        node.addView(focusLayout);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
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
                focusLayout.setAlpha(.7f);
                break;
            }
            case DragEvent.ACTION_DRAG_EXITED:{
                focusLayout.setAlpha(.0f);
                break;
            }
            case DragEvent.ACTION_DROP:{
                focusLayout.setAlpha(.0f);
                if(type.equals("menu")){
                    String code = value;
                    Node new_node =  NodeDefinition.find(code).create(belongNode.getContext());
                    new_node.setNodePanel(belongNode.getNodePanel());
                    belongNode.getContainer().insertBefore(new_node, belongNode);
                }else if(type.equals("node")){

                }
                break;
            }
            case DragEvent.ACTION_DRAG_ENDED:{
                focusLayout.setAlpha(.0f);
                break;
            }

        }
        return true;
    }

    public int getHeight(){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) focusLayout.getLayoutParams();
        return layoutParams.height;
    }
}

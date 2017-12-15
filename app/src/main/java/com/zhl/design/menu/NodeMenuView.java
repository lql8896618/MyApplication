package com.zhl.design.menu;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhl.R;
import com.zhl.common.ZHLApplication;
import com.zhl.design.DesignHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/14 0014
 * 描述：
 */
public class NodeMenuView extends LinearLayout implements View.OnClickListener {
    private int groupWidth = 148;
    private int groupHeight = 48;
    private int itemWidth = 135;
    private int itemHeight = 48;

    private List<NodeGroupView> groupList = new ArrayList<>();

    private NodeGroupView expandGroup = null;

    public NodeMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        groupList.add(new NodeGroupView(context,
                R.drawable.design_nodemenu_category_n1_default,
                R.drawable.design_nodemenu_category_n1_checked,
                new ArrayList<NodeItem>() {
                    {
                        add(new NodeItem(R.drawable.design_nodemenu_item101, "101"));
                        add(new NodeItem(R.drawable.design_nodemenu_item102, "102"));
                        add(new NodeItem(R.drawable.design_nodemenu_item102, "103"));
                    }
                }));

        groupList.add(new NodeGroupView(context,
                R.drawable.design_nodemenu_category_n2_default,
                R.drawable.design_nodemenu_category_n2_checked,
                new ArrayList<NodeItem>() {
                    {
                        add(new NodeItem(R.drawable.design_nodemenu_item201, "201"));
                        add(new NodeItem(R.drawable.design_nodemenu_item202, "202"));
                        add(new NodeItem(R.drawable.design_nodemenu_item203, "203"));
                        add(new NodeItem(R.drawable.design_nodemenu_item204, "204"));
                        add(new NodeItem(R.drawable.design_nodemenu_item205, "205"));
                        add(new NodeItem(R.drawable.design_nodemenu_item206, "206"));
                    }
                }));

        groupList.add(new NodeGroupView(context,
                R.drawable.design_nodemenu_category_n3_default,
                R.drawable.design_nodemenu_category_n3_checked,
                new ArrayList<NodeItem>() {
                    {
                        add(new NodeItem(R.drawable.design_nodemenu_item301, "301"));
                        add(new NodeItem(R.drawable.design_nodemenu_item302, "302"));
                        add(new NodeItem(R.drawable.design_nodemenu_item303, "303"));
                        add(new NodeItem(R.drawable.design_nodemenu_item304, "304"));
                        add(new NodeItem(R.drawable.design_nodemenu_item305, "305"));
                        add(new NodeItem(R.drawable.design_nodemenu_item306, "306"));
                        add(new NodeItem(R.drawable.design_nodemenu_item307, "307"));
                        add(new NodeItem(R.drawable.design_nodemenu_item308, "308"));
                    }
                }));

        groupList.add(new NodeGroupView(context,
                R.drawable.design_nodemenu_category_n4_default,
                R.drawable.design_nodemenu_category_n4_checked,
                new ArrayList<NodeItem>(){
                    {
                        add(new NodeItem(R.drawable.design_nodemenu_item401, "401"));
                        add(new NodeItem(R.drawable.design_nodemenu_item402, "402"));
                        add(new NodeItem(R.drawable.design_nodemenu_item403, "403"));
                        add(new NodeItem(R.drawable.design_nodemenu_item404, "404"));
                        add(new NodeItem(R.drawable.design_nodemenu_item405, "405"));
                        add(new NodeItem(R.drawable.design_nodemenu_item406, "406"));
                        add(new NodeItem(R.drawable.design_nodemenu_item407, "407"));
                        add(new NodeItem(R.drawable.design_nodemenu_item408, "408"));
                        add(new NodeItem(R.drawable.design_nodemenu_item409, "409"));
                    }
        }));

        groupList.add(new NodeGroupView(context,
                R.drawable.design_nodemenu_category_n10_default,
                R.drawable.design_nodemenu_category_n10_checked,
                new ArrayList<NodeItem>(){
                    {
                        add(new NodeItem(R.drawable.design_nodemenu_item1001, "1001"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1002, "1002"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1003, "1003"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1004, "1004"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1005, "1005"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1006, "1006"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1007, "1007"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1008, "1008"));
                        add(new NodeItem(R.drawable.design_nodemenu_item1009, "1009"));
                    }
                }));

        groupList.add(new NodeGroupView(context,
                R.drawable.design_nodemenu_category_n5_default,
                R.drawable.design_nodemenu_category_n5_checked,
                new ArrayList<NodeItem>(){
                    {
                        add(new NodeItem(R.drawable.design_nodemenu_item501, "501"));
                        add(new NodeItem(R.drawable.design_nodemenu_item502, "502"));
                        add(new NodeItem(R.drawable.design_nodemenu_item503, "503"));
                        add(new NodeItem(R.drawable.design_nodemenu_item504, "504"));
                        add(new NodeItem(R.drawable.design_nodemenu_item505, "505"));
                    }
                }));

        for(int i = 0; i < groupList.size(); i++){
            groupList.get(i).setOnClickListener(this);
            addView(groupList.get(i));
        }
    }

    @Override
    public void onClick(View v) {
        if(expandGroup == v){
            expandGroup.collapse();
            expandGroup = null;
        }else {
            if (expandGroup != null) {
                expandGroup.collapse();
            }
            ((NodeGroupView) v).expand();
            expandGroup = (NodeGroupView) v;
        }
    }

    class NodeGroupView extends LinearLayout implements OnLongClickListener{

        private ImageView groupImage;
        private LinearLayout childLayout;
        private List<NodeItem> nodeItemList;
        private int collapseResources;
        private int expandResources;

        public NodeGroupView(Context context, int collapseResources, int expandResources, List<NodeItem> items) {
            super(context);
            this.nodeItemList = items;
            this.collapseResources = collapseResources;
            this.expandResources = expandResources;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = ZHLApplication.convertSize(20, DesignHolder.width);
            setOrientation(VERTICAL);
            setLayoutParams(layoutParams);

            layoutParams = new LinearLayout.LayoutParams(ZHLApplication.convertSize(groupWidth, DesignHolder.width), ZHLApplication.convertSize(groupHeight, DesignHolder.width));
            groupImage = new ImageView(context);
            groupImage.setImageResource(collapseResources);
            groupImage.setLayoutParams(layoutParams);

            layoutParams = new LinearLayout.LayoutParams(ZHLApplication.convertSize(groupWidth, DesignHolder.width), ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = ZHLApplication.convertSize(10, DesignHolder.width);
            childLayout = new LinearLayout(context);
            childLayout.setOrientation(VERTICAL);
            childLayout.setLayoutParams(layoutParams);
            childLayout.setBackgroundResource(R.drawable.design_nodemenu_itemback);
            childLayout.setVisibility(GONE);

            for(int i = 0 ; i < nodeItemList.size(); i++){
                ImageView image = new ImageView(context);
                layoutParams = new LinearLayout.LayoutParams(ZHLApplication.convertSize(itemWidth, DesignHolder.width), ZHLApplication.convertSize(itemHeight, DesignHolder.width));
                layoutParams.topMargin = ZHLApplication.convertSize(5, DesignHolder.width);
                layoutParams.bottomMargin = ZHLApplication.convertSize(5, DesignHolder.width);
                image.setLayoutParams(layoutParams);
                image.setImageResource(items.get(i).itemResources);
                image.setTag(i);
                image.setOnLongClickListener(this);
                childLayout.addView(image);
            }

            addView(groupImage);
            addView(childLayout);
        }

        @Override
        public boolean onLongClick(View v) {
            Integer index = (Integer) v.getTag();
            String code = nodeItemList.get(index).code;
            ClipData.Item item = new ClipData.Item(code);
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData dragData = new ClipData("menu_" + code, mimeTypes, item);
            ZHLApplication.vibrate(200);
            return v.startDrag(dragData, new View.DragShadowBuilder(v), null, 0);
        }

        public void expand(){
            childLayout.setVisibility(VISIBLE);
            groupImage.setImageResource(expandResources);
        }

        public void collapse(){
            childLayout.setVisibility(GONE);
            groupImage.setImageResource(collapseResources);
        }
    }

    class NodeItem{
        public int itemResources;
        public String code;
        public NodeItem(int itemResources, String code){
            this.itemResources = itemResources;
            this.code = code;
        }
    }
}

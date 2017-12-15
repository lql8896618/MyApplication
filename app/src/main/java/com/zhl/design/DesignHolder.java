package com.zhl.design;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.View;

import com.zhl.R;
import com.zhl.common.ZHLApplication;
import com.zhl.design.node.NodeView;
import com.zhl.design.node.base.Node;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/7 0007
 * 描述：
 */
public class DesignHolder implements View.OnLongClickListener, View.OnDragListener {
    public static final float width = 750f;
    private DesignActivity activity;
    private NodeView scrollView;
    public DesignHolder(DesignActivity activity){
        this.activity = activity;
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_title_pname), width);
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_title_btn_help), width);
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_title_btn_menu), width);
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_title_image_switch), width);
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_title_btn_switch), width);
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_content_scroller), width);
        ZHLApplication.convertViewSize(activity.findViewById(R.id.design_main_nodemenu), width);
        scrollView = activity.findViewById(R.id.design_main_content_scroller);

        activity.findViewById(R.id.design_main_btn_create).setOnLongClickListener(this);
        activity.findViewById(R.id.design_main_btn_rename).setOnLongClickListener(this);
        activity.findViewById(R.id.design_main_btn_exchange).setOnLongClickListener(this);
        activity.findViewById(R.id.design_main_btn_save).setOnLongClickListener(this);
        activity.findViewById(R.id.design_main_btn_run).setOnLongClickListener(this);
        activity.findViewById(R.id.design_main_btn_debug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.resetNode();
            }
        });

        activity.findViewById(R.id.design_main_panel_recycle).setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item(v.getTag().toString() + "");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData dragData = new ClipData("menu_" + v.getTag().toString(), mimeTypes, item);
        ZHLApplication.vibrate(200);
        return v.startDrag(dragData, new View.DragShadowBuilder(v), null, 0);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_ENTERED:
                activity.findViewById(R.id.design_main_panel_recycle).setAlpha(1.0f);
                break;
            case DragEvent.ACTION_DROP:
                ClipDescription description = event.getClipDescription();
                String label = description.getLabel().toString();
                String[] arr = label.split("_");
                String type = arr[0];
                if(type.equals("node")) {
                    String value = arr[1];
                    Node node = scrollView.findViewById(Integer.parseInt(value));
                    node.delete();
                }else if(type.equals("case")){

                }
//                com.zhl.design.view.node.base.Node node = findNode(value);
//                node.remove();

                break;
            case DragEvent.ACTION_DRAG_EXITED:
                activity.findViewById(R.id.design_main_panel_recycle).setAlpha(0.7f);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                activity.findViewById(R.id.design_main_panel_recycle).setAlpha(0.7f);
                activity.findViewById(R.id.design_main_panel_recycle).setVisibility(View.GONE);
                break;
        }
        return true;
    }
}

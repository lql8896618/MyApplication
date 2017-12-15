package com.zhl.design.node;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import com.zhl.R;
import com.zhl.common.ZHLApplication;
import com.zhl.design.DesignActivity;
import com.zhl.design.node.base.CNode;
import com.zhl.design.node.base.Node;
import com.zhl.design.tools.FocusRegion;
import com.zhl.design.tools.NodeContainer;

import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/7 0007
 * 描述：
 */
public class NodeView extends AbsoluteLayout {
    private DesignActivity activity;

    public static NRoot nRoot;

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activity = (DesignActivity) context;
        mScroller = new Scroller(context);

        nRoot = new NRoot(activity){};
        nRoot.setNodePanel(this);
        addView(nRoot);
    }

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    private float lastX = 0;
    private float lastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        ZHLApplication.error("onInterceptTouchEvent");
        return true;
    }

    public void resetNode(){
        removeAllViews();
        nRoot = new NRoot(activity);
        nRoot.setNodePanel(this);
        addView(nRoot);
    }

    public void addView(View child, boolean test) {
        super.addView(child);
        if(test) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) child.getLayoutParams();
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(getWidth(), 2, 0, layoutParams.y));
            imageView.setBackgroundColor(Color.BLACK);
            addView(imageView);
        }
    }

    public void refresh(){
    }

    private void refreshNode(CNode cnode){
        List<NodeContainer> content_list = cnode.getNodeContainerList();
        for(int i = 0; i < content_list.size(); i++){
            NodeContainer node_content = content_list.get(i);
            List<Node> node_list = node_content.getChildNodes();
            for(int j = 0; j < node_list.size(); j++){
                Node node = node_list.get(j);
                if(node instanceof CNode)
                    refreshNode((CNode)node);
                AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) node.getLayoutParams();
                int height = layoutParams.height;
                NodeContainer nc = node.getContainer();

                while(true){
                    if(nc == null) break;
                    CNode parent = nc.getBelongNode();

                }
            }
        }
    }

    private void onLongClickEvent(){
        int[] pos = getClickPosition();
        int x = pos[0];
        int y = pos[1];
        for(int i = 0; i < getChildCount(); i++){
            if(getChildAt(i) instanceof Node) {
                Node node = (Node) getChildAt(i);
                for (int j = 0; j < node.getFocusRegins().size(); j++) {
                    FocusRegion region = node.getFocusRegins().get(j);
                    int type = region.isFocus(x, y);
                    if (type >= 0) {
                        node.startDrag(x, y);
                        activity.findViewById(R.id.design_main_panel_recycle).setVisibility(VISIBLE);
                        return;
                    }
                }
            }
        }
    }

    private void onClickEvent(){
        int[] pos = getClickPosition();
        int x = pos[0];
        int y = pos[1];
        for(int i = 0; i < getChildCount(); i++){
            if(getChildAt(i) instanceof Node) {
                Node node = (Node) getChildAt(i);
                for (int j = 0; j < node.getFocusRegins().size(); j++) {
                    FocusRegion region = node.getFocusRegins().get(j);
                    int type = region.isFocus(x, y);
                    if (type >= 0) {
                        node.showPropertiesEditor();
                        return;
                    }
                }
            }
        }
    }

    private int[] getClickPosition(){
        return new int[]{(int)(downX + mScroller.getCurrX()), (int)(downY + mScroller.getCurrY())};
    }

    private boolean isLongClick = false;
    private boolean stop = false;
    private float downX = 0f;
    private float downY = 0f;

    private Runnable longClickHandler = new Runnable() {
        @Override
        public void run() {
            int timer = 0;
            while(true){
                if(stop) break;
                if(timer >= 500){
                    isLongClick = true;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLongClickEvent();
                        }
                    });
                    break;
                }
                try {
                    Thread.sleep(1);
                    timer++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = lastX = event.getX();
                downY = lastY = event.getY();
//                    Log.e("pingmu", downX + "," + downY);
//                    Log.e("kongjian", mScroller.getCurrX() + "," + mScroller.getCurrY());
                stop = false;
                new Thread(longClickHandler).start();
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                float offsetX = Math.abs(event.getX() - downX);
                float offsetY = Math.abs(event.getY() - downY);
                if(isLongClick) break;
                if(offsetX >= 20 || offsetY >= 20) stop = true;
                ZHLApplication.error("onTouchEvent-->" + offsetX + "," + offsetY + "," +stop);
                if(stop || !isLongClick){

                    float raw_x = event.getRawX();
                    float raw_y = event.getRawY();
                    float scroll_x = (lastX - raw_x);
                    float scroll_y = (lastY - raw_y);
                    if (mScroller.getFinalX() + scroll_x < 0)
                        scroll_x = mScroller.getFinalX() * -1;
                    if (mScroller.getFinalY() + scroll_y < 0)
                        scroll_y = mScroller.getFinalY() * -1;
//                        if (mScroller.getFinalX() + scroll_x > (int) (ZHLApplication.widthScreen * 0.5))
//                            scroll_x = (int) (ZHLApplication.widthScreen * 0.5) - mScroller.getFinalX();
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), (int) scroll_x, (int) scroll_y);
                    lastX = raw_x;
                    lastY = raw_y;
                    invalidate();
//                        Log.e("DContent", "onTouchEvent_ACTION_MOVE:");
                }

                break;
            }
            case MotionEvent.ACTION_UP: {

                if(stop || !isLongClick){
                    float offsetX = Math.abs(event.getX() - downX);
                    float offsetY = Math.abs(event.getY() - downY);
                    if((event.getEventTime() - event.getDownTime() < 300) && (offsetX < 20 && offsetY < 20)){
                        onClickEvent();
                    }
                    int final_y = mScroller.getFinalY();
                    if (final_y < 0) {
                        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, final_y * -1);
                        invalidate();
                    }

                    int final_x = mScroller.getFinalX();
                    if (final_x < 0) {
                        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), final_x * -1, 0);
                        invalidate();
                    }
                }
                stop = true;
                isLongClick = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}

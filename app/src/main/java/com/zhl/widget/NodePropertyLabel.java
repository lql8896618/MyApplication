package com.zhl.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhl.common.ZHLApplication;
import com.zhl.R;

/**
 * 作者：刘启亮
 * 创建时间： 2017/10/20 0020
 * 描述： 作用于节点属性弹出框中的显示标签,布局文件中使用
 */
public class NodePropertyLabel extends FrameLayout {

    private TextView label;

    public NodePropertyLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.NodePropertyLabel);
        String text = typedArray.getString(R.styleable.NodePropertyLabel_label);

        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        label = new TextView(context);
        label.setText(text);
        label.setTextColor(Color.BLACK);
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
        label.setTypeface(ZHLApplication.getFont());
        label.getPaint().setFakeBoldText(true);
        label.setLayoutParams(layoutParams);

        addView(label);
    }

    public void setLabel(String text){
        label.setText(text);
    }
}

package com.zhl.design.node.editor;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LQL on 2017/12/15.
 * 描述：
 */

public class FieldEditor<T extends FieldEditor> extends LinearLayout {
    public static final int Field_Height = 120;
    public static final int Label_Width = 200;

    private TextView fieldLabel;
    private LinearLayout contentLayout;
    ValueChangeListener valueChangeListener;

    public FieldEditor(Context context, String labelText) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Field_Height));
        setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        fieldLabel = new TextView(context);
        fieldLabel.setLayoutParams(layoutParams);
        fieldLabel.setText(labelText);
        fieldLabel.setGravity(Gravity.CENTER);
        fieldLabel.setTextColor(Color.WHITE);
        fieldLabel.getPaint().setFakeBoldText(true);
        fieldLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

        contentLayout = new LinearLayout(context);
        contentLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentLayout.setOrientation(HORIZONTAL);



        layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 4;
        HorizontalScrollView scrollView = new HorizontalScrollView(getContext());

        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(contentLayout);

        addView(fieldLabel);
        addView(scrollView);
    }

    public T setValueChangeListener(ValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
        return (T)this;
    }

    void addContent(View view){
        contentLayout.addView(view);
    }
}

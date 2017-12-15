package com.zhl.design.node.editor;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LQL on 2017/12/15.
 * 描述：
 */

public class DigitField extends FieldEditor  {

    public static final int Text_Width = 200;
    private TextView valueText;

    public DigitField(Context context, String labelText) {
        super(context, labelText);

        LinearLayout.LayoutParams layoutParams = new LayoutParams(Text_Width, ViewGroup.LayoutParams.MATCH_PARENT);
        valueText = new TextView(context);
        valueText.setLayoutParams(layoutParams);
        valueText.setGravity(Gravity.CENTER);
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

        addContent(valueText);
    }
}

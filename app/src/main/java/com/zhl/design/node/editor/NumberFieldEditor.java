package com.zhl.design.node.editor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhl.R;

/**
 * Created by LQL on 2017/12/15.
 * 描述：
 */

public class NumberFieldEditor extends DigitField implements View.OnClickListener {

    private Button showButton;
    private int value;
    private int maxValue;
    private int minValue;
    private boolean infinite;

    public NumberFieldEditor(Context context, String labelText, int value, int maxValue, int minValue, boolean infinite) {
        super(context, labelText);

        this.value = value;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.infinite = infinite;

        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        showButton = new Button(context);
        showButton.setLayoutParams(layoutParams);
        showButton.setBackgroundResource(R.drawable.design_node_editor_button1);
        showButton.setText("输入数值");
        showButton.setOnClickListener(this);

        addContent(showButton);
    }

    @Override
    public void onClick(View v) {

    }
}

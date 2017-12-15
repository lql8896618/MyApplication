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

public class VariableFieldEditor extends DigitField implements View.OnClickListener {

    private Button showButton;
    private boolean allVariables;

    public VariableFieldEditor(Context context, String labelText, boolean all) {
        super(context, labelText);
        this.allVariables = all;

        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        showButton = new Button(context);
        showButton.setLayoutParams(layoutParams);
        showButton.setBackgroundResource(R.drawable.design_node_editor_button1);
        showButton.setText("选择变量");
        showButton.setOnClickListener(this);

        addContent(showButton);
    }

    @Override
    public void onClick(View v) {

    }
}

package com.zhl.design.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zhl.R;
import com.zhl.common.ZHLApplication;
import com.zhl.design.node.base.Node;
import com.zhl.design.node.editor.FieldEditor;

import java.util.List;

/**
 * Created by LQL on 2017/12/15.
 * 描述：
 */

public class AttributeEditorDialog extends Dialog {

    private List<FieldEditor> editors;
    private Node node;
    private FrameLayout contentView;
    private LinearLayout mainLayout;

    public AttributeEditorDialog(Node node, List<FieldEditor> editors) {
        super(node.getContext(), R.style.ActionSheetDialogStyle);
        this.node = node;
        this.editors = editors;

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ZHLApplication.widthScreen;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);

        ViewGroup.LayoutParams vparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        contentView = new FrameLayout(node.getContext());
        contentView.setLayoutParams(vparams);
        contentView.setBackgroundResource(R.drawable.design_dialog_editor_background);
        setContentView(contentView);


        mainLayout = new LinearLayout(getContext());
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams fparams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fparams.leftMargin = 50;
        fparams.topMargin = 40;
        fparams.rightMargin = 45;
        fparams.bottomMargin = 35;
        mainLayout.setLayoutParams(fparams);
        //mainLayout.setPadding(50, 40, 45, 35);
        for(int i = 0; i < editors.size(); i++){
            mainLayout.addView(editors.get(i));
        }


        contentView.addView(mainLayout);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        mainLayout.removeAllViews();
        super.dismiss();
    }
}

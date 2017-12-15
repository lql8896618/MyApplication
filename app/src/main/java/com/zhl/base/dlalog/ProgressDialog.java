package com.zhl.base.dlalog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.common.ZHLApplication;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/30 0030
 * 描述：
 */
public class ProgressDialog extends Dialog {

    private TextView titleTxt;
    private ProgressBar progressBar;

    public ProgressDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        setContentView(R.layout.design_dlg_download);
        titleTxt = findViewById(R.id.design_dlg_download_txt_title);
        progressBar = findViewById(R.id.design_dlg_download_progressbar);

        titleTxt.setText("正在下载 0%");
        setCanceledOnTouchOutside(false);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int)(ZHLApplication.widthScreen * .8f);
        lp.height = (int)(ZHLApplication.heightScreen * .20f);
        dialogWindow.setAttributes(lp);
    }
}

package com.zhl.production.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zhl.R;
import com.zhl.common.ZHLApplication;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/1 0001
 * 描述：
 */
public class ChapterSelectionDialog extends Dialog {

    private ListView listView;

    public ChapterSelectionDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        setContentView(R.layout.production_dlg_list_chapter);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = ZHLApplication.heightScreen / 2;
        //lp.y = 20;
        dialogWindow.setAttributes(lp);


        listView = findViewById(R.id.production_dlg_list_chapter_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, new String[]{
                "第1节  啊啊啊啊啊啊啊",
                "第2节  啊啊啊啊啊啊啊",
                "第3节  啊啊啊啊啊啊啊",
                "第4节  啊啊啊啊啊啊啊",
                "第5节  啊啊啊啊啊啊啊",
                "第6节  啊啊啊啊啊啊啊",
                "第2节  啊啊啊啊啊啊啊",
                "第3节  啊啊啊啊啊啊啊",
                "第4节  啊啊啊啊啊啊啊",
                "第5节  啊啊啊啊啊啊啊",
                "第6节  啊啊啊啊啊啊啊",
                "第2节  啊啊啊啊啊啊啊",
                "第3节  啊啊啊啊啊啊啊",
                "第4节  啊啊啊啊啊啊啊",
                "第5节  啊啊啊啊啊啊啊",
                "第6节  啊啊啊啊啊啊啊",
                "第7节  啊啊啊啊啊啊啊"
        });
        listView.setAdapter(adapter);
    }


}

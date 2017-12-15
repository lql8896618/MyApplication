package com.zhl.production.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.base.view.BaseActivity;
import com.zhl.widget.viewflow.CircleFlowIndicator;
import com.zhl.widget.viewflow.ViewFlow;
import com.zhl.production.dialog.ChapterSelectionDialog;
import com.zhl.production.view.adapter.SheetAdapter;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/21 0021
 * 描述： 章节列表
 */
public class SheetActivity extends BaseActivity {

    private TextView titleText;
    private ViewFlow pagerView;
    private ImageView selChapterImage;

    private String dirName;
    private int pageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trends.ttf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.production_sheet_main);
        titleText = findViewById(R.id.production_sheet_main_txt_pname);
        pagerView = findViewById(R.id.production_sheet_main_pager);
        selChapterImage = findViewById(R.id.production_sheet_main_button_selchapter);

        titleText.setTypeface(typeface);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 72);

        Bundle bundle = this.getIntent().getExtras();
        dirName = bundle.getString("dirName");

        SharedPreferences sp = getSharedPreferences(getString(R.string.sp_last_read_production), Context.MODE_PRIVATE);
        pageNum = sp.getInt(dirName, 0);
        titleText.setText(bundle.getString("productionName"));

        SheetAdapter adapter = new SheetAdapter(this);
        pagerView.setOnViewSwitchListener(new ViewFlow.ViewSwitchListener() {
            @Override
            public void onSwitched(View view, int position) {
                SharedPreferences sp = getSharedPreferences(getString(R.string.sp_last_read_production), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(dirName, position);
                editor.commit();
            }
        });
        adapter.initSheetList(dirName);
        pagerView.setAdapter(adapter, pageNum);
        CircleFlowIndicator indic = findViewById(R.id.production_sheet_main_pager_circle);
        pagerView.setFlowIndicator(indic);

        selChapterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterSelectionDialog dlg = new ChapterSelectionDialog(SheetActivity.this);
                dlg.show();
            }
        });
    }

}


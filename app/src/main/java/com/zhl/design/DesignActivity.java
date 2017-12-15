package com.zhl.design;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.zhl.R;
import com.zhl.base.view.BaseActivity;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/6 0006
 * 描述：
 */
public class DesignActivity extends BaseActivity {

    private FrameLayout recycleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_main);
        new DesignHolder(this);
        recycleLayout = findViewById(R.id.design_main_panel_recycle);
    }

    public void showRecycle(){
        recycleLayout.setVisibility(View.VISIBLE);
    }

}
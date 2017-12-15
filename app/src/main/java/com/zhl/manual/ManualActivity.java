package com.zhl.manual;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.zhl.R;

public class ManualActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        //隐藏电量栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new ManualActivityController(this);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CollectThread.getCollectThread().stopCollect();
    }
}

package com.zhl.setup;

import android.os.Bundle;

import com.zhl.R;
import com.zhl.base.view.BaseActivity;

public class SetupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        new SetupActivityController(this);
    }

}

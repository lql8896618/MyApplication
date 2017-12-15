package com.zhl.main;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.zhl.R;
import com.zhl.base.view.BaseActivity;
import com.zhl.common.Config;
import com.zhl.common.SoundUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdate();
        setContentView(R.layout.activity_main);
//        Config.loadConfig();
        ScaleAnimation beginAnimatioin = new ScaleAnimation(0.5f,1.5f,1,1.3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        beginAnimatioin.setDuration(1500);
        beginAnimatioin.setRepeatMode(Animation.REVERSE);
        beginAnimatioin.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.img_main_begin_animation).startAnimation(beginAnimatioin);

        //隐藏电量栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MainActivityController controller = new MainActivityController(this);


        SoundUtil.init(this);
        //启动背景音乐
        SoundUtil.bgMusicStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundUtil.setBgMusicVolume(Config.appBgVolume);
    }
}

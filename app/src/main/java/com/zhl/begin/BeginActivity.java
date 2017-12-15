package com.zhl.begin;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.zhl.R;
import com.zhl.common.Config;
import com.zhl.control.ControlGlobal;

public class BeginActivity extends Activity{

    AnimationSet rightToBottomSet = new AnimationSet(true);
    AnimationSet bottomToLeftSet = new AnimationSet(true);
    TranslateAnimation leftToRight;

    AnimationSet bottomToRightSet = new AnimationSet(true);
    AnimationSet leftToBottomSet = new AnimationSet(true);
    TranslateAnimation rightToLeft;

    ScaleAnimation enlarge;
    ScaleAnimation reduce;
    static BeginActivityController beginActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        //隐藏电量栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        beginActivityController = new BeginActivityController(this);

        ControlGlobal.init(this, beginActivityController, Config.controlName);
        beginActivityController.init();

        //初始化动画

        enlarge = new ScaleAnimation(1,1.5f,1,1.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        reduce = new ScaleAnimation(1,0.67f,1,0.67f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);


        leftToRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,2.8f,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        leftToRight.setDuration(600);

        rightToLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-2.8f,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        rightToLeft.setDuration(600);

        rightToBottomSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.94f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.05f));
        rightToBottomSet.addAnimation(enlarge);
        rightToBottomSet.setDuration(600);

        leftToBottomSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.94f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.05f));
        leftToBottomSet.addAnimation(enlarge);
        leftToBottomSet.setDuration(600);

        bottomToLeftSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.39f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.57f));
        bottomToLeftSet.addAnimation(reduce);
        bottomToLeftSet.setDuration(600);

        bottomToRightSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.39f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.57f));
        bottomToRightSet.addAnimation(reduce);
        bottomToRightSet.setDuration(600);


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        beginActivityController.init();
//    }

}

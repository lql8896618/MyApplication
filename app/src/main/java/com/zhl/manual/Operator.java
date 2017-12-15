package com.zhl.manual;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;

import com.zhl.R;
import com.zhl.control.CommManage;

/**
 * Created by Administrator on 2017/10/16.
 */
public class Operator {

    public static void manual1(ManualActivityController manualActivityController,View v){
        int port = ViewController.selectedPort;
        if(v.getId()==R.id.btn_manual_zzJiao){
            int speed = manualActivityController.seek_manual_speed.getProgress();
            CommManage.monitorStart(port, speed, 1, 1, 360);
        }else if(v.getId()==R.id.btn_manual_fzJiao){
            int speed = manualActivityController.seek_manual_speed.getProgress();
            CommManage.monitorStart(port, speed, 2, 1, 360);
        }else if(v.getId()==R.id.btn_manual_zzTime){
            int speed = manualActivityController.seek_manual_speed.getProgress();
            CommManage.monitorStart(port, speed, 1, 2, 5000);
        }else if(v.getId()==R.id.btn_manual_fzTime){
            int speed = manualActivityController.seek_manual_speed.getProgress();
            CommManage.monitorStart(port, speed, 2, 2, 5000);
        }else if(v.getId()==R.id.btn_manual_stop){
            CommManage.monitorStop(port, false);
        }else if(v.getId()==R.id.btn_manual_break){
            CommManage.monitorStop(port, true);
        }
    }

    public static void manual2(ManualActivityController manualActivityController,View v,int action){
        if(action ==1) {
            int speed = manualActivityController.seek_manual2_speed.getProgress();
            if (v.getId() == R.id.btn_manual2_qx) {
                CommManage.monitorDouble(speed, 100, 1);
            } else if (v.getId() == R.id.btn_manual2_qxzz) {
                CommManage.monitorDouble(speed, 50, 1);
            } else if (v.getId() == R.id.btn_manual2_qxyz) {
                CommManage.monitorDouble(speed, 150, 1);
            } else if (v.getId() == R.id.btn_manual2_ht) {
                CommManage.monitorDouble(speed, 100, 2);
            } else if (v.getId() == R.id.btn_manual2_htzz) {
                CommManage.monitorDouble(speed, 50, 2);
            } else if (v.getId() == R.id.btn_manual2_htyz) {
                CommManage.monitorDouble(speed, 150, 2);
            } else if (v.getId() == R.id.btn_manual2_zz) {
                CommManage.monitorDouble(speed, 0, 1);
            } else if (v.getId() == R.id.btn_manual2_yz) {
                CommManage.monitorDouble(speed, 200, 1);
            }
        }else{
            CommManage.monitorDouble(0, 200, 1);
        }
    }

    public static void manual3(ManualActivityController manualActivityController,View v) {
        Resources resources = manualActivityController.manualActivity.getApplicationContext().getResources();
        int port = ViewController.selectedPort;
        boolean flicker = false;
        if(v.getId() == R.id.btn_manual3_red){
            CommManage.controlLED(port, flicker?2:1,1,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_red));
        }else if(v.getId() == R.id.btn_manual3_green){
            CommManage.controlLED(port, flicker?2:1,2,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_green));
        }else if(v.getId() == R.id.btn_manual3_blue){
            CommManage.controlLED(port, flicker?2:1,3,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_blue));
        }else if(v.getId() == R.id.btn_manual3_yellow){
            CommManage.controlLED(port, flicker?2:1,4,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_yellow));
        }else if(v.getId() == R.id.btn_manual3_cyan){
            CommManage.controlLED(port,flicker?2:1,5,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_cyan));
        }else if(v.getId() == R.id.btn_manual3_purple){
            CommManage.controlLED(port,flicker?2:1,6,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_purple));
        }else if(v.getId() == R.id.btn_manual3_white){
            CommManage.controlLED(port,flicker?2:1,7,1000);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_white));
        }else if(v.getId() == R.id.btn_manual3_turnoff){
            CommManage.controlLED(port,0,0,0);
            manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_black));
        }
    }

    //音乐播放

    private static void changeSoundImg(ManualActivityController manualActivityController,int index){
        Resources resources = manualActivityController.manualActivity.getApplicationContext().getResources();
        switch (index){
            case 1:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s1));
                break;
            case 2:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s2));
                break;
            case 3:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s3));
                break;
            case 4:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s4));
                break;
            case 5:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s5));
                break;
            case 6:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s6));
                break;
            case 7:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s7));
                break;
            case 8:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s8));
                break;
            case 9:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s9));
                break;
            case 10:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s10));
                break;
            case 11:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s11));
                break;
            case 12:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s12));
                break;
            case 13:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s13));
                break;
            case 14:
                manualActivityController.img_manual4_soundimg.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_bg_s14));
                break;
        }
    }
    public static void manual4(ManualActivityController manualActivityController,View v) {

        if(v.getId() == R.id.btn_manual4_pre){
            ViewController.selectedSound--;
            if(ViewController.selectedSound<1)ViewController.selectedSound = 14;
            changeSoundImg(manualActivityController,ViewController.selectedSound);
        }else if(v.getId() == R.id.btn_manual4_next){
            ViewController.selectedSound++;
            if(ViewController.selectedSound>14)ViewController.selectedSound = 1;
            changeSoundImg(manualActivityController,ViewController.selectedSound);
        }else if(v.getId() == R.id.img_manual4_soundimg){
            CommManage.controlSound(0,ViewController.selectedSound);
        }else if(v.getId() == R.id.btn_manual4_m1){
            CommManage.controlSound(1,1);
        }else if(v.getId() == R.id.btn_manual4_m2){
            CommManage.controlSound(1,2);
        }else if(v.getId() == R.id.btn_manual4_m3){
            CommManage.controlSound(1,3);
        }

    }


    private static void changeCollect(ManualActivityController manualActivityController,int index){
        Resources resources = manualActivityController.manualActivity.getApplicationContext().getResources();
        switch (index){
            case 1:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_1));
                break;
            case 2:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_2));
                break;
            case 3:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_3));
                break;
            case 4:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_4));
                break;
            case 5:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_5));
                break;
            case 6:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_6));
                break;
            case 7:
                manualActivityController.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_7));
                break;


        }

    }
    public static void manual5(ManualActivityController manualActivityController,View v) {
        if(v.getId() == R.id.btn_manual5_collect){
            CollectThread.getCollectThread().startCollect(manualActivityController);
            manualActivityController.txt_manual5_view.setTextColor(Color.WHITE);
        }else if(v.getId() == R.id.btn_manual5_stop){
            manualActivityController.txt_manual5_view.setTextColor(Color.GRAY);
            CollectThread.getCollectThread().stopCollect();
        }else if(v.getId() == R.id.btn_manual5_pre){
            ViewController.selectedCollect--;
            if(ViewController.selectedCollect<1)ViewController.selectedCollect = 7;
            changeCollect(manualActivityController,ViewController.selectedCollect);
        }else if(v.getId() == R.id.btn_manual5_next){
            ViewController.selectedCollect++;
            if(ViewController.selectedCollect>7)ViewController.selectedCollect = 1;
            changeCollect(manualActivityController,ViewController.selectedCollect);
        }
    }
}

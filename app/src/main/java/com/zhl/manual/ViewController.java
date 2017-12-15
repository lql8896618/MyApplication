package com.zhl.manual;

import android.app.ActionBar;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhl.R;

/**
 * Created by Administrator on 2017/11/17.
 */
public class ViewController {

    public static int selectedItem = 1;
    public static int selectedPort = 1;
    public static int selectedSound = 1;
    public static int selectedCollect = 1;
    /**
     *
     * @param sel 1到5
     * @param manual
     */
    public static void itemSelect(int sel,ManualActivityController manual){
        Resources resources = manual.manualActivity.getApplicationContext().getResources();
        manual.frame_manual1.setVisibility(View.GONE);
        manual.frame_manual2.setVisibility(View.GONE);
        manual.frame_manual3.setVisibility(View.GONE);
        manual.frame_manual4.setVisibility(View.GONE);
        manual.frame_manual5.setVisibility(View.GONE);
        manual.btn_manual5_pre.setVisibility(View.GONE);
        manual.btn_manual5_next.setVisibility(View.GONE);
        manual.btn_manual_Sel1.setBackground(resources.getDrawable(R.drawable.manual_sel1_off));
        manual.btn_manual_Sel2.setBackground(resources.getDrawable(R.drawable.manual_sel2_off));
        manual.btn_manual_Sel3.setBackground(resources.getDrawable(R.drawable.manual_sel3_off));
        manual.btn_manual_Sel4.setBackground(resources.getDrawable(R.drawable.manual_sel4_off));
        manual.btn_manual_Sel5.setBackground(resources.getDrawable(R.drawable.manual_sel5_off));


        switch (sel){
            case 1:{
                manual.frame_manual1.setVisibility(View.VISIBLE);
                manual.btn_manual_Sel1.setBackground(resources.getDrawable(R.drawable.manual_sel1_on));
                manual.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel1_mainimg));
                selectedItem = 1;
                break;
            }
            case 2:{
                manual.frame_manual2.setVisibility(View.VISIBLE);
                manual.btn_manual_Sel2.setBackground(resources.getDrawable(R.drawable.manual_sel2_on));
                manual.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel2_mainimg));
                selectedItem = 2;
                break;
            }
            case 3:{
                manual.frame_manual3.setVisibility(View.VISIBLE);
                manual.btn_manual_Sel3.setBackground(resources.getDrawable(R.drawable.manual_sel3_on));
                manual.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel3_mainimg_black));
                selectedItem = 3;
                break;
            }
            case 4:{
                manual.frame_manual4.setVisibility(View.VISIBLE);
                manual.btn_manual_Sel4.setBackground(resources.getDrawable(R.drawable.manual_sel4_on));
                manual.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel4_mainimg));
                selectedItem = 4;
                break;
            }
            case 5:{
                manual.frame_manual5.setVisibility(View.VISIBLE);
                manual.btn_manual5_pre.setVisibility(View.VISIBLE);
                manual.btn_manual5_next.setVisibility(View.VISIBLE);
                manual.btn_manual_Sel5.setBackground(resources.getDrawable(R.drawable.manual_sel5_on));
                manual.mainImage.setImageDrawable(resources.getDrawable(R.drawable.manual_sel5_mainimg_1));
                selectedItem = 5;
                break;
            }
        }

        portSelect(ViewController.selectedPort,manual);

    }

    /**
     *
     * @param port 1到8
     * @param manual
     */
    public static void portSelect(int port,ManualActivityController manual){
        Resources resources = manual.manualActivity.getApplicationContext().getResources();
        manual.btn_manual_port1.setVisibility(View.GONE);
        manual.btn_manual_port2.setVisibility(View.GONE);
        manual.btn_manual_port3.setVisibility(View.GONE);
        manual.btn_manual_port4.setVisibility(View.GONE);
        manual.btn_manual_port5.setVisibility(View.GONE);
        manual.btn_manual_port6.setVisibility(View.GONE);
        manual.btn_manual_port7.setVisibility(View.GONE);
        manual.btn_manual_port8.setVisibility(View.GONE);
        manual.btn_manual_port1.setBackground(resources.getDrawable(R.drawable.manual_port1_off));
        manual.btn_manual_port2.setBackground(resources.getDrawable(R.drawable.manual_port2_off));
        manual.btn_manual_port3.setBackground(resources.getDrawable(R.drawable.manual_port3_off));
        manual.btn_manual_port4.setBackground(resources.getDrawable(R.drawable.manual_port4_off));
        manual.btn_manual_port5.setBackground(resources.getDrawable(R.drawable.manual_port5_off));
        manual.btn_manual_port6.setBackground(resources.getDrawable(R.drawable.manual_port6_off));
        manual.btn_manual_port7.setBackground(resources.getDrawable(R.drawable.manual_port7_off));
        manual.btn_manual_port8.setBackground(resources.getDrawable(R.drawable.manual_port8_off));

        selectedPort = port;
        switch (selectedItem){
            case 1:{
                manual.btn_manual_port1.setVisibility(View.VISIBLE);
                manual.btn_manual_port2.setVisibility(View.VISIBLE);
                manual.btn_manual_port3.setVisibility(View.VISIBLE);
                manual.btn_manual_port4.setVisibility(View.VISIBLE);
                switch (port){
                    case 1:manual.btn_manual_port1.setBackground(resources.getDrawable(R.drawable.manual_port1_on));break;
                    case 2:manual.btn_manual_port2.setBackground(resources.getDrawable(R.drawable.manual_port2_on));break;
                    case 3:manual.btn_manual_port3.setBackground(resources.getDrawable(R.drawable.manual_port3_on));break;
                    case 4:manual.btn_manual_port4.setBackground(resources.getDrawable(R.drawable.manual_port4_on));break;
                }
                break;
            }
            case 2:{
                break;
            }
            case 3:{
                manual.btn_manual_port5.setVisibility(View.VISIBLE);
                manual.btn_manual_port6.setVisibility(View.VISIBLE);
                manual.btn_manual_port7.setVisibility(View.VISIBLE);
                manual.btn_manual_port8.setVisibility(View.VISIBLE);
                switch (port){
                    case 5:manual.btn_manual_port5.setBackground(resources.getDrawable(R.drawable.manual_port5_on));break;
                    case 6:manual.btn_manual_port6.setBackground(resources.getDrawable(R.drawable.manual_port6_on));break;
                    case 7:manual.btn_manual_port7.setBackground(resources.getDrawable(R.drawable.manual_port7_on));break;
                    case 8:manual.btn_manual_port8.setBackground(resources.getDrawable(R.drawable.manual_port8_on));break;
                }
                break;
            }
            case 4:{
                break;
            }
            case 5:{
                manual.btn_manual_port1.setVisibility(View.VISIBLE);
                manual.btn_manual_port2.setVisibility(View.VISIBLE);
                manual.btn_manual_port3.setVisibility(View.VISIBLE);
                manual.btn_manual_port4.setVisibility(View.VISIBLE);
                manual.btn_manual_port5.setVisibility(View.VISIBLE);
                manual.btn_manual_port6.setVisibility(View.VISIBLE);
                manual.btn_manual_port7.setVisibility(View.VISIBLE);
                manual.btn_manual_port8.setVisibility(View.VISIBLE);
                switch (port){
                    case 1:manual.btn_manual_port1.setBackground(resources.getDrawable(R.drawable.manual_port1_on));break;
                    case 2:manual.btn_manual_port2.setBackground(resources.getDrawable(R.drawable.manual_port2_on));break;
                    case 3:manual.btn_manual_port3.setBackground(resources.getDrawable(R.drawable.manual_port3_on));break;
                    case 4:manual.btn_manual_port4.setBackground(resources.getDrawable(R.drawable.manual_port4_on));break;
                    case 5:manual.btn_manual_port5.setBackground(resources.getDrawable(R.drawable.manual_port5_on));break;
                    case 6:manual.btn_manual_port6.setBackground(resources.getDrawable(R.drawable.manual_port6_on));break;
                    case 7:manual.btn_manual_port7.setBackground(resources.getDrawable(R.drawable.manual_port7_on));break;
                    case 8:manual.btn_manual_port8.setBackground(resources.getDrawable(R.drawable.manual_port8_on));break;
                }
                break;
            }
        }

    }
}

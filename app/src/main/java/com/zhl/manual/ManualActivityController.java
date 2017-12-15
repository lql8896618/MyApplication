package com.zhl.manual;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.RecoverySystem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.control.CommManage;
import com.zhl.control.ControlMessage;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/21.
 */
public class ManualActivityController extends Handler implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,View.OnTouchListener {
    ManualActivity manualActivity;

    RelativeLayout mainLayout;
    Button btn_manual_Sel1;
    Button btn_manual_Sel2;
    Button btn_manual_Sel3;
    Button btn_manual_Sel4;
    Button btn_manual_Sel5;
    FrameLayout frame_manual1;
    FrameLayout frame_manual2;
    FrameLayout frame_manual3;
    FrameLayout frame_manual4;
    FrameLayout frame_manual5;

    Button btn_manual_port1;
    Button btn_manual_port2;
    Button btn_manual_port3;
    Button btn_manual_port4;
    Button btn_manual_port5;
    Button btn_manual_port6;
    Button btn_manual_port7;
    Button btn_manual_port8;

    ImageView mainImage;

    Button btn_manual_back;

    //以下为分别页面的
    Button btn_manual_zzTime;
    Button btn_manual_fzTime;
    Button btn_manual_zzJiao;
    Button btn_manual_fzJiao;
    Button btn_manual_stop;
    Button btn_manual_break;

    SeekBar seek_manual_speed;
    TextView txt_manual_speedValue;

//2------------------------------------------
    Button btn_manual2_qxzz;
    Button btn_manual2_qxyz;
    Button btn_manual2_qx;
    Button btn_manual2_htzz;
    Button btn_manual2_htyz;
    Button btn_manual2_ht;
    Button btn_manual2_zz;
    Button btn_manual2_yz;

    SeekBar seek_manual2_speed;
    TextView txt_manual2_speedValue;


    //3-------------------------------------------
    Button btn_manual3_red;
    Button btn_manual3_green;
    Button btn_manual3_blue;
    Button btn_manual3_yellow;
    Button btn_manual3_cyan;
    Button btn_manual3_purple;
    Button btn_manual3_white;
    Button btn_manual3_turnoff;

    //4----------------------------------------------------------
    ImageView img_manual4_soundimg;
    Button btn_manual4_pre;
    Button btn_manual4_next;
    Button btn_manual4_m1;
    Button btn_manual4_m2;
    Button btn_manual4_m3;

    //5
    TextView txt_manual5_view;
    TextView txt_manual5_unit;

    Button btn_manual5_pre;
    Button btn_manual5_next;
    Button btn_manual5_collect;
    Button btn_manual5_stop;

    public ManualActivityController(ManualActivity manualActivity){
        this.manualActivity = manualActivity;

        mainLayout = (RelativeLayout)manualActivity.findViewById(R.id.frame_manual_main);
        btn_manual_Sel1 = (Button)manualActivity.findViewById(R.id.btn_manual_Sel1);
        btn_manual_Sel2 = (Button)manualActivity.findViewById(R.id.btn_manual_Sel2);
        btn_manual_Sel3 = (Button)manualActivity.findViewById(R.id.btn_manual_Sel3);
        btn_manual_Sel4 = (Button)manualActivity.findViewById(R.id.btn_manual_Sel4);
        btn_manual_Sel5 = (Button)manualActivity.findViewById(R.id.btn_manual_Sel5);
        frame_manual1 = (FrameLayout)manualActivity.findViewById(R.id.frame_manual1);
        frame_manual2 = (FrameLayout)manualActivity.findViewById(R.id.frame_manual2);
        frame_manual3 = (FrameLayout)manualActivity.findViewById(R.id.frame_manual3);
        frame_manual4 = (FrameLayout)manualActivity.findViewById(R.id.frame_manual4);
        frame_manual5 = (FrameLayout)manualActivity.findViewById(R.id.frame_manual5);
        btn_manual_Sel1.setOnClickListener(this);
        btn_manual_Sel2.setOnClickListener(this);
        btn_manual_Sel3.setOnClickListener(this);
        btn_manual_Sel4.setOnClickListener(this);
        btn_manual_Sel5.setOnClickListener(this);


        btn_manual_port1 = (Button)manualActivity.findViewById(R.id.btn_manual_port1);
        btn_manual_port2 = (Button)manualActivity.findViewById(R.id.btn_manual_port2);
        btn_manual_port3 = (Button)manualActivity.findViewById(R.id.btn_manual_port3);
        btn_manual_port4 = (Button)manualActivity.findViewById(R.id.btn_manual_port4);
        btn_manual_port5 = (Button)manualActivity.findViewById(R.id.btn_manual_port5);
        btn_manual_port6 = (Button)manualActivity.findViewById(R.id.btn_manual_port6);
        btn_manual_port7 = (Button)manualActivity.findViewById(R.id.btn_manual_port7);
        btn_manual_port8 = (Button)manualActivity.findViewById(R.id.btn_manual_port8);
        btn_manual_port1.setOnClickListener(this);
        btn_manual_port2.setOnClickListener(this);
        btn_manual_port3.setOnClickListener(this);
        btn_manual_port4.setOnClickListener(this);
        btn_manual_port5.setOnClickListener(this);
        btn_manual_port6.setOnClickListener(this);
        btn_manual_port7.setOnClickListener(this);
        btn_manual_port8.setOnClickListener(this);

        btn_manual_back  = (Button)manualActivity.findViewById(R.id.btn_manual_back);
        btn_manual_back.setOnClickListener(this);

        mainImage = (ImageView)manualActivity.findViewById(R.id.img_manual_mainImg);

        //1----------------------------
        btn_manual_zzTime = (Button)manualActivity.findViewById(R.id.btn_manual_zzTime);
        btn_manual_fzTime = (Button)manualActivity.findViewById(R.id.btn_manual_fzTime);
        btn_manual_zzJiao = (Button)manualActivity.findViewById(R.id.btn_manual_zzJiao);
        btn_manual_fzJiao = (Button)manualActivity.findViewById(R.id.btn_manual_fzJiao);
        btn_manual_stop = (Button)manualActivity.findViewById(R.id.btn_manual_stop);
        btn_manual_break = (Button)manualActivity.findViewById(R.id.btn_manual_break);
        btn_manual_zzTime.setOnClickListener(this);
        btn_manual_fzTime.setOnClickListener(this);
        btn_manual_zzJiao.setOnClickListener(this);
        btn_manual_fzJiao.setOnClickListener(this);
        btn_manual_stop.setOnClickListener(this);
        btn_manual_break.setOnClickListener(this);

        txt_manual_speedValue = (TextView)manualActivity.findViewById(R.id.txt_manual_speedValue);

        seek_manual_speed = (SeekBar)manualActivity.findViewById(R.id.seek_manual_speed);
        seek_manual_speed.setOnSeekBarChangeListener(this);

        //2---------------------------------------
        btn_manual2_qxzz = (Button)manualActivity.findViewById(R.id.btn_manual2_qxzz);
        btn_manual2_qxyz = (Button)manualActivity.findViewById(R.id.btn_manual2_qxyz);
        btn_manual2_qx = (Button)manualActivity.findViewById(R.id.btn_manual2_qx);
        btn_manual2_zz = (Button)manualActivity.findViewById(R.id.btn_manual2_zz);
        btn_manual2_yz = (Button)manualActivity.findViewById(R.id.btn_manual2_yz);
        btn_manual2_ht = (Button)manualActivity.findViewById(R.id.btn_manual2_ht);
        btn_manual2_htzz = (Button)manualActivity.findViewById(R.id.btn_manual2_htzz);
        btn_manual2_htyz = (Button)manualActivity.findViewById(R.id.btn_manual2_htyz);

        btn_manual2_qxzz.setOnTouchListener(this);
        btn_manual2_qxyz.setOnTouchListener(this);
        btn_manual2_qx.setOnTouchListener(this);
        btn_manual2_zz.setOnTouchListener(this);
        btn_manual2_yz.setOnTouchListener(this);
        btn_manual2_ht.setOnTouchListener(this);
        btn_manual2_htzz.setOnTouchListener(this);
        btn_manual2_htyz.setOnTouchListener(this);
        txt_manual2_speedValue = (TextView)manualActivity.findViewById(R.id.txt_manual2_speedValue);

        seek_manual2_speed = (SeekBar)manualActivity.findViewById(R.id.seek_manual2_speed);
        seek_manual2_speed.setOnSeekBarChangeListener(this);

        //3---------------------------
        btn_manual3_red = (Button)manualActivity.findViewById(R.id.btn_manual3_red);
        btn_manual3_green = (Button)manualActivity.findViewById(R.id.btn_manual3_green);
        btn_manual3_blue = (Button)manualActivity.findViewById(R.id.btn_manual3_blue);
        btn_manual3_yellow = (Button)manualActivity.findViewById(R.id.btn_manual3_yellow);
        btn_manual3_cyan = (Button)manualActivity.findViewById(R.id.btn_manual3_cyan);
        btn_manual3_purple = (Button)manualActivity.findViewById(R.id.btn_manual3_purple);
        btn_manual3_white = (Button)manualActivity.findViewById(R.id.btn_manual3_white);
        btn_manual3_turnoff = (Button)manualActivity.findViewById(R.id.btn_manual3_turnoff);
        btn_manual3_red.setOnClickListener(this);
        btn_manual3_green.setOnClickListener(this);
        btn_manual3_blue.setOnClickListener(this);
        btn_manual3_yellow.setOnClickListener(this);
        btn_manual3_cyan.setOnClickListener(this);
        btn_manual3_purple.setOnClickListener(this);
        btn_manual3_white.setOnClickListener(this);
        btn_manual3_turnoff.setOnClickListener(this);

        //4--------------------------
        btn_manual4_pre = (Button)manualActivity.findViewById(R.id.btn_manual4_pre);
        btn_manual4_next = (Button)manualActivity.findViewById(R.id.btn_manual4_next);
        btn_manual4_m1 = (Button)manualActivity.findViewById(R.id.btn_manual4_m1);
        btn_manual4_m2 = (Button)manualActivity.findViewById(R.id.btn_manual4_m2);
        btn_manual4_m3 = (Button)manualActivity.findViewById(R.id.btn_manual4_m3);
        img_manual4_soundimg = (ImageView)manualActivity.findViewById(R.id.img_manual4_soundimg);
        btn_manual4_pre.setOnClickListener(this);
        btn_manual4_next.setOnClickListener(this);
        btn_manual4_m1.setOnClickListener(this);
        btn_manual4_m2.setOnClickListener(this);
        btn_manual4_m3.setOnClickListener(this);
        img_manual4_soundimg.setOnClickListener(this);

        //5------------------------------------
        txt_manual5_view = (TextView)manualActivity.findViewById(R.id.txt_manual5_view);
        txt_manual5_unit = (TextView)manualActivity.findViewById(R.id.txt_manual5_unit);

        btn_manual5_collect = (Button)manualActivity.findViewById(R.id.btn_manual5_collect);
        btn_manual5_stop = (Button)manualActivity.findViewById(R.id.btn_manual5_stop);
        btn_manual5_pre = (Button)manualActivity.findViewById(R.id.btn_manual5_pre);
        btn_manual5_next = (Button)manualActivity.findViewById(R.id.btn_manual5_next);
        btn_manual5_collect.setOnClickListener(this);
        btn_manual5_stop.setOnClickListener(this);
        btn_manual5_pre.setOnClickListener(this);
        btn_manual5_next.setOnClickListener(this);

        ViewController.itemSelect(ViewController.selectedItem, this);
        ViewController.portSelect(ViewController.selectedPort, this);
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == ControlMessage.COLLECT_CON_DATA){
            int value = msg.arg1;
            if(value==0xfff1){
                txt_manual5_view.setText("未连接");
            }else if(value==0xfff2){
                txt_manual5_view.setText("错误");
            }else {
                if (ViewController.selectedCollect==1) {
                    txt_manual5_view.setText(new DecimalFormat("#.0").format(value / 10.0)+"");
                    txt_manual5_unit.setText("CM");
                }else if (ViewController.selectedCollect==2) {
                    txt_manual5_view.setText(value==1?"按下":"松开");
                    txt_manual5_unit.setText("");
                }else if (ViewController.selectedCollect==3) {
                    txt_manual5_view.setText(value+"");
                    txt_manual5_unit.setText("%");
                }else if (ViewController.selectedCollect==4) {
                    txt_manual5_view.setText(value);
                }else if (ViewController.selectedCollect==5) {
                    txt_manual5_view.setText(value+"");
                    txt_manual5_unit.setText("%");
                }else if (ViewController.selectedCollect==6) {
                    txt_manual5_view.setText(value+"");
                    txt_manual5_unit.setText("C");
                }else if (ViewController.selectedCollect==7) {
                    txt_manual5_view.setText(value+"");
                    txt_manual5_unit.setText("MA");
                }
            }
        }else if(msg.what == ControlMessage.COLLECT_CON_STOP){
            txt_manual5_view.setTextColor(Color.GRAY);
            txt_manual5_view.setText("停止");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_manual_Sel1){
            ViewController.itemSelect(1, this);
        }else if(v.getId()==R.id.btn_manual_Sel2){
            ViewController.itemSelect(2, this);
        }else if(v.getId()==R.id.btn_manual_Sel3){
            ViewController.itemSelect(3, this);
        }else if(v.getId()==R.id.btn_manual_Sel4){
            ViewController.itemSelect(4, this);
        }else if(v.getId()==R.id.btn_manual_Sel5){//声音
            ViewController.itemSelect(5, this);
        }else if(v.getId()==R.id.btn_manual_port1){
            ViewController.portSelect(1, this);
        }else if(v.getId()==R.id.btn_manual_port2){
            ViewController.portSelect(2, this);
        }else if(v.getId()==R.id.btn_manual_port3){
            ViewController.portSelect(3, this);
        }else if(v.getId()==R.id.btn_manual_port4){
            ViewController.portSelect(4, this);
        }else if(v.getId()==R.id.btn_manual_port5){
            ViewController.portSelect(5, this);
        }else if(v.getId()==R.id.btn_manual_port6){
            ViewController.portSelect(6, this);
        }else if(v.getId()==R.id.btn_manual_port7){
            ViewController.portSelect(7, this);
        }else if(v.getId()==R.id.btn_manual_port8){
            ViewController.portSelect(8, this);
        }else if(v.getId()==R.id.btn_manual_back){
            this.manualActivity.finish();
        }else{
            if(ViewController.selectedItem==1){
                Operator.manual1(this, v);
            }else if(ViewController.selectedItem==2){

            }else if(ViewController.selectedItem==3){
                Operator.manual3(this,v);
            }else if(ViewController.selectedItem==4){
                Operator.manual4(this, v);
            }else if(ViewController.selectedItem==5){
                Operator.manual5(this,v);
            }

        }




    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId()==R.id.seek_manual_speed) {
            if (progress < 30) progress = 30;
            seekBar.setProgress(progress);
            txt_manual_speedValue.setText(progress + "");
        }else if(seekBar.getId()==R.id.seek_manual2_speed) {
            if (progress < 30) progress = 30;
            seekBar.setProgress(progress);
            txt_manual2_speedValue.setText(progress + "");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            Operator.manual2(this, v,2);
        }else if(event.getAction() ==MotionEvent.ACTION_DOWN){
            Operator.manual2(this, v,1);
        }
        return false;
    }
}

package com.zhl.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.zhl.R;
import com.zhl.begin.BeginActivity;
import com.zhl.common.SoundUtil;
import com.zhl.production.dialog.ProductionListDialog;
import com.zhl.setup.SetupActivity;

/**
 * Created by Administrator on 2017/9/16.
 */
public class MainActivityController extends Handler implements View.OnClickListener {
    private MainActivity mainActivity;



    private Intent beginIntent;
    private Intent setupIntent;

    private Button btn_main_begin;
    private Button btn_main_setup;
    private Button btn_main_user;
    private Button btn_main_production;
    private Button btn_main_ranking;
    private Button btn_main_myproduction;
    private Button btn_main_help;



    public MainActivityController(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        beginIntent = new Intent();
        beginIntent.setClass(mainActivity, BeginActivity.class);

        setupIntent = new Intent();
        setupIntent.setClass(mainActivity, SetupActivity.class);

        this.btn_main_begin =(Button) mainActivity.findViewById(R.id.btn_main_begin);
        btn_main_begin.setOnClickListener(this);

        this.btn_main_setup = (Button)mainActivity.findViewById(R.id.btn_main_setup);
        this.btn_main_setup.setOnClickListener(this);

        this.btn_main_user = (Button)mainActivity.findViewById(R.id.btn_main_user);
        this.btn_main_user.setOnClickListener(this);

        this.btn_main_production = (Button)mainActivity.findViewById(R.id.btn_main_production);
        this.btn_main_production.setOnClickListener(this);

        this.btn_main_ranking = (Button)mainActivity.findViewById(R.id.btn_main_ranking);
        this.btn_main_ranking.setOnClickListener(this);

        this.btn_main_myproduction = (Button)mainActivity.findViewById(R.id.btn_main_myproduction);
        this.btn_main_myproduction.setOnClickListener(this);

        this.btn_main_help = (Button)mainActivity.findViewById(R.id.btn_main_help);
        this.btn_main_help.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        SoundUtil.playButtonSound();
        if(v.getId()==R.id.btn_main_begin){//开始
            mainActivity.startActivity(beginIntent);
            SoundUtil.setBgMusicVolume(0);
//            mainActivity.finish();

        }else if(v.getId()==R.id.btn_main_setup){
            mainActivity.startActivity(setupIntent);
//            mainActivity.finish();
        }else if(v.getId()==R.id.btn_main_user){
            new AlertDialog.Builder(mainActivity)
                    .setTitle("测试").setMessage("个人信息").setPositiveButton("确定", null).show();
        }else if(v.getId()==R.id.btn_main_production){
            ProductionListDialog dlg = new ProductionListDialog(mainActivity);
            dlg.show();
        }else if(v.getId()==R.id.btn_main_ranking){
            new AlertDialog.Builder(mainActivity)
                    .setTitle("测试").setMessage("我的排名").setPositiveButton("确定", null).show();
        }else if(v.getId()==R.id.btn_main_myproduction){
            new AlertDialog.Builder(mainActivity)
                    .setTitle("测试").setMessage("我的作品").setPositiveButton("确定", null).show();
        }else if(v.getId()==R.id.btn_main_help){
            new AlertDialog.Builder(mainActivity)
                    .setTitle("测试").setMessage("帮助说明").setPositiveButton("确定", null).show();
        }
        System.gc();
    }
}

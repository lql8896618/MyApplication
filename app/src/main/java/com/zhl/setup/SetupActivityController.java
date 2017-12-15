package com.zhl.setup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.common.Config;
import com.zhl.control.ControlGlobal;
import com.zhl.control.ControlMessage;
import com.zhl.manual.ManualActivity;
import com.zhl.manual.Operator;

import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/9/21.
 */
public class SetupActivityController extends Handler implements View.OnClickListener{
    private SetupActivity setupActivity;

    Button btn_setup_controlSave;

    TextView txt_setup_controlID;


    public SetupActivityController(SetupActivity setupActivity){
        this.setupActivity = setupActivity;
        btn_setup_controlSave = (Button)setupActivity.findViewById(R.id.btn_setup_controlSave);
        txt_setup_controlID = (TextView)setupActivity.findViewById(R.id.txt_setup_controlID);
        btn_setup_controlSave.setOnClickListener(this);
        if(Config.controlName.equals(""))
            txt_setup_controlID.setText("未设置");
        else txt_setup_controlID.setText(Config.controlName.substring(3));
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_setup_controlSave){
            String controlID = txt_setup_controlID.getText().toString().trim();
            if(controlID.length()!=7){
                new AlertDialog.Builder(setupActivity)
                        .setTitle("提示").setMessage("请使用正确的编号").setPositiveButton("确定", null).show();
            }else {
                Config.controlName = "ZHL" + controlID;
                if(ControlGlobal.blueteeth!=null){
                    ControlGlobal.blueteeth.breakConnection();
                    ControlGlobal.blueteeth.setBlueteethName(Config.controlName);
                }
                SharedPreferences sp = setupActivity.getSharedPreferences(setupActivity.getString(R.string.sp_config_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(setupActivity.getString(R.string.sp_control_name), Config.controlName);
                editor.commit();
                new AlertDialog.Builder(setupActivity)
                        .setTitle("提示").setMessage("保存成功").setPositiveButton("确定", null).show();
            }
        }

    }

}

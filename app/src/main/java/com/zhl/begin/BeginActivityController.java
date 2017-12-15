package com.zhl.begin;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.common.Cache;
import com.zhl.common.SoundUtil;
import com.zhl.control.CommManage;
import com.zhl.control.ControlGlobal;
import com.zhl.control.ControlMessage;
import com.zhl.control.MessageUtil;
import com.zhl.design.DesignActivity;
import com.zhl.manual.ManualActivity;

/**
 * Created by Administrator on 2017/9/9.
 */
public class BeginActivityController extends Handler implements View.OnClickListener, Animation.AnimationListener,
        View.OnTouchListener {

    private BeginActivity beginActivity;

    private Intent maualIntent;
    private Intent programIntent;

    private FrameLayout layout_begin_status1;
    private FrameLayout layout_begin_status2;
    private FrameLayout layout_begin_status3;
    private FrameLayout layout_begin_status4;
    private FrameLayout layout_begin_update;

    private TextView txt_begin_conStatus;
    private TextView txt_begin_updateInfo;
    private TextView txt_begin_controlID;
    private TextView txt_begin_controlVersion;
    private TextView txt_begin_eg;
    private ProgressBar pro_begin_update;
    private Button btn_begin_update;
    private Button btn_begin_createConn;
    private Button btn_begin_breakConn;
    private Button btn_begin_stopConn;
    private Button btn_begin_update_finish;
    private Button btn_begin_manual;
    private Button btn_begin_program;
    private Button btn_begin_run;
    private Button btn_begin_exit;

    private ImageView bg_begin_eg;

    private ViewGroup.LayoutParams param1;
    private ViewGroup.LayoutParams param2;
    private ViewGroup.LayoutParams param3;
    private boolean buttonMoving = false;


    public BeginActivityController(BeginActivity beginActivity) {
        this.beginActivity = beginActivity;

        maualIntent = new Intent();
        maualIntent.setClass(beginActivity, ManualActivity.class);
        programIntent = new Intent();
        programIntent.setClass(beginActivity, DesignActivity.class);

        this.layout_begin_status1 = (FrameLayout) beginActivity.findViewById(R.id.layout_begin_status1);
        this.layout_begin_status2 = (FrameLayout) beginActivity.findViewById(R.id.layout_begin_status2);
        this.layout_begin_status3 = (FrameLayout) beginActivity.findViewById(R.id.layout_begin_status3);
        this.layout_begin_status4 = (FrameLayout) beginActivity.findViewById(R.id.layout_begin_status4);
        this.layout_begin_update = (FrameLayout) beginActivity.findViewById(R.id.layout_begin_update);

        this.txt_begin_updateInfo = (TextView) beginActivity.findViewById(R.id.txt_begin_updateInfo);
        this.txt_begin_conStatus = (TextView) beginActivity.findViewById(R.id.txt_begin_conStatus);
        this.txt_begin_controlID = (TextView) beginActivity.findViewById(R.id.txt_begin_controlID);
        this.txt_begin_controlVersion = (TextView) beginActivity.findViewById(R.id.txt_begin_controlVersion);
        this.txt_begin_eg = (TextView) beginActivity.findViewById(R.id.txt_begin_eg);

        bg_begin_eg = (ImageView) beginActivity.findViewById(R.id.bg_begin_eg);

        this.btn_begin_update = (Button) beginActivity.findViewById(R.id.btn_begin_update);
        this.btn_begin_createConn = (Button) beginActivity.findViewById(R.id.btn_begin_createConn);
        this.btn_begin_breakConn = (Button) beginActivity.findViewById(R.id.btn_begin_breakConn);
        this.btn_begin_stopConn = (Button) beginActivity.findViewById(R.id.btn_begin_stopConn);
        this.pro_begin_update = (ProgressBar) beginActivity.findViewById(R.id.pro_begin_update);
        this.btn_begin_update_finish = (Button) beginActivity.findViewById(R.id.btn_begin_update_finish);
        this.btn_begin_manual = (Button) beginActivity.findViewById(R.id.btn_begin_manual);
        this.btn_begin_program = (Button) beginActivity.findViewById(R.id.btn_begin_program);
        this.btn_begin_run = (Button) beginActivity.findViewById(R.id.btn_begin_run);
        btn_begin_exit = (Button) beginActivity.findViewById(R.id.btn_begin_exit);

        param1 = btn_begin_run.getLayoutParams();
        param2 = btn_begin_program.getLayoutParams();
        param3 = btn_begin_manual.getLayoutParams();

        controlButtonPos(btn_begin_run);
        controlButtonPos(btn_begin_program);
        controlButtonPos(btn_begin_manual);

        btn_begin_update.setOnClickListener(this);
        btn_begin_createConn.setOnClickListener(this);
        btn_begin_breakConn.setOnClickListener(this);
        btn_begin_stopConn.setOnClickListener(this);
        btn_begin_update_finish.setOnClickListener(this);
        btn_begin_manual.setOnClickListener(this);
        btn_begin_program.setOnClickListener(this);
        btn_begin_run.setOnClickListener(this);
        btn_begin_exit.setOnClickListener(this);
        this.beginActivity.findViewById(R.id.bg_begin_global).setOnTouchListener(this);

        txt_begin_controlVersion.setOnClickListener(this);

    }

    public void init() {
        if (ControlGlobal.blueteeth == null ||
                ControlGlobal.blueteeth.getBlueteethName() == null ||
                "".equals(ControlGlobal.blueteeth.getBlueteethName())) {
            changeControlStatus(1);
        } else {
            changeControlStatus(2);
            txt_begin_controlID.setText(ControlGlobal.blueteeth.getBlueteethName());
        }

    }

    private void controlButtonPos(Button btn) {
        int sort = Cache.beginBtnSort.get(btn.getId());
        if (sort == 1) {
            btn.setLayoutParams(param1);
        } else if (sort == 2) {
            btn.setLayoutParams(param2);
        } else if (sort == 3) {
            btn.setLayoutParams(param3);
            if (btn.getId() == R.id.btn_begin_manual) {
                txt_begin_eg.setText("直接控制电机、小灯\n、及各种传感器");
            } else if (btn.getId() == R.id.btn_begin_program) {
                txt_begin_eg.setText("进入编程模式，设计一个\n自主运行的机器人");
            }
            if (btn.getId() == R.id.btn_begin_run) {
                txt_begin_eg.setText("遥控已经编好程序的\n机器人");
            }
            bg_begin_eg.setVisibility(View.VISIBLE);
        }


    }

    private void moveButton(int btnIndex) {

        if (Cache.beginBtnSort.get(R.id.btn_begin_run) == btnIndex) {
            moveButton(btn_begin_run);
        }else if (Cache.beginBtnSort.get(R.id.btn_begin_manual) == btnIndex) {
            moveButton(btn_begin_manual);
        }else if (Cache.beginBtnSort.get(R.id.btn_begin_program) == btnIndex) {
            moveButton(btn_begin_program);
        }

    }

    private void moveButton(Button btn) {
        if (buttonMoving) return;
        int sort = Cache.beginBtnSort.get(btn.getId());

        if (sort == 3) {
            if (btn.getId() == R.id.btn_begin_manual) {
                beginActivity.startActivity(maualIntent);
            } else if (btn.getId() == R.id.btn_begin_run) {
                //beginActivity.startActivity(maualIntent);
            } else if (btn.getId() == R.id.btn_begin_program) {
                beginActivity.startActivity(new Intent(beginActivity, DesignActivity.class));
            }
        } else if (sort == 1) {
            buttonMoving = true;
            txt_begin_eg.setText("");
            bg_begin_eg.setVisibility(View.GONE);
            if (btn.getId() == R.id.btn_begin_manual) {
                this.btn_begin_run.startAnimation(beginActivity.rightToLeft);
                this.btn_begin_program.startAnimation(beginActivity.bottomToRightSet);
                this.btn_begin_manual.startAnimation(beginActivity.leftToBottomSet);

                Cache.beginBtnSort.put(R.id.btn_begin_program, 2);
                Cache.beginBtnSort.put(R.id.btn_begin_manual, 3);
                Cache.beginBtnSort.put(R.id.btn_begin_run, 1);
                beginActivity.leftToBottomSet.setAnimationListener(this);
            } else if (btn.getId() == R.id.btn_begin_run) {
                this.btn_begin_run.startAnimation(beginActivity.leftToBottomSet);
                this.btn_begin_program.startAnimation(beginActivity.rightToLeft);
                this.btn_begin_manual.startAnimation(beginActivity.bottomToRightSet);

                Cache.beginBtnSort.put(R.id.btn_begin_program, 1);
                Cache.beginBtnSort.put(R.id.btn_begin_manual, 2);
                Cache.beginBtnSort.put(R.id.btn_begin_run, 3);
                beginActivity.leftToBottomSet.setAnimationListener(this);
            } else if (btn.getId() == R.id.btn_begin_program) {
                this.btn_begin_run.startAnimation(beginActivity.bottomToRightSet);
                this.btn_begin_program.startAnimation(beginActivity.leftToBottomSet);
                this.btn_begin_manual.startAnimation(beginActivity.rightToLeft);

                Cache.beginBtnSort.put(R.id.btn_begin_program, 3);
                Cache.beginBtnSort.put(R.id.btn_begin_manual, 1);
                Cache.beginBtnSort.put(R.id.btn_begin_run, 2);
                beginActivity.leftToBottomSet.setAnimationListener(this);
            }
        } else if (sort == 2) {
            buttonMoving = true;
            txt_begin_eg.setText("");
            bg_begin_eg.setVisibility(View.GONE);
            if (btn.getId() == R.id.btn_begin_manual) {
                this.btn_begin_run.startAnimation(beginActivity.bottomToLeftSet);
                this.btn_begin_program.startAnimation(beginActivity.leftToRight);
                this.btn_begin_manual.startAnimation(beginActivity.rightToBottomSet);

                Cache.beginBtnSort.put(R.id.btn_begin_program, 2);
                Cache.beginBtnSort.put(R.id.btn_begin_manual, 3);
                Cache.beginBtnSort.put(R.id.btn_begin_run, 1);
                beginActivity.rightToBottomSet.setAnimationListener(this);
            } else if (btn.getId() == R.id.btn_begin_run) {
                this.btn_begin_run.startAnimation(beginActivity.rightToBottomSet);
                this.btn_begin_program.startAnimation(beginActivity.bottomToLeftSet);
                this.btn_begin_manual.startAnimation(beginActivity.leftToRight);

                Cache.beginBtnSort.put(R.id.btn_begin_program, 1);
                Cache.beginBtnSort.put(R.id.btn_begin_manual, 2);
                Cache.beginBtnSort.put(R.id.btn_begin_run, 3);
                beginActivity.rightToBottomSet.setAnimationListener(this);
            } else if (btn.getId() == R.id.btn_begin_program) {
                this.btn_begin_run.startAnimation(beginActivity.leftToRight);
                this.btn_begin_program.startAnimation(beginActivity.rightToBottomSet);
                this.btn_begin_manual.startAnimation(beginActivity.bottomToLeftSet);

                Cache.beginBtnSort.put(R.id.btn_begin_program, 3);
                Cache.beginBtnSort.put(R.id.btn_begin_manual, 1);
                Cache.beginBtnSort.put(R.id.btn_begin_run, 2);
                beginActivity.rightToBottomSet.setAnimationListener(this);
            }
        }

    }

    /**
     * 更新控制器的显示状态
     *
     * @param status 0表示未绑定控制 1表示未连接  2表示连接中  3表示已连接
     */
    private void changeControlStatus(int status) {

        if (status == 1) {
            layout_begin_status1.setVisibility(View.VISIBLE);
            layout_begin_status2.setVisibility(View.GONE);
            layout_begin_status3.setVisibility(View.GONE);
            layout_begin_status4.setVisibility(View.GONE);
            layout_begin_update.setVisibility(View.GONE);
        } else if (status == 2) {
            layout_begin_status1.setVisibility(View.GONE);
            layout_begin_status2.setVisibility(View.VISIBLE);
            layout_begin_status3.setVisibility(View.GONE);
            layout_begin_status4.setVisibility(View.GONE);
            layout_begin_update.setVisibility(View.GONE);
        } else if (status == 3) {
            layout_begin_status1.setVisibility(View.GONE);
            layout_begin_status2.setVisibility(View.GONE);
            layout_begin_status3.setVisibility(View.VISIBLE);
            layout_begin_status4.setVisibility(View.GONE);
            layout_begin_update.setVisibility(View.GONE);
        } else if (status == 4) {
            layout_begin_status1.setVisibility(View.GONE);
            layout_begin_status2.setVisibility(View.GONE);
            layout_begin_status3.setVisibility(View.GONE);
            layout_begin_status4.setVisibility(View.VISIBLE);
            layout_begin_update.setVisibility(View.GONE);
        } else if (status == 5) {
            layout_begin_status1.setVisibility(View.GONE);
            layout_begin_status2.setVisibility(View.GONE);
            layout_begin_status3.setVisibility(View.GONE);
            layout_begin_status4.setVisibility(View.VISIBLE);
            layout_begin_update.setVisibility(View.VISIBLE);
            btn_begin_update_finish.setVisibility(View.GONE);
        }

    }


    @Override
    public void handleMessage(Message msg)//接收消息的方法
    {
        switch (msg.what) {
            case ControlMessage.NET_GETINGWEBPRO: {
                txt_begin_updateInfo.setText("正在从服务器获取最新固件程序...");
                break;
            }
            case ControlMessage.NET_GETWEBPRO_ERROR: {
                txt_begin_updateInfo.setText("从服务器获取固件程序失败");
                break;
            }
            case ControlMessage.UPDATE_CONTROL_BLEERROR: {
                txt_begin_updateInfo.setText("向控制器下载程序失败");
                btn_begin_update_finish.setText("取消");
                btn_begin_update_finish.setVisibility(View.VISIBLE);
                break;
            }
            case ControlMessage.UPDATE_CONTROL_CORE_SENDING: {
                txt_begin_updateInfo.setText("正在向控制器下载固件程序...");
                pro_begin_update.setProgress(msg.arg1);
                break;
            }
            case ControlMessage.UPDATE_CONTROL_CORE_FINISH: {
                txt_begin_updateInfo.setText("固件程序更新完成");
                pro_begin_update.setProgress(100);
                btn_begin_update_finish.setText("确定");
                btn_begin_update_finish.setVisibility(View.VISIBLE);
                break;
            }

            case ControlMessage.CON_NOCONNECT: {
                txt_begin_conStatus.setText("尚未连接控制器");
                break;
            }
            case ControlMessage.CON_DISABLE: {
                txt_begin_conStatus.setText("您的手机或平板没有开启蓝牙设备");
                break;
            }
            case ControlMessage.CON_WAITINGENABLE: {
                txt_begin_conStatus.setText("蓝牙正在开启...");
                break;
            }
            case ControlMessage.CON_SCANING: {
                txt_begin_conStatus.setText("正在寻找您的控制器...");
                break;
            }
            case ControlMessage.CON_CONNECTING: {
                txt_begin_conStatus.setText("正在与控制器建立连接...");
                break;
            }
            case ControlMessage.CON_BONDING: {
                txt_begin_conStatus.setText("正在识别控制器，请输入密码‘1234’...");
                break;
            }
            case ControlMessage.CON_BONDING_ERR: {
                txt_begin_conStatus.setText("识别控制器出错");
                break;
            }
            case ControlMessage.CON_BONDING_ERR_MIMA: {
                txt_begin_conStatus.setText("识别控制器出错，请确认控制器编号或密码");
                break;
            }
            case ControlMessage.CON_SCAN_NOTFOUND: {
                txt_begin_conStatus.setText("未找到您的控制器(" + ControlGlobal.blueteeth.getBlueteethName() + ")，请确认控制器是否已开机");
                break;
            }
            case ControlMessage.CON_CONNECT_FAIL: {
                txt_begin_conStatus.setText("连接控制器失败");
                break;
            }
            case ControlMessage.CON_WAITING_RECONNECT: {
                txt_begin_conStatus.setText("连接控制器失败," + msg.arg1 + "秒后将重新连接");
                break;
            }
            case ControlMessage.CON_CONNECTED: {
                txt_begin_conStatus.setText("控制器已连接");
                txt_begin_controlVersion.setText("V201");
                changeControlStatus(4);
                break;
            }
            case ControlMessage.Begin_BTN_MOVEED: {
                buttonMoving = false;
                this.controlButtonPos(btn_begin_manual);
                this.controlButtonPos(btn_begin_run);
                this.controlButtonPos(btn_begin_program);
                btn_begin_manual.setOnClickListener(this);
                btn_begin_run.setOnClickListener(this);
                btn_begin_program.setOnClickListener(this);
                break;
            }
            default: {
                txt_begin_conStatus.setText(msg.what + "   " + msg.arg1);
            }
        }

    }

    @Override
    public void onClick(View v) {
        SoundUtil.playButtonSound();
        if (v.getId() == R.id.txt_begin_controlVersion) {
            CommManage.updateControlCore(this);
            changeControlStatus(5);
        } else if (v.getId() == R.id.btn_begin_createConn) {
            ControlGlobal.blueteeth.setEnable(true);
            changeControlStatus(3);
        } else if (v.getId() == R.id.btn_begin_stopConn || v.getId() == R.id.btn_begin_breakConn) {
            ControlGlobal.blueteeth.breakConnection();
            changeControlStatus(2);
            txt_begin_controlVersion.setText("");
        } else if (v.getId() == R.id.btn_begin_update_finish) {
            changeControlStatus(4);
        } else if (v.getId() == R.id.btn_begin_manual) {
            moveButton((Button) v);
        } else if (v.getId() == R.id.btn_begin_program) {
            moveButton((Button) v);
        } else if (v.getId() == R.id.btn_begin_run) {
            moveButton((Button) v);
        } else if (v.getId() == R.id.btn_begin_exit) {
            this.beginActivity.finish();
        }

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new MessageUtil(this, ControlMessage.Begin_BTN_MOVEED).send();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    float startX = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float endX = event.getX();
            if (endX - startX > 100) {
                this.moveButton(1);
            }
            if (endX - startX < -100) {
                this.moveButton(2);
            }
        }
        return false;
    }
}

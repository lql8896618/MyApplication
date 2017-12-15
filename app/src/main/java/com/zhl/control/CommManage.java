package com.zhl.control;

import android.os.Handler;

import java.util.ResourceBundle;


/**
 * Created by Administrator on 2017/8/28.
 */
public class CommManage {
    public static byte getSum(byte[] buff,int length){
        byte sum = 0;
        for(int i=0;i<length;i++){
            sum += buff[i];
        }
        return sum;
    }


    /**
     * 更新控制器固件程序
     * @param handler
     */
    public static void updateControlCore(Handler handler) {
        new UpdateControlCoreThread(handler).start();
    }

    /**
     * 向控制器下载用户积木程序
     * @param handler
     */
    public static void downloadProgram(Handler handler,ProgramCache pc) {
        new DownloadProgramThread(handler,pc).start();
    }

    /**
     * 控制单电机正向转
     * @param port 端口 0－3  表示端口1到端口4
     * @param speed 速度 30到200
     * @param dir 1正 2反
     * @param method  1角度 2时间  4长久
     * @param value  值 秒 度
     * @return 0表示蓝牙状态未连接，－1表示超时 0表示返回不正确 1表示成功
     */
    public static int monitorStart(int port,int speed,int dir,int method,int value){
        if(ControlGlobal.blueteeth==null||ControlGlobal.blueteeth.getStatus()!=8){
            return 0;
        }
        byte[] cmd = new byte[10];
        cmd[0] = 1;
        cmd[1] = 1;
        cmd[2] = (byte)port;
        cmd[3] = 2;
        cmd[4] = (byte)dir;
        cmd[5] = (byte)speed;
        cmd[6] = (byte)method;
        cmd[7] = (byte)(value>>8);
        cmd[8] = (byte)value;
        cmd[9] = getSum(cmd,9);
        byte receive[] = new byte[8];
        int len = ControlGlobal.blueteeth.request(cmd,receive,8);
        if(receive[0]!=1||receive[1]!=1||receive[2]!=port){
            return 2;
        }
        return len;
    }

    public static int monitorStop(int port,boolean isbreak){
        if(ControlGlobal.blueteeth==null||ControlGlobal.blueteeth.getStatus()!=8){
            return 0;
        }
        byte[] cmd = new byte[10];
        cmd[0] = 1;
        cmd[1] = 1;
        cmd[2] = (byte)port;
        cmd[3] = 2;
        cmd[4] = (byte)(isbreak?4:3);
        cmd[9] = getSum(cmd,9);
        byte receive[] = new byte[8];
        int len = ControlGlobal.blueteeth.request(cmd,receive,8);
        if(receive[0]!=1||receive[1]!=1||receive[2]!=port){
            return 2;
        }
        return len;
    }

    public static int monitorDouble(int speed,int proportion,int dir){
        if(ControlGlobal.blueteeth==null||ControlGlobal.blueteeth.getStatus()!=8){
            return 0;
        }
        byte[] cmd = new byte[10];
        cmd[0] = 1;
        cmd[1] = 1;
        cmd[3] = 3;
        cmd[4] = (byte)speed;
        cmd[5] = (byte)proportion;
        cmd[6] = (byte)dir;
        cmd[9] = getSum(cmd,9);
        byte receive[] = new byte[8];
        int len = ControlGlobal.blueteeth.request(cmd,receive,8);
        if(receive[0]!=1||receive[1]!=1){
            return 2;
        }
        return len;
    }

    /**
     * action 0为关，1为开 2为闪烁
        color 1红；2绿；3蓝；4黄；5青；6紫；7白
        cycle 毫秒
     */
    public static int controlLED(int port,int action,int color,int cycle){
        if(ControlGlobal.blueteeth==null||ControlGlobal.blueteeth.getStatus()!=8){
            return 0;
        }
        byte[] cmd = new byte[10];
        cmd[0] = 1;
        cmd[1] = 1;
        cmd[2] = (byte)port;
        cmd[3] = 4;
        cmd[4] = (byte)action;
        cmd[5] = (byte)color;
        cmd[6] = (byte)(cycle>>8);
        cmd[7] = (byte)cycle;
        cmd[9] = getSum(cmd,9);
        byte receive[] = new byte[8];
        int len = ControlGlobal.blueteeth.request(cmd,receive,8);
        if(receive[0]!=1||receive[1]!=1){
            return 2;
        }
        return len;
    }

    /**
     * 播放声音
     * type 0为音效，1歌曲
     * index 索引
     */
    public static int controlSound(int type,int index){
        if(ControlGlobal.blueteeth==null||ControlGlobal.blueteeth.getStatus()!=8){
            return 0;
        }
        byte[] cmd = new byte[10];
        cmd[0] = 1;
        cmd[1] = 1;
        cmd[3] = 13;//播放
        cmd[4] = (byte)type;
        if(type==0){
            cmd[5] = (byte)(index+1);
        }else {
            cmd[5] = (byte) index;
        }
        cmd[9] = getSum(cmd,9);
        byte receive[] = new byte[8];
        int len = ControlGlobal.blueteeth.request(cmd,receive,8);
        if(receive[0]!=1||receive[1]!=1){
            return 2;
        }
        return len;
    }

    /**
     *
     * @param port
     * @param device
     * @return  0xFFF1表示蓝牙不正常 0xFFF2表示协议不正确 0xFFFF表示数据不正常
     */
    public static int collectData(int port,int device){
        if(ControlGlobal.blueteeth==null||ControlGlobal.blueteeth.getStatus()!=8){
            return 0xFFF1;
        }
//        5：亮度采集
//        6：声音采集
//        7：温度采集
//        8：超声波测距
//        9：按键检测
//        10：灰度检测

        byte code = 0;
        if(device==1){//超声波测距
            code = 8;
        }else if(device==2){//"按键检测"
            code = 9;
        }else if(device==3){//灰度检测
            code = 10;
        }else if(device==4){//亮度采集
            code = 5;
        }else if(device==5){//声音采集
            code = 6;
        }else if(device==6){//温度采集
            code = 7;
        }else if(device==7){//运行电流
            code = 12;
        }
        byte[] cmd = new byte[10];
        cmd[0] = 1;
        cmd[1] = 1;
        cmd[2] = (byte)port;
        cmd[3] = code;
        cmd[9] = getSum(cmd,9);
        byte receive[] = new byte[8];
        int len = ControlGlobal.blueteeth.request(cmd,receive,8);
        if(receive[0]!=1||receive[1]!=1||receive[2]!=port){
            return 0xFFF2;
        }
        int value = (0xff00&(receive[5]<<8)) | 0xff&receive[6];
        return value;
    }
}

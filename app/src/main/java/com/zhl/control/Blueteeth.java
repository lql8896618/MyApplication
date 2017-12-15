package com.zhl.control;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothSocket;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
/**
 * Created by Administrator on 2017/9/6.
 */
public class Blueteeth {

    private String blueteethName;
    private Activity activity;
    private Handler handler;
    private BluetoothAdapter adapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;

    private boolean enable = false;

    public Blueteeth(String blueteethName,Activity activity,Handler handler){
        this.blueteethName = blueteethName;
        this.activity = activity;
        this.handler = handler;
        adapter= BluetoothAdapter.getDefaultAdapter();

        if (Build.VERSION.SDK_INT >= 23) {
            //检测当前app是否拥有某个权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){//判断这个权限是否已经授权过
                //判断是否需要 向用户解释，为什么要申请该权限
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(activity, "需要开启蓝牙才可以与控制器通讯",Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(activity ,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},10);
                return;
            }
        }

        if(adapter == null){
            enable = false;
            return;
        }

        if(!adapter.isEnabled()){
            adapter.enable();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.setPriority(Integer.MAX_VALUE);//设置优先级
        activity.registerReceiver(new BlueTeethReceiver(this), filter);

        new BlueteethControlThread(this).start();

        new BlueteethReader().start();
    }




    private int status;

    private int threadTimeCounter = 0;

    public void setEnable(boolean enable){
        this.enable = enable;
    }
    public boolean isEnable(){
        return this.enable;
    }

    public String getBlueteethName() {
        return blueteethName;
    }

    public void setBlueteethName(String blueteethName) {
        this.blueteethName = blueteethName;
    }

    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void breakConnection(){
        this.enable = false;
        this.status = 0;
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //device.
        this.device = null;
        //this.adapter.disable();
        new MessageUtil(handler, ControlMessage.CON_NOCONNECT).send();
    }

    public void createConnection(){
        switch (this.status){
            case -1:{//连接过程中出现问题，重新连接
                adapter.disable();
                status = 0;
                break;
            }
            case 0:{//表示蓝牙在关闭状态,开启蓝牙
                if(!adapter.isEnabled()){
                    adapter.enable();
                    new MessageUtil(handler, ControlMessage.CON_WAITINGENABLE).send();
                }
                if (adapter.isDiscovering()) {//开启后默认不扫描
                    adapter.cancelDiscovery();
                }
                this.threadTimeCounter = 0;
                this.status = 1;
                break;
            }
            case 1:{//等待开启完成
                if(!adapter.isEnabled()){
                    this.threadTimeCounter++;
                }else{
                    this.status = 2;
                    this.threadTimeCounter = 0;
                    break;
                }
                if(this.threadTimeCounter>50){
                    new MessageUtil(handler, ControlMessage.CON_DISABLE).send();//蓝牙未开启
                    this.status = 0;
                }
                break;
            }
            case 2:{//表示蓝牙刚打开，检测已配对列表中是否有目标控制器
                this.status = 3;
                Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        String devName = device.getName();
                        if(devName.equals(this.blueteethName)){//已找到控制器
                            this.device = device;
                            this.status = 6;//去连接
                            break;
                        }
                    }
                }
                break;
            }
            case 3:{//已配对列表中没有找到目标控制器，开启扫描
                new MessageUtil(handler, ControlMessage.CON_SCANING).send();//准备扫描
                if (adapter.isDiscovering()) {//开启后默认不扫描
                    adapter.cancelDiscovery();
                }
                adapter.startDiscovery();
                this.status = 4;
                break;
            }
            case 4:{//等待扫描并成功配对蓝牙设备
                break;
            }
            case 5:{
                if(device.getBondState()==BluetoothDevice.BOND_BONDED){
                    this.status = 6;
                    this.threadTimeCounter = 0;
                }else{
                    this.threadTimeCounter++;
                }
                if(this.threadTimeCounter>50){
                    new MessageUtil(handler, ControlMessage.CON_BONDING_ERR_MIMA).send();//蓝牙未开启
                    this.status = 7;//重新扫描
                    //this.breakConnection();
                }
            }
            case 6: {//已找到配对好的控制器，建立连接并获取到输出流
                new MessageUtil(handler, ControlMessage.CON_CONNECTING).send();//准备连接
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                try {
                    socket = device.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    status = 8;
                    new MessageUtil(handler, ControlMessage.CON_CONNECTED).send();//蓝牙连接功能
                } catch (IOException e) {
                    new MessageUtil(handler, ControlMessage.CON_CONNECT_FAIL).send();//蓝牙连接失败
                    status = 7;
                }

                break;
            }
            case 7:{
                if(this.threadTimeCounter<100){
                    this.threadTimeCounter++;
                    if(threadTimeCounter>5&&threadTimeCounter%5==0){
                        new MessageUtil(handler, ControlMessage.CON_WAITING_RECONNECT,(100-threadTimeCounter)/5).send();//蓝牙连接失败
                    }
                }else{
                    this.status = 0;
                    this.threadTimeCounter = 0;
                }
                break;

            }
            case 8:{//连接成功
                break;
            }


        }



    }



    public int request(byte[] cmdBuff,byte[] receiveBuff,int receiveLen){
        int len = -1;
        try {
            OutputStream out = socket.getOutputStream();
            out.write(cmdBuff);
            len = BlueteethReader.readBuff(receiveBuff,receiveLen,900);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return len;
    }


}

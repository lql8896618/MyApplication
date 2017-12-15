package com.zhl.control;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/9/9.
 */
public class BlueTeethReceiver extends BroadcastReceiver {
    private Blueteeth blueteeth;
    public BlueTeethReceiver(Blueteeth blueteeth){
        this.blueteeth = blueteeth;
    }
    @Override
    public void onReceive(Context context, Intent intent) {//step 4
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {//搜索设备时发现新设备
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if(!device.getName().equals(blueteeth.getBlueteethName()))return;

            new MessageUtil(blueteeth.getHandler(), ControlMessage.CON_BONDING).send();//正在绑定,验证密码
            boolean bondResult = device.createBond();
            if(bondResult){
                blueteeth.setStatus(5);
            }else{
                blueteeth.setStatus(7);
//                blueteeth.breakConnection();
                new MessageUtil(blueteeth.getHandler(), ControlMessage.CON_BONDING_ERR).send();//正在绑定
            }
            //blueteeth.getAdapter().cancelDiscovery();

        }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            if(blueteeth.getDevice()==null) {
                blueteeth.setStatus(7);
//                blueteeth.breakConnection();
                new MessageUtil(blueteeth.getHandler(), ControlMessage.CON_SCAN_NOTFOUND).send();//没有找到控制器
            }
        }
    }
}
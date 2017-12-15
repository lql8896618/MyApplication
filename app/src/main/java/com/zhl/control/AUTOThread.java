package com.zhl.control;

import android.util.Log;

/**
 * Created by Administrator on 2017/9/9.
 */
public class AUTOThread extends Thread {

    private int count;
    @Override
    public void run() {
        while(true) {
            if(ControlGlobal.blueteeth.isEnable()&&ControlGlobal.blueteeth.getStatus()==8){
                byte[] buff = {0x01,0x03,00,00,00,00,04};
                byte[] receiveBuff = new byte[20];
                int rLen = ControlGlobal.blueteeth.request(buff, receiveBuff,14);
                if(rLen==-1){//发送失败
                    ControlGlobal.blueteeth.breakConnection();
                    ControlGlobal.blueteeth.setEnable(true);
                    Log.d("blueteeth", "未收到请求返回数据");
                }else{
                    Log.d("blueteeth","接收到数据长度："+rLen);
                    if(rLen>receiveBuff.length)rLen = receiveBuff.length;
                    String str = "";
                    for(int i=0;i<rLen;i++){
                        str+=Integer.toHexString(receiveBuff[i])+" ";
                    }
                    Log.d("blueteeth", "接收数据：" + str);

                }
            }


            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

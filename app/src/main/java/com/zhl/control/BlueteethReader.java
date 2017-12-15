package com.zhl.control;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/13.
 */
public class BlueteethReader extends Thread{

    private static byte[] blueteethReceiveBuff = new byte[1024];
    private static int blueteethReceiveLen = 0;

    public static void clearBuff(){
        blueteethReceiveLen = 0;
    }

    public static int readBuff(byte[] buff,int minLength,int timeout){
        int count = timeout/100;
        while(true){
            if(blueteethReceiveLen>=minLength){
                int len = blueteethReceiveLen;
                for(int i=0;i<len;i++){
                    if(i<buff.length) {
                        buff[i] = blueteethReceiveBuff[i];
                    }
                }
                blueteethReceiveLen = 0;
                return len;
            }else{
                count--;
            }
            if(count==0){
                Log.d("blueteeth","请求超时1000");
                return -1;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void run() {
        while(true){
            if((ControlGlobal.blueteeth.getSocket()==null||!ControlGlobal.blueteeth.getSocket().isConnected())&&ControlGlobal.blueteeth.getStatus()==8){
                ControlGlobal.blueteeth.setStatus(0);
            }
            if(ControlGlobal.blueteeth.isEnable()&&ControlGlobal.blueteeth.getStatus()==8){
                try {
                    InputStream in = ControlGlobal.blueteeth.getSocket().getInputStream();
                    byte b;
                    if((b=(byte)in.read())!=-1){
                        blueteethReceiveBuff[blueteethReceiveLen++] = b;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(ControlGlobal.blueteeth.getStatus()==8){
                        ControlGlobal.blueteeth.setStatus(0);
                    }
                }
            }else{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

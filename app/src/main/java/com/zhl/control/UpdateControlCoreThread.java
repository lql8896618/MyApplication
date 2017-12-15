package com.zhl.control;

import android.os.Handler;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wex on 2017/9/22.
 * 更新控制器的固件程序的类
 */
public class UpdateControlCoreThread extends Thread {
    private int sendingProgress;
    private Handler handler;
    private SystemCoreCache scc;

    public UpdateControlCoreThread(Handler handler){
        this.handler = handler;
    }

    private void getCoreProgramFromWeb() {
        try {
            URL url = new URL("http://118.89.219.223/controlCore/version.jsp?param=program");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream in = connection.getInputStream();
                SystemCoreCache scc = new SystemCoreCache();
                int len;
                while ((len = in.read(scc.coreBodyCache, scc.coreLength, 1024)) != -1) {
                    scc.coreLength += len;
                }
                this.scc = scc;
            }
        } catch (Exception e) {
            Log.i("e", e.toString());
            e.printStackTrace();
        }

    }
    public void run() {

        new MessageUtil(handler, ControlMessage.NET_GETINGWEBPRO).send();//正在获取
        getCoreProgramFromWeb();

        if (scc == null) {
            new MessageUtil(handler, ControlMessage.NET_GETWEBPRO_ERROR).send();
            return;
        }
        new MessageUtil(handler, ControlMessage.UPDATE_CONTROL_CORE_SENDING,0).send();
        int pakCount = scc.getPakcount();
        int reqCount = 0;
        for(int i=0;i<pakCount;){
            if(!ControlGlobal.blueteeth.isEnable()||ControlGlobal.blueteeth.getStatus()!=8){
                new MessageUtil(handler, ControlMessage.UPDATE_CONTROL_BLEERROR).send();
                return;
            }
            byte []pak = scc.getSubPak(i);
            byte[] reBuff = new byte[5];
            int rLen = ControlGlobal.blueteeth.request(pak, reBuff, 5);
            if(rLen!=-1&&reBuff[0]==1&&reBuff[1]==0x20&&reBuff[2]==i&&reBuff[3]==1){
                i++;
                if(i==pakCount){//完成了
                    new MessageUtil(handler, ControlMessage.UPDATE_CONTROL_CORE_FINISH).send();
                }else{
                    sendingProgress = (int)(i*100.0/pakCount);
                    new MessageUtil(handler, ControlMessage.UPDATE_CONTROL_CORE_SENDING,sendingProgress).send();
                }
                reqCount = 0;
            }else{
                reqCount++;
                if(reqCount>3){
                    new MessageUtil(handler, ControlMessage.UPDATE_CONTROL_BLEERROR).send();
                    return;
                }
            }

        }

    }
}

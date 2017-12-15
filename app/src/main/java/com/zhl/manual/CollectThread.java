package com.zhl.manual;

import android.os.Handler;

import com.zhl.control.CommManage;
import com.zhl.control.ControlMessage;
import com.zhl.control.MessageUtil;

/**
 * Created by Administrator on 2017/10/16.
 */
public class CollectThread extends Thread{
    private Handler handler;
    private boolean status;
    private static int count;
    private CollectThread(){    }
    private static CollectThread collectThread;
    public static CollectThread getCollectThread(){
        if(collectThread==null){
            collectThread = new CollectThread();
            collectThread.start();
        }
        count = 60;
        return collectThread;
    }
    public void startCollect(Handler handler){
        this.handler = handler;
        this.status = true;
    }
    public void stopCollect() {
        this.status = false;
    }
    @Override
    public void run() {
        while(true){
            if(this.status&&ViewController.selectedPort>=1&&ViewController.selectedPort<=8) {
                int value = CommManage.collectData(ViewController.selectedPort, ViewController.selectedCollect);
                new MessageUtil(handler, ControlMessage.COLLECT_CON_DATA, value).send();
            }
            count--;
            if(count<=0){
                this.status = false;
                new MessageUtil(handler, ControlMessage.COLLECT_CON_STOP, 0).send();
            }
            try{
                Thread.sleep(1000);
            }catch (Exception e){}
        }
    }
}

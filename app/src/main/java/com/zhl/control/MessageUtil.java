package com.zhl.control;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2017/9/9.
 */
public class MessageUtil extends Thread {
    private int what;
    private int args;
    Handler handler;
    public MessageUtil(Handler handler,int what,int args){
        this.handler = handler;
        this.what = what;
        this.args = args;
    }
    public MessageUtil(Handler handler,int what){
        this.handler = handler;
        this.what = what;
    }
    @Override
    public void run() {
        Message mes = new Message();
        mes.what = what;
        mes.arg1 = args;
        if(handler == null) return;
        handler.sendMessage(mes);
    }

    public void send(){
        this.start();
    }
}

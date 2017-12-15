package com.zhl.control;

/**
 * Created by Administrator on 2017/9/9.
 */
public class BlueteethControlThread extends Thread{
    private Blueteeth blueteeth;
    BlueteethControlThread(Blueteeth blueteeth){
        this.blueteeth = blueteeth;
    }
    @Override
    public void run() {
        while(true) {
            if (this.blueteeth.isEnable()){
                this.blueteeth.createConnection();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

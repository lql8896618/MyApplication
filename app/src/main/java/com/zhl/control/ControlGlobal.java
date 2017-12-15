package com.zhl.control;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by Administrator on 2017/9/9.
 */
public class ControlGlobal {
    public static Blueteeth blueteeth;
    public static void init(Activity activity, Handler handler,String blueteethName){
        if(blueteethName==null||"".equals(blueteethName)){
            return;
        }
        if(blueteeth==null) {
            blueteeth = new Blueteeth(blueteethName, activity, handler);
        }else{
            blueteeth.setHandler(handler);
            blueteeth.setBlueteethName(blueteethName);
        }
        //new AUTOThread().start();
    }
}

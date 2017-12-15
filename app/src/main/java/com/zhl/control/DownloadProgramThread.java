package com.zhl.control;

import android.os.Handler;

import com.zhl.control.codeRow.CodeRow;
import com.zhl.design.DesignMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wex on 2017/9/29.
 * 向控制器下载用户积木程序
 */
public class DownloadProgramThread extends Thread {
    private int sendingProgress;
    private Handler handler;
    private ProgramCache programCache;

    public DownloadProgramThread(Handler handler,ProgramCache programCache){
        this.programCache = programCache;
        this.handler = handler;
    }


    public void run() {

        new MessageUtil(handler, DesignMessage.DOWNLOAD_SENDING,0).send();
        Map<Integer,ArrayList<CodeRow>> programList = programCache.getProgramList();

        Iterator<Integer> keySet = programList.keySet().iterator();
        int reqCount = 0;
        int pakCount = programCache.getPakCount();
        int curPakIndex = 0;
        while(keySet.hasNext()){
            int programIndex = keySet.next();
            boolean isLastProgramIndex = !keySet.hasNext();
            ArrayList<CodeRow> rowList = programList.get(programIndex);
            int groupCount = (rowList.size()%20==0)?(rowList.size()/20):(rowList.size()/20+1);
            for(int i=0;i<groupCount;){
                if(!ControlGlobal.blueteeth.isEnable()||ControlGlobal.blueteeth.getStatus()!=8){

                    new MessageUtil(handler, DesignMessage.DOWNLOAD_BLEERROR).send();
                    return;
                }
                byte []pak = programCache.getSubPak(programIndex,i,isLastProgramIndex);
                byte[] reBuff = new byte[5];
                int rLen = ControlGlobal.blueteeth.request(pak, reBuff, 5);
                if(rLen!=-1&&reBuff[0]==1&&reBuff[1]==2&&reBuff[2]==programIndex&&reBuff[3]==i){
                    i++;

                    sendingProgress = (int)(curPakIndex*100.0/pakCount);
                    new MessageUtil(handler, DesignMessage.DOWNLOAD_SENDING,sendingProgress).send();

                    curPakIndex++;
                    reqCount = 0;
                }else{
                    reqCount++;
                    if(reqCount>3){
                        new MessageUtil(handler, DesignMessage.DOWNLOAD_BLEERROR).send();
                        return;
                    }
                }
            }
        }


        new MessageUtil(handler, DesignMessage.DOWNLOAD_FINISH).send();
    }
}

package com.zhl.control;

/**
 * Created by wex on 2017/8/28.
 *更新控制器固件程序时，缓存程序存储区与方法
 */
public class SystemCoreCache {
    private final int PAK_SIZE = 256;//蓝牙传输的分包长度
    public byte[] coreBodyCache= new byte[48000];//固件程序存储区
    public int coreLength = 0;


    public byte[] getSubPak(int index){
        int len = (index>coreLength/PAK_SIZE) ? coreLength % coreLength:PAK_SIZE;
        byte[] sub = new byte[len+12];
        sub[0] = 1;
        sub[1] = 0x20;
        sub[2] = (byte)(coreLength>>24);
        sub[3] = (byte)(coreLength>>16);
        sub[4] = (byte)(coreLength>>8);
        sub[5] = (byte)coreLength;
        sub[6] = (byte)(len>>8);
        sub[7] = (byte)len;
        sub[9] = (byte)index;

        for(int i=0;i<len;i++){
            sub[i+10] = coreBodyCache[i+index*PAK_SIZE];
        }
        byte sum = 0;
        for(int i=0;i<PAK_SIZE+11;i++){
            sum+=(byte)sub[i];
        }
        sub[PAK_SIZE+11] = sum;
        return sub;
    }

    public int getPakcount(){
        return coreLength/PAK_SIZE + ((coreLength%PAK_SIZE==0)?0:1);
    }
}

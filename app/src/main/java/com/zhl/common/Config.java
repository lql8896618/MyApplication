package com.zhl.common;

/**
 * Created by Administrator on 2017/9/17.
 */
public class Config {


    public static String    controlName;//控制器名称
    public static float     appBgVolume;//app背景音乐音量  0到1
    public static float     appSoundVolume;//app音效音量  0到1

    public static void loadConfig(){
        //controlName = "ZHL1711004";
        appBgVolume = 0.5f;
        appSoundVolume = 0.5f;
    }
    public static void saveConfig(){

    }
}

package com.zhl.common;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import com.zhl.R;

/**
 * Created by Administrator on 2017/10/9.
 */
public class SoundUtil {
    private static MediaPlayer bgMusic;
    private static MediaPlayer buttonSound;

    public static void init(Context context){
        bgMusic = MediaPlayer.create(context, R.raw.main_music2);
        bgMusic.setLooping(true);
        bgMusic.setVolume(Config.appBgVolume,Config.appBgVolume);

        buttonSound = MediaPlayer.create(context,R.raw.button2);
        buttonSound.setVolume(Config.appSoundVolume,Config.appSoundVolume);
    }

    public static void bgMusicStart(){
        if(bgMusic==null)return;
        bgMusic.start();
    }
    public static void bgMusicStop(){
        if(bgMusic==null)return;
        bgMusic.stop();
    }
    public static void setBgMusicVolume(float volume){
        if(bgMusic==null)return;
        bgMusic.setVolume(volume, volume);
    }

    public static void playButtonSound(){
        if(buttonSound==null)return;
        buttonSound.start();
    }
    public static void setButtonSoundVolume(float volume){
        if(buttonSound==null)return;
        buttonSound.setVolume(volume, volume);
    }
}

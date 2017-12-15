package com.zhl.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhl.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @创建者：刘启亮
 * @创建时间： 2017/8/22 0022.
 * @描述：全局变量信息
 */
public class ZHLApplication extends Application {



    public static final float referWidthPixel = 1080;
    public static final float referHeightPixel = 1920;

    public static int widthScreen = 0; //屏幕宽度
    public static int heightScreen = 0; //屏幕高度
    public static WindowManager windowManagerHandler; //Android系统的窗口服务
    public static Context applicationContextHandle; //应用全局上下文
    public static ZHLApplication applicationHandler; //一个application对象
    public static SQLiteDatabase databaseHandler;
    private static String TAG = "ZhiHuiLong_App";

    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationHandler = this;
        applicationContextHandle = getApplicationContext();
        windowManagerHandler = (WindowManager) applicationContextHandle.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        windowManagerHandler.getDefaultDisplay().getMetrics(mDisplayMetrics);
        widthScreen = mDisplayMetrics.widthPixels;
        heightScreen = mDisplayMetrics.heightPixels;

        vibrator = (Vibrator) applicationContextHandle.getSystemService(Context.VIBRATOR_SERVICE);

        initLocalDatabase();
        SharedPreferences sp = getSharedPreferences(getString(R.string.sp_config_file), Context.MODE_PRIVATE);
        Config.loadConfig();
        Config.controlName = sp.getString(getString(R.string.sp_control_name), "");

        SoundUtil.init(applicationContextHandle);
        //启动背景音乐
        SoundUtil.bgMusicStart();


    }

    /**
     * 初始化APP数据库
     */
    private void initLocalDatabase(){
        databaseHandler = openOrCreateDatabase("zhl.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        databaseHandler.execSQL("create table if not exists [design_pdata](" +
                        "_id varchar(32) primary key," +
                        "_name varchar," +
                        "_create_date varchar," +
                        "_update_date varchar," +
                        "_icon blob," +
                        "_object blob)"
        );

//        databaseHandler.execSQL("drop table if exists [production_sys]");

        databaseHandler.execSQL("create table if not exists [production_sys](" +
                        "_worksname varchar," +
                        "_version varchar," +
                        "_dir varchar," +
                        "_image blob)"
        );

//        databaseHandler.execSQL("drop table if exists [production_sys_sheet]");

        databaseHandler.execSQL("create table if not exists [production_sys_sheet](" +
                        "_dir varchar," +
                        "[_indx] integer," +
                        "_pagenum integer," +
                        "_pagesign varchar," +
                        "_title varchar," +
                        "_marginbottom integer," +
                        "_desc varchar," +
                        "_image blob)"
        );

        databaseHandler.execSQL("create table if not exists [design_property_help_html](" +
                "_id varchar primary key," +
                "_version integer," +
                "_html text," +
                "_code varchar)");
        databaseHandler.execSQL("create table if not exists [global_resources](" +
                "_id varchar primary key," +
                "_flag varchar," +
                "_data blob)");
    }

    public static int convertSize(int sourceLen, float width){
        float scalew = widthScreen / width;
        return (int)(sourceLen * scalew);
    }


    public static void convertViewSize(View view, float width){
        float scalew = widthScreen / width;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(layoutParams instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams fparams = (FrameLayout.LayoutParams) layoutParams;
            if (fparams.width > 0) fparams.width = (int) (scalew * fparams.width);
            if (fparams.height > 0) fparams.height = (int) (scalew * fparams.height);
            if (fparams.leftMargin > 0) fparams.leftMargin = (int) (scalew * fparams.leftMargin);
            if (fparams.topMargin > 0) fparams.topMargin = (int) (scalew * fparams.topMargin);
            view.setLayoutParams(fparams);

        }else if(layoutParams instanceof AbsoluteLayout.LayoutParams){
            AbsoluteLayout.LayoutParams aparams = (AbsoluteLayout.LayoutParams) layoutParams;
            if (aparams.width > 0) aparams.width = (int) (scalew * aparams.width);
            if (aparams.height > 0) aparams.height = (int) (scalew * aparams.height);
//            if (aparams.x > 0) aparams.x = (int) (scalew * aparams.x);
//            if (aparams.y > 0) aparams.y = (int) (scalew * aparams.y);
            if(view.getX() > 0) view.setX((int) (scalew * view.getX()));
            if(view.getY() > 0) view.setY((int) (scalew * view.getY()));
            view.setLayoutParams(aparams);
        }
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((TextView) view).getTextSize() * scalew);
        }
    }

    public static void calculatorTextViewSize(TextView text, float width){

    }

    public static void debug(String message){
        Log.d(TAG, message);
    }

    public static void info(String message){
        Log.i(TAG, message);
    }

    public static void error(String message){
        Log.e(TAG, message);
    }

    public static Typeface getFont(){
        return Typeface.createFromAsset(applicationContextHandle.getAssets(), "hanyixingshi.ttf");
    }

    /**
     * 震动指定毫秒数
     * @param ms
     */
    public static void vibrate(long ms){
        applicationHandler.vibrator.vibrate(ms);
    }

    public static HashMap<String, Object> getAppVersion(){
        PackageManager manager = applicationContextHandle.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(applicationContextHandle.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = packageInfo.versionName;
        int versionCode = packageInfo.versionCode;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("versionName", versionName);
        map.put("versionCode", versionCode);
        return map;
    }

    /**
     * 计算图标大小（宽度高度的像素）
     * @param res
     * @return
     */
    public static Size calculatorImageSize(int res){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        Bitmap mBitmap =BitmapFactory.decodeResource(applicationContextHandle.getResources(), res, opts);
        int width=opts.outWidth;
        int height=opts.outHeight;
        Size size = new Size(width, height);
        return size;
    }

    public static String createUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static int NodeID = 0;

    public static int createNodeID(){
        NodeID++;
        return NodeID;
    }

    public static String getCurrentDateTime(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(new Date());
    }

    public static Bitmap toBitmap(int resID){
        Bitmap bitmap = BitmapFactory.decodeResource(applicationHandler.getResources(), resID);
        return bitmap;
    }

    public static byte[] bitmapToBArray(int resID){
        Bitmap bitmap = BitmapFactory.decodeResource(applicationHandler.getResources(), resID);
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] array = baos.toByteArray();
        return array;
    }

    public static byte[] objectToBArray(Serializable serializable){
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            return data;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Serializable bArrayToObject(byte[] array){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Serializable serializable = (Serializable)objectInputStream.readObject();
            return serializable;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int dip2px(float dpValue) {
        final float scale = applicationContextHandle.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

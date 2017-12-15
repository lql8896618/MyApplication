package com.zhl.base.version;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zhl.common.ZHLApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Crazyfzw on 2016/8/21.
 * 创建服务完成apk文件的下载，下载完成后调用系统的安装程序完成安装
 */
public class DownloadApkService extends IntentService{

    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private AlertDialog.Builder builder;

    public DownloadApkService() {
        super("DownloadApkService");
    }

    /**
     * 在onHandleIntent中下载apk文件
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        //初始化通知，用于显示下载进度
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        String appName = getString(getApplicationInfo().labelRes);
        int icon = android.R.mipmap.sym_def_app_icon;
        mBuilder.setContentTitle(appName).setSmallIcon(icon);

        String urlStr = intent.getStringExtra("apkUrl"); //从intent中取得apk下载路径
        float apkSize = intent.getIntExtra("apkSize", 0);

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //建立下载连接
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();

            String sdtemp_file = Environment.getExternalStorageDirectory() + "/zhl.apk";
            File apkfile = new File(sdtemp_file);
            if(apkfile.exists()) apkfile.delete();
            apkfile.createNewFile();

            outputStream = new FileOutputStream(apkfile);
            byte[] buffer = new byte[1024 * 8];

            int oldProgress = 0;
            long read_count = 0;
            while (true) {
                int n = inputStream.read(buffer, 0, 1024 * 8);
                if(n == -1) break;
                read_count += n;
                outputStream.write(buffer, 0, n);
                float temp = ((float)read_count) / 1024F;
                temp = temp / apkSize;
                temp = temp * 100;
                int progress = (int)temp;
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，则会造成界面卡顿
                if (progress != oldProgress) {
                    updateProgress(progress);
                }
                ZHLApplication.info(progress + "%");
                oldProgress = progress;
            }

            // 下载完成,调用installAPK开始安装文件
            installAPk(apkfile);
            mNotifyManager.cancel(NOTIFICATION_ID);

        } catch (Exception e) {
            ZHLApplication.error(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {

                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    /**
     * 实时更新下载进度条显示
     * @param progress
     */
    private void updateProgress(int progress) {
        mBuilder.setContentTitle("应用更新").setContentText("已下载" + progress + "%").setProgress(100, progress, false);
        PendingIntent pendingintent = PendingIntent.getActivity(
                this,
                0,
                new Intent(),
                PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mNotifyManager.notify(NOTIFICATION_ID, mBuilder.getNotification());
    }


    /**
     * 调用系统安装程序安装下载好的apk
     * @param apkFile
     */
    private void installAPk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
        try {
            String[] command = {"chmod", "777", apkFile.toString()}; //777代表权限 rwxrwxrwx
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

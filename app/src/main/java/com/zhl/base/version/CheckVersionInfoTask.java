package com.zhl.base.version;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.zhl.R;
import com.zhl.common.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Crazyfzw on 2016/8/21.
 * CheckVersionInfoTask.java
 * 从服务器取得版本信息，与本地apk对比版本号，判断是否有更新，然后用Dialog让用户选择是否更新
 * 若用户选择更新，则调用服务去完成apk文件的下载
 */
public class CheckVersionInfoTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "CheckVersionInfoTask";
    private ProgressDialog dialog;
    private Context mContext;
    private boolean mShowProgressDialog;
    private boolean tip;
    private static final String VERSION_INFO_URL = "http://" + ServerUtil.Server_Address + "/app.jsp?action=getVersion";


    public CheckVersionInfoTask(Context context, boolean showProgressDialog, boolean tip) {
        this.mContext = context;
        this.mShowProgressDialog = showProgressDialog;
        this.tip = tip;
    }

    //初始化显示Dialog
    protected void onPreExecute() {
        if (mShowProgressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("正在检测版本");
            dialog.show();
        }
    }

    //在后台任务(子线程)中检查服务器的版本信息
    @Override
    protected String doInBackground(Void... params) {
        return getVersionInfo(VERSION_INFO_URL);
    }


    //后台任务执行完毕后，解除Dialog并且解析return返回的结果
    @Override
    protected void onPostExecute(String result) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result);
        }
    }


    /**
     * 从服务器取得版本信息
     * @return
     */
    public String getVersionInfo(String urlStr){
        HttpURLConnection uRLConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
        try {
            URL url = new URL(urlStr);
            uRLConnection = (HttpURLConnection) url.openConnection();
            uRLConnection.setRequestMethod("GET");
            is = uRLConnection.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            result = strBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "http post error");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ignored) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {

                }
            }
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
        return result;
    }

    /**
     *
     * @param result
     */
    private void parseJson(String result) {
        try {
            /*
                versionName=1.0.0
                versionCode=1
                updateTime=2017-11-30 08:00:00
                apkName=zhl_1.apk
                apkSize=36660
                updateContent=\u66F4\u65B0\u4E86xxxxxxxxxxx.<br>\u66F4\u65B0\u4E86yyyyyyyy.<br>\u4F18\u5316\u4E86asdfkljgee.
             */
            JSONObject obj = new JSONObject(result);
            String versionName = obj.getString("versionName");
            Integer serverVersionCode = obj.getInt("versionCode");
            String updateTime = obj.getString("updateTime");
            String updateContent = obj.getString("updateContent");
            final Integer apkSize = obj.getInt("apkSize");

            //取得已经安装在手机的APP的版本号 versionCode
            int versionCode = getCurrentVersionCode();

            //对比版本号判断是否需要更新
            if (serverVersionCode > versionCode) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("发现了新版本" + versionName + ",更新时间" + updateTime);
                builder.setMessage(Html.fromHtml(updateContent))
                        .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //下载apk文件
                                Toast.makeText(mContext, "正在下载新版本,在任务条中查看下载进度.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, DownloadApkService.class);
                                intent.putExtra("apkUrl", "http://" + ServerUtil.Server_Address + "/app.jsp?action=getApk");
                                intent.putExtra("apkSize", apkSize);
                                mContext.startService(intent);
                            }
                        })
                        .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog dialog = builder.create();
                //点击对话框外面,对话框不消失
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } else if (mShowProgressDialog && tip) {
                Toast.makeText(mContext, "当前已经是最新版本了哦", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e(TAG, "parse json error");
        }
    }

    /**
     * 取得当前版本号
     * @return
     */
    public int getCurrentVersionCode() {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        return 0;
    }
}

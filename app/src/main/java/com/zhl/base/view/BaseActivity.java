package com.zhl.base.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhl.R;
import com.zhl.base.version.CheckVersionInfoTask;
import com.zhl.common.ServerUtil;
import com.zhl.common.ZHLApplication;
import com.zhl.control.AUTOThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by lql on 2016/11/26 0026.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //checkUpdate();
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    public FrameLayout.LayoutParams getFrameLayoutParams(int width, int height){
        return new FrameLayout.LayoutParams(width, height);
    }

    public void checkUpdate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkVersion();
            }
        }).start();
    }

    private void checkVersion(){
        HashMap<String, Object> localVersionMap = ZHLApplication.getAppVersion();
        try{
            final HashMap<String, Object> serverVersionMap = ServerUtil.getServerAppVersion();
            Integer local_version = (Integer)localVersionMap.get("versionCode");
            Double versionCode = (Double)serverVersionMap.get("versionCode");
            if(local_version < versionCode){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowUpdateDialog(serverVersionMap);
                    }
                });
            }
        }catch (Exception e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseActivity.this, "服务器连接失败,无法获取更新列表", Toast.LENGTH_SHORT);
                }
            });

        }

    }

    private void ShowUpdateDialog(final HashMap<String, Object> serverVersionMap){
        final String versionName = (String)serverVersionMap.get("versionName");
        final String updateTime = (String)serverVersionMap.get("updateTime");
        final Double apkSize = (Double)serverVersionMap.get("apkSize");
        final String updateContent = (String)serverVersionMap.get("updateContent");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
            .setTitle("发现了新版本 v" + versionName + ", 文件大小" + apkSize + "kb")
            .setMessage(Html.fromHtml(updateContent))
            .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    UpdateDialog dialog = new UpdateDialog(builder.getContext(), serverVersionMap);
                    dialog.show();
                    dialogInterface.dismiss();
                }
            })
            .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    class UpdateDialog extends Dialog{
        private TextView titleTxt;
        private ProgressBar progressBar;
        private HashMap<String, Object> serverVersionMap;
        public UpdateDialog(Context context, HashMap<String, Object> serverVersionMap) {
            super(context, R.style.ActionSheetDialogStyle);
            this.serverVersionMap = serverVersionMap;
            setContentView(R.layout.design_dlg_download);
            titleTxt = findViewById(R.id.design_dlg_download_txt_title);
            progressBar = findViewById(R.id.design_dlg_download_progressbar);

            titleTxt.setText("正在下载 0%");
            setCanceledOnTouchOutside(false);
            Window dialogWindow = getWindow();
            dialogWindow.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = (int)(ZHLApplication.widthScreen * .8f);
            lp.height = (int)(ZHLApplication.heightScreen * .20f);
            dialogWindow.setAttributes(lp);
        }

        public void setProgressValue(int progressValue){
            if(progressBar.getProgress() != progressValue){
                titleTxt.setText(" 已下载 " + progressValue + "%");
                progressBar.setProgress(progressValue);
            }
        }

        @Override
        public void show() {
            super.show();
            AppUpdate appUpdate = new AppUpdate(getContext(), this, ((Double)serverVersionMap.get("apkSize")).intValue());
            appUpdate.execute();
        }
    }

    class AppUpdate extends AsyncTask<Void, Integer, Integer> {

        private UpdateDialog dialog;
        private Context context;
        private int size;

        public AppUpdate(Context context, UpdateDialog dlg, int size){
            this.dialog = dlg;
            this.context = context;
            this.size = size;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                URL url = new URL("http://" + ServerUtil.Server_Address + "/app.jsp?action=getApk");
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

                String path = getDataDir().getPath();

                String sdtemp_file = path + "/zhl.apk";
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
                    temp = temp / size;
                    temp = temp * 100;
                    int progress = (int)temp;
                    if (progress != oldProgress) {
                        publishProgress(progress);
                    }
                    ZHLApplication.info(progress + "%");
                    oldProgress = progress;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context, "com.yuneec.android.saleelfin.fileprovider", apkfile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);

//                try {
//                    String[] command = {"chmod", "777", apkfile.toString()}; //777代表权限 rwxrwxrwx
//                    ProcessBuilder builder = new ProcessBuilder(command);
//                    builder.start();
//                } catch (IOException ignored) {
//                }
//                intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
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
            return 0;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgressValue(values[0]);
        }
    }

}

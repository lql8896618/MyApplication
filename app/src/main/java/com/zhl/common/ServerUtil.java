package com.zhl.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/28 0028
 * 描述：
 */
public class ServerUtil {

    public static final String Server_Address = "118.89.219.223";

    public static String getHttpServerResponseContent(String http_url) throws ServerConnectFaildException{
        HttpURLConnection connection = null;
        try {
            URL url = new URL(http_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer content = new StringBuffer();
                while(true){
                    String temp = reader.readLine();
                    if(temp == null) break;
                    content.append(temp);
                }
                inputStream.close();
                reader.close();
                return content.toString();
            }
        } catch (Exception e) {
            throw new ServerConnectFaildException();
        }finally {
            connection.disconnect();
        }
        return "";
    }

    public static byte[] getHttpServerImage(String http_url){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(http_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                inputStream.close();
                byte[] arr = baos.toByteArray();
                baos.close();
                return arr;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return null;
    }

    public static HashMap<String, Object> getServerAppVersion() throws ServerConnectFaildException {
        //{"versionName":"1.0.0.0", "versionCode":"1", "size": 36666}
        String content = ServerUtil.getHttpServerResponseContent("http://" + ServerUtil.Server_Address + "/app.jsp?action=getVersion");
        Gson gson = new Gson();
        HashMap<String, Object> map = gson.fromJson(content, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        return map;
    }

    public static class ServerConnectFaildException extends Exception{
        public ServerConnectFaildException(){
            super();
        }
    }
}

package com.zhl.production.service;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhl.R;
import com.zhl.common.ServerUtil;
import com.zhl.common.ZHLApplication;
import com.zhl.production.data.Production;
import com.zhl.production.data.Sheet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/22 0022
 * 描述：
 */
public class ProductionService {

    public static boolean isExistsSheet(String dir, int pageNum){
        SQLiteDatabase db = ZHLApplication.databaseHandler;
        Cursor cursor = db.rawQuery("select count(*) as _count from [production_sys_sheet] where _dir = '" + dir + "' and _pagenum = " + pageNum, null);
        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndex("_count")) == 1;
    }

    public static List<Sheet> getSheetListByDir(String dir){
        SQLiteDatabase db = ZHLApplication.databaseHandler;
        Cursor cursor = db.rawQuery("select _dir, _indx, _pagenum, _pagesign, _title, _marginbottom, _desc, _image" +
                        " from [production_sys_sheet] where _dir = ? order by _pagenum asc", new String[]{dir});
        List<Sheet> sheetList = new ArrayList<Sheet>();
        while(cursor.moveToNext()){
            Sheet sheet = new Sheet();
            sheet.setDir(dir);
            sheet.setIndex(cursor.getInt(cursor.getColumnIndex("_indx")));
            sheet.setPageNumber(cursor.getInt(cursor.getColumnIndex("_pagenum")));
            sheet.setPageSign(cursor.getString(cursor.getColumnIndex("_pagesign")));
            sheet.setTitle(cursor.getString(cursor.getColumnIndex("_title")));
            sheet.setMarginBottom(cursor.getInt(cursor.getColumnIndex("_marginbottom")));
            sheet.setDescription(cursor.getString(cursor.getColumnIndex("_desc")));
            byte[] image_bytes = cursor.getBlob(cursor.getColumnIndex("_image"));

            if(image_bytes != null) sheet.setBitmap(BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length));

            if(sheet.getDescription() == null || sheet.getDescription().equals(""))
                sheet.setDescription("");
            sheetList.add(sheet);
        }
        return sheetList;
    }

    /**
     * 获取指定作品的某个页
     * @param dir
     * @param no
     * @return
     */
    public static Sheet getSheet(String dir, int no){
        SQLiteDatabase db = ZHLApplication.databaseHandler;
        Cursor cursor = db.rawQuery(
                "select _dir, _indx, _pagenum, _pagesign, _title, _marginbottom, _desc, _image" +
                        " from [production_sys_sheet] where _dir = '" + dir + "' and _pagenum = " + no, null);
        if(cursor.moveToNext()){
            Sheet sheet = new Sheet();
            sheet.setDir(dir);
            sheet.setIndex(cursor.getInt(cursor.getColumnIndex("_indx")));
            sheet.setPageNumber(cursor.getInt(cursor.getColumnIndex("_pagenum")));
            sheet.setPageSign(cursor.getString(cursor.getColumnIndex("_pagesign")));
            sheet.setTitle(cursor.getString(cursor.getColumnIndex("_title")));
            sheet.setMarginBottom(cursor.getInt(cursor.getColumnIndex("_marginbottom")));
            sheet.setDescription(cursor.getString(cursor.getColumnIndex("_desc")));
            byte[] image_bytes = cursor.getBlob(cursor.getColumnIndex("_image"));

            if(image_bytes != null) sheet.setBitmap(BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length));

            if(sheet.getDescription() == null || sheet.getDescription().equals(""))
            sheet.setDescription("");
            return sheet;
        }
        return null;
    }

    private static Bitmap getNullImage(){
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.noimage).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        p.setTextSize(40);
        canvas.drawText("此页没有图片", 100, 100, p);
        return bitmap;
    }

    /**
     * 获取服务器端的作品列表
     * @return
     */
    public static List<Production> getServerProductionList() throws ServerUtil.ServerConnectFaildException{
        String plist_str = ServerUtil.getHttpServerResponseContent("http://www.zhihuilong.net/worksTutorial/list.jsp");
        Gson gs = new Gson();
        List<Production> list = gs.fromJson(plist_str, new TypeToken<List<Production>>() {
        }.getType());
        for(int i = 0; i < list.size(); i++){
            byte[] image_bytes = ServerUtil.getHttpServerImage("http://www.zhihuilong.net/worksTutorial/" + list.get(i).getDirName() + "/cover.png");
            list.get(i).setBitmap(BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length));
        }

        return list;
    }

    /**
     * 获取本地存储的作品列表
     * @return
     */
    public static List<Production> getLocalProductionList(){
        SQLiteDatabase db = ZHLApplication.databaseHandler;
        Cursor cursor = db.rawQuery("select _worksname, _version, _dir, _image from [production_sys]", null);
        List<Production> list = new ArrayList<Production>();
        while(cursor.moveToNext()){
            String worksname = cursor.getString(cursor.getColumnIndex("_worksname"));
            String version = cursor.getString(cursor.getColumnIndex("_version"));
            String dir = cursor.getString(cursor.getColumnIndex("_dir"));
            byte[] image_bytes = cursor.getBlob(cursor.getColumnIndex("_image"));
            Production p = new Production();
            p.setWorksname(worksname);
            p.setVersion(version);
            p.setDirName(dir);
            p.setBitmap(BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length));
            list.add(p);
        }
        return list;
    }

    /**
     * 获取服务端指定作品下的页码列表
     * @param dir
     * @return
     */
    public static List<Sheet> getServerSheetListByDir(String dir) throws ServerUtil.ServerConnectFaildException{
        String json_str = ServerUtil.getHttpServerResponseContent("http://www.zhihuilong.net/worksTutorial/getWorks.jsp?dir=" + dir);
        Gson gs = new Gson();
        return gs.fromJson(json_str, new TypeToken<List<Sheet>>(){}.getType());
    }

    /**
     * 同步服务端与本地的作品列表和作品中页码列表
     */
    public static void syncProductions()  throws ServerUtil.ServerConnectFaildException{
        List<Production> server_plist = getServerProductionList();
        if(server_plist == null || server_plist.size() == 0) return;
        List<Production> local_plist = getLocalProductionList();
        boolean sync = true;
        if (server_plist.size() != local_plist.size()) {
            for(int i = 0; i < server_plist.size(); i++){
                Production server_p = server_plist.get(i);
                savePSheetToLocal(server_p.getDirName());
            }
            sync = false;
        }else {
            for (int i = 0; i < server_plist.size(); i++) {
                Production server_p = server_plist.get(i);
                Production local_p = local_plist.get(i);
                if (!server_p.equals(local_p)) {
                    savePSheetToLocal(server_p.getDirName());
                    sync = false;
                }
            }
        }
        if(!sync) saveProductionListToLocal(server_plist);
    }

    public static void saveProductionListToLocal(List<Production> server_plist){
        SQLiteDatabase db = ZHLApplication.databaseHandler;
        db.execSQL("delete from [production_sys]");
        //_worksname, _version, _dir
        try {

            for (int i = 0; i < server_plist.size(); i++) {
                Production p = server_plist.get(i);
                ContentValues values = new ContentValues();
                values.put("_worksname", p.getWorksname());
                values.put("_version", p.getVersion());
                values.put("_dir", p.getDirName());
                byte[] image_bytes = ServerUtil.getHttpServerImage("http://www.zhihuilong.net/worksTutorial/" + p.getDirName() + "/cover.png");
                values.put("_image", image_bytes);
                db.insert("[production_sys]", "", values);
            }
        }catch (Exception e){
            ZHLApplication.error(e.getMessage());
        }
    }

    /**
     * 保存作品到本地
     * @param dirName
     */
    public static void savePSheetToLocal(String dirName) throws ServerUtil.ServerConnectFaildException{
        List<Sheet> server_slist = getServerSheetListByDir(dirName);
        SQLiteDatabase db = ZHLApplication.databaseHandler;
        db.execSQL("delete from [production_sys_sheet] where _dir = '" + dirName + "'");
        //_dir, [_indx], _pagenum, _pagesign, _title, _marginbottom, _desc, _image
        try {

            for (int i = 0; i < server_slist.size(); i++) {
                Sheet sheet = server_slist.get(i);
                ContentValues values = new ContentValues();
                values.put("_dir", dirName);
                values.put("[_indx]", sheet.getIndex());
                values.put("_pagenum", sheet.getPageNumber());
                values.put("_pagesign", sheet.getPageSign());
                values.put("_title", sheet.getTitle());
                values.put("_marginbottom", sheet.getMarginBottom());
                values.put("_desc", sheet.getDescription());

                byte[] image_bytes = ServerUtil.getHttpServerImage("http://www.zhihuilong.net/worksTutorial/" + dirName + "/" + sheet.getImage());
                values.put("_image", image_bytes);
                db.insert("[production_sys_sheet]", "", values);
            }
        }catch (Exception e){
            ZHLApplication.error(e.getMessage());
        }
    }

}


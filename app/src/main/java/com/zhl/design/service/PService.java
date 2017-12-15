package com.zhl.design.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhl.common.ZHLApplication;
import com.zhl.R;
import com.zhl.design.data.NData;
import com.zhl.design.data.PData;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/13 0013
 * 描述：
 */
public class PService {

    /**
     * 读取最后一次保存的程序
     * @return
     */
    public static PData loadLastEditorPData(){
        String sql = "select * from design_pdata group by _update_date having _update_date = max(_update_date)";
        Cursor cursor = ZHLApplication.databaseHandler.rawQuery(sql, null);
        if(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("_name"));
            String createDate = cursor.getString(cursor.getColumnIndex("_create_date"));
            String updateDate = cursor.getString(cursor.getColumnIndex("_create_date"));
            byte[] icon_data = cursor.getBlob(cursor.getColumnIndex("_icon"));
            byte[] ndata = cursor.getBlob(cursor.getColumnIndex("_object"));

            Bitmap icon = BitmapFactory.decodeByteArray(icon_data, 0, icon_data.length);
            ArrayList<NData> datas = (ArrayList<NData>) ZHLApplication.bArrayToObject(ndata);
            PData pData = new PData();
            pData.setId(id);
            pData.setName(name);
            pData.setCreateDate(createDate);
            pData.setUpdateDate(updateDate);
            pData.setIcon(icon);
            pData.setFunctions(datas);
            pData.setFunctions(datas);
            return pData;
        }
        return null;
    }

    /**
     * 加载指定ID的程序
     * @param id
     * @return
     */
    public static PData loadPData(String id){

        String sql = "select * from design_pdata where _id = '" + id + "'";
        Cursor cursor = ZHLApplication.databaseHandler.rawQuery(sql, null);
        if(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("_name"));
            String createDate = cursor.getString(cursor.getColumnIndex("_create_date"));
            String updateDate = cursor.getString(cursor.getColumnIndex("_create_date"));
            byte[] icon_data = cursor.getBlob(cursor.getColumnIndex("_icon"));
            byte[] ndata = cursor.getBlob(cursor.getColumnIndex("_object"));

            Bitmap icon = BitmapFactory.decodeByteArray(icon_data, 0, icon_data.length);
            ArrayList<NData> datas = (ArrayList<NData>) ZHLApplication.bArrayToObject(ndata);
            PData pData = new PData();
            pData.setId(id);
            pData.setName(name);
            pData.setCreateDate(createDate);
            pData.setUpdateDate(updateDate);
            pData.setIcon(icon);
            pData.setFunctions(datas);
            return pData;
        }
        return null;
    }

    public static void deletePData(String id){
        String sql = "delete from design_pdata where _id = ?";
        ZHLApplication.databaseHandler.execSQL(sql, new String[]{id});
    }

    /**
     * 保存一个程序
     * @param pData
     */
    public static void savePData(PData pData){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", pData.getId());
        contentValues.put("_name", pData.getName());
        contentValues.put("_create_date", pData.getCreateDate());
        contentValues.put("_update_date", pData.getUpdateDate());
//        contentValues.put("_icon", ZHLApplication.bitmapToBArray(R.drawable.design_node_101_icon));
        contentValues.put("_object", ZHLApplication.objectToBArray(pData.getFunctions()));
        ZHLApplication.databaseHandler.replace("design_pdata", "", contentValues);
    }

    /**
     * 修改程序名称
     * @param id
     * @param name
     */
    public static void changePName(String id, String name){
        String sql = "update design_pdata set _name = ? where _id = ?";
        ZHLApplication.databaseHandler.execSQL(sql, new String[]{name, id});
    }

    /**
     * 获取所有程序
     * @return
     */
    public static List<PData> findAll(){
        String sql = "select * from design_pdata order by _update_date desc";
        Cursor cursor = ZHLApplication.databaseHandler.rawQuery(sql, null);
        List<PData> list = new ArrayList<PData>();
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("_name"));
            String createDate = cursor.getString(cursor.getColumnIndex("_create_date"));
            String updateDate = cursor.getString(cursor.getColumnIndex("_create_date"));
            byte[] icon_data =cursor.getBlob(cursor.getColumnIndex("_icon"));
            Bitmap icon = BitmapFactory.decodeByteArray(icon_data, 0, icon_data.length);
            byte[] ndata = cursor.getBlob(cursor.getColumnIndex("_object"));
            ArrayList<NData> nDataArrayList = (ArrayList<NData>) ZHLApplication.bArrayToObject(ndata);
            PData pData = new PData();
            pData.setId(id);
            pData.setName(name);
            pData.setCreateDate(createDate);
            pData.setUpdateDate(updateDate);
            pData.setIcon(icon);
            pData.setFunctions(nDataArrayList);
            list.add(pData);
        }
        return list;
    }

    public static List<PData> findAllOfBase(){
        String sql = "select _id, _name, _update_date, _icon from design_pdata order by _update_date desc";
        Cursor cursor = ZHLApplication.databaseHandler.rawQuery(sql, null);
        List<PData> list = new ArrayList<PData>();
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("_name"));
            String updateDate = cursor.getString(cursor.getColumnIndex("_update_date"));
            byte[] icon_data =cursor.getBlob(cursor.getColumnIndex("_icon"));
            Bitmap icon = BitmapFactory.decodeByteArray(icon_data, 0, icon_data.length);
            PData pData = new PData();
            pData.setId(id);
            pData.setName(name);
            pData.setUpdateDate(updateDate);
            pData.setIcon(icon);
            list.add(pData);
        }
        return list;
    }

}

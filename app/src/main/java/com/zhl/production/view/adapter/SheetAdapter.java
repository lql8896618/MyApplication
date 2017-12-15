package com.zhl.production.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.production.data.Sheet;
import com.zhl.production.service.ProductionService;
import com.zhl.production.view.SheetActivity;

import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/1 0001
 * 描述：
 */
public class SheetAdapter extends BaseAdapter {

    private List<Sheet> sheetList;
    private SheetActivity sheetActivity;

    private LayoutInflater mInflater;
    private String dirName;

    public SheetAdapter(SheetActivity activity){
        super();
        this.sheetActivity = activity;
        mInflater = (LayoutInflater) sheetActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void initSheetList(String dir){
        this.dirName = dir;
        sheetList = ProductionService.getSheetListByDir(dir);
    }

    @Override
    public int getCount() {
        return sheetList.size();
    }

    @Override
    public Object getItem(int i) {
        return sheetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            view = mInflater.inflate(R.layout.production_sheet_main_pageritem, null);
            Typeface typeface = Typeface.createFromAsset(sheetActivity.getAssets(), "trends.ttf");
            holder = new ViewHolder();
            holder.titleText = view.findViewById(R.id.production_sheet_main_pageritem_sname);
            holder.signText = view.findViewById(R.id.production_sheet_main_pageritem_sign);
            holder.sheetImage = view.findViewById(R.id.production_sheet_main_pageritem_image);
            holder.descText = view.findViewById(R.id.production_sheet_main_pageritem_desc);
            holder.pageNoText = view.findViewById(R.id.production_sheet_main_pageritem_pageno);

            holder.titleText.setTypeface(typeface);
            holder.titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 64);

            holder.signText.setTypeface(typeface);
            holder.signText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 64);

            holder.descText.setTypeface(typeface);
            holder.descText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

            holder.pageNoText.setTypeface(typeface);
            holder.pageNoText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 56);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Sheet sheet = (Sheet)getItem(i);
        holder.titleText.setText(sheet.getTitle());
        holder.signText.setText(sheet.getPageSign());
        holder.sheetImage.setImageBitmap(sheet.getBitmap());
        holder.descText.setText(sheet.getDescription());
        holder.pageNoText.setText(sheet.getPageNumber() + "");
        return view;
    }

    private class ViewHolder {
        TextView titleText;
        ImageView sheetImage;
        TextView signText;
        TextView descText;
        TextView pageNoText;
    }
}

package com.zhl.production.data;

import android.graphics.Bitmap;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/24 0024
 * 描述：
 * {"index":1,"pageNumber":1,"title":"第一节 手臂搭建","pageSign":"1-1","marginBottom":50,"bit":"1.jpg"}
 */
public class Sheet {

    public int getIndex() {
        return index;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getPageSign() {
        return pageSign;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setIndex(int index) {
        this.index = index;

    }


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPageSign(String pageSign) {
        this.pageSign = pageSign;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    private int index;
    private int pageNumber;
    private String title;
    private String pageSign;
    private int marginBottom;
    private Bitmap bitmap;
    private String image;

    private String dir;

    private String description;

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

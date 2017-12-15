package com.zhl.production.data;

import android.graphics.Bitmap;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/22 0022
 * 描述：
 */
public class Production {
    private String worksname;
    private String version;
    private String dirName;
    private Bitmap bitmap;

    private boolean isNew = false;


    public void setNew() {
        this.isNew = true;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setWorksname(String worksname) {
        this.worksname = worksname;
    }

    public String getWorksname() {
        return worksname;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirName() {
        return dirName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null ||  !(obj instanceof Production)) return false;

        return dirName.equals(((Production)obj).getDirName());
    }
}

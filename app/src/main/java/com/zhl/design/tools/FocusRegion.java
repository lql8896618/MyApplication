package com.zhl.design.tools;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/7 0007
 * 描述：
 */
public class FocusRegion {
    private int x;
    private int y;
    private int width;
    private int height;
    private int type;


    public FocusRegion(int x, int y, int width, int height, int type){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public int isFocus(int xpos, int ypos){
        if(xpos > x && xpos < x + width && ypos > y && ypos < y + height) return type;
        return  -1;
    }
}

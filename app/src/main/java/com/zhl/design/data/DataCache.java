package com.zhl.design.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhl.common.ZHLApplication;
import com.zhl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/10/20 0020
 * 描述：
 */
public class DataCache {



    public static final List<Bitmap> PORT_IMAGE_LIST = new ArrayList<Bitmap>();

    public static void init(){
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_1));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_2));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_3));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_4));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_5));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_6));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_7));
        PORT_IMAGE_LIST.add(BitmapFactory.decodeResource(ZHLApplication.applicationContextHandle.getResources(), R.drawable.design_node_icon_port_8));
    }

    public static void destroy(){
        PORT_IMAGE_LIST.clear();
        System.gc();
    }
}

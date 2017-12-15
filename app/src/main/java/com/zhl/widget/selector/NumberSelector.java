package com.zhl.widget.selector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.zhl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/10/21 0021
 * 描述：
 */
public class NumberSelector extends ImageSelector {
    public NumberSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        List<Bitmap> data = new ArrayList<Bitmap>();
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_0));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_1));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_2));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_3));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_4));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_5));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_6));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_7));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_8));
        data.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.design_node_prop_number_9));
        setData(data);
    }

}

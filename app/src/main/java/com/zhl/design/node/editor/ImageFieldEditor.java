package com.zhl.design.node.editor;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zhl.design.node.base.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQL on 2017/12/15.
 * 描述：
 */
public class ImageFieldEditor extends FieldEditor implements View.OnClickListener {

    private List<ImageView> imageViewList;

    private int[] defaultImages;
    private int[] checkImages;

    public ImageFieldEditor(Context context, String labelText, int[] defaultImages, int[] checkedImages, Node.Region imageSize) {
        super(context, labelText);

        this.defaultImages = defaultImages;
        this.checkImages = checkedImages;

        imageViewList = new ArrayList<>();
        for(int i = 0; i < defaultImages.length; i++){
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LayoutParams(imageSize.width, imageSize.height));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(defaultImages[i]);
            imageView.setTag(new Integer(i));
            addContent(imageView);
            imageViewList.add(imageView);
            imageView.setOnClickListener(this);
        }
    }

    public void setChecked(int index){
        imageViewList.get(index).setImageResource(checkImages[index]);
    }



    @Override
    public void onClick(View v) {
        Integer index = (Integer) v.getTag();
        if(valueChangeListener != null) valueChangeListener.changeValue(index, ValueChangeListener.ValueType_Image);

    }
}

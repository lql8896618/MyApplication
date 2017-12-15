package com.zhl.widget.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zhl.R;

import static com.zhl.design.data.DataCache.PORT_IMAGE_LIST;

/**
 * 作者：刘启亮
 * 创建时间： 2017/10/20 0020
 * 描述：
 */
public class PortSelector extends ImageSelector {

    public PortSelector(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.PortSelector);
        int minPortIndex = typedArray.getInt(R.styleable.PortSelector_minPortIndex, 0);
        int maxPortIndex = typedArray.getInt(R.styleable.PortSelector_maxPortIndex, 7);
        if(minPortIndex < 0) minPortIndex = 0;
        if(maxPortIndex > 7) maxPortIndex = 7;
        setData(PORT_IMAGE_LIST.subList(minPortIndex, maxPortIndex));
    }

}

package com.zhl.common;

import com.zhl.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/8.
 */
public class Cache {
    public static Map<Integer,Integer> beginBtnSort = new HashMap<Integer,Integer>();
    static {
        beginBtnSort.put(R.id.btn_begin_run,1);
        beginBtnSort.put(R.id.btn_begin_program,2);
        beginBtnSort.put(R.id.btn_begin_manual,3);
    }
}

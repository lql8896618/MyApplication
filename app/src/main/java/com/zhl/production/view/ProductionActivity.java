package com.zhl.production.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhl.R;
import com.zhl.base.view.BaseActivity;
import com.zhl.common.ServerUtil;
import com.zhl.common.ZHLApplication;
import com.zhl.production.data.Production;
import com.zhl.production.service.ProductionService;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/2 0002
 * 描述：
 */
public class ProductionActivity extends BaseActivity {

    private List<SystemProductionView> systemProductions = new ArrayList<SystemProductionView>();
    private LinearLayout sysitemsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.production_main);

        sysitemsLayout = findViewById(R.id.production_main_sysitemlist_layout);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Production> list = new ArrayList<Production>();
                List<Production> localList = ProductionService.getLocalProductionList();
                try {
                    List<Production> serverList = ProductionService.getServerProductionList();
                    if(serverList == null) list.addAll(localList);
                    else{
                        int size = localList.size();
                        for(int j = size; j >= 0 ; j--){
                            if(!serverList.contains(localList.get(j))) localList.remove(j);
                        }

                        for(int i = 0; i < serverList.size(); i++){
                            Production sp = serverList.get(i);
                            if(localList.contains(sp)){
                                Production lp = localList.get(localList.indexOf(sp));
                                if(!lp.getVersion().equals(sp.getVersion()))
                                    sp.setNew();
                            }
                        }
                        list.addAll(serverList);

                    }
                } catch (ServerUtil.ServerConnectFaildException e) {
                    e.printStackTrace();
                    list.addAll(localList);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductionActivity me = ProductionActivity.this;
                        LinearLayout inner_layout = null;
                        for(int i = 0; i < list.size(); i++){
                            if(i % 2 == 0){
                                inner_layout = new LinearLayout(me);
                                sysitemsLayout.addView(inner_layout);
                            }

                        }
                    }
                });

            }
        }).start();
    }

    private class SystemProductionView extends FrameLayout{

        private ImageView coverImage;
        private TextView nameText;
        private ImageView buildImageButton;
        private ImageView flagImage;
        private float scale = 1.33333f;
        private int covertWidth = 0;
        private int coverHeight = 0;
        private int limit = 8;

        public SystemProductionView(Context context, Production production) {
            super(context);

            covertWidth = ZHLApplication.widthScreen / 2 - (limit * 3);
            coverHeight = (int)(covertWidth / scale);

            FrameLayout.LayoutParams layoutParams = new LayoutParams(covertWidth, coverHeight);
            coverImage = new ImageView(context);
            coverImage.setLayoutParams(layoutParams);
            coverImage.setScaleType(ImageView.ScaleType.FIT_XY);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
            layoutParams.topMargin = 16;
            layoutParams.rightMargin = 16;
            flagImage = new ImageView(context);
            flagImage.setLayoutParams(layoutParams);
            flagImage.setImageResource(R.drawable.production_main_sys_new);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            layoutParams.rightMargin = 16;
            layoutParams.bottomMargin = 16;
            buildImageButton = new ImageView(context);
            buildImageButton.setLayoutParams(layoutParams);
            buildImageButton.setImageResource(R.drawable.production_main_sys_build);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            layoutParams.bottomMargin = 16;
            nameText = new TextView(context);
            nameText.setText("标题");
            nameText.setLayoutParams(layoutParams);

            addView(coverImage);
            addView(flagImage);
            addView(buildImageButton);
            addView(nameText);

            setLayoutParams(new LinearLayout.LayoutParams(covertWidth, coverHeight + 96));
        }
    }
}

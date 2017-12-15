package com.zhl.production.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhl.R;
import com.zhl.common.ServerUtil;
import com.zhl.production.data.Production;
import com.zhl.production.service.ProductionService;
import com.zhl.production.view.SheetActivity;

import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/21 0021
 * 描述：
 */
public class ProductionListDialog extends Dialog implements View.OnClickListener {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private List<Production> productionList;


    public ProductionListDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.y = 20;
        dialogWindow.setAttributes(lp);
        setContentView(R.layout.production_dlg_list_product);

        image1 = findViewById(R.id.production_dlg_productlist_image_1);
        image2 = findViewById(R.id.production_dlg_productlist_image_2);
        image3 = findViewById(R.id.production_dlg_productlist_image_3);
        image4 = findViewById(R.id.production_dlg_productlist_image_4);
        button1 = findViewById(R.id.production_dlg_productlist_button_1);
        button2 = findViewById(R.id.production_dlg_productlist_button_2);
        button3 = findViewById(R.id.production_dlg_productlist_button_3);
        button4 = findViewById(R.id.production_dlg_productlist_button_4);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProductionService.syncProductions();
                }catch (ServerUtil.ServerConnectFaildException e){
                    //Toast.makeText(getContext(), "服务器连接失败", Toast.LENGTH_SHORT);
                }
                productionList = ProductionService.getLocalProductionList();

                if(productionList == null || productionList.size() != 4) return;
                image1.post(new Runnable() {
                    @Override
                    public void run() {
                        image1.setTag(0);
                        button1.setTag(0);
                        button1.setText(productionList.get(0).getWorksname());
                        image1.setImageBitmap(productionList.get(0).getBitmap());
                        image1.setOnClickListener(ProductionListDialog.this);
                        button1.setOnClickListener(ProductionListDialog.this);
                    }
                });

                image2.post(new Runnable() {
                    @Override
                    public void run() {
                        image2.setTag(1);
                        button2.setTag(1);
                        button2.setText(productionList.get(1).getWorksname());
                        image2.setImageBitmap(productionList.get(1).getBitmap());
                        image2.setOnClickListener(ProductionListDialog.this);
                        button2.setOnClickListener(ProductionListDialog.this);
                    }
                });

                image3.post(new Runnable() {
                    @Override
                    public void run() {
                        image3.setTag(2);
                        button3.setTag(2);
                        button3.setText(productionList.get(2).getWorksname());
                        image3.setImageBitmap(productionList.get(2).getBitmap());
                        image3.setOnClickListener(ProductionListDialog.this);
                        button3.setOnClickListener(ProductionListDialog.this);
                    }
                });

                image4.post(new Runnable() {
                    @Override
                    public void run() {
                        image4.setTag(3);
                        button4.setTag(3);
                        button4.setText(productionList.get(3).getWorksname());
                        image4.setImageBitmap(productionList.get(3).getBitmap());
                        image4.setOnClickListener(ProductionListDialog.this);
                        button4.setOnClickListener(ProductionListDialog.this);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        String tag = view.getTag().toString();
        Integer index = Integer.parseInt(tag);
        Production p = productionList.get(index);
        Intent intent = new Intent(this.getContext(), SheetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("dirName", p.getDirName());
        bundle.putString("productionName", p.getWorksname());
        intent.putExtras(bundle);
//        dismiss();
        this.getContext().startActivity(intent);
    }
}

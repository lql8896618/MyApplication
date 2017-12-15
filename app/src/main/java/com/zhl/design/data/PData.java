package com.zhl.design.data;

import android.graphics.Bitmap;

import com.zhl.common.ZHLApplication;
import com.zhl.R;
import com.zhl.control.ProgramCache;
import com.zhl.control.codeRow.CodeRow;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/9 0009
 * 描述： 程序数据结构类，用于存储时持久化
 */
public class PData {

    private static int groupIndex = 0;

    public static final int FUNCTION_MAIN = 0;
    public static final int FUNCTION_1 = 0;
    public static final int FUNCTION_2 = 0;
    public static final int FUNCTION_3 = 0;
    public static final int FUNCTION_4 = 0;
    public static final int FUNCTION_5 = 0;
    public static final int FUNCTION_6 = 0;
    public static final int FUNCTION_7 = 0;
    public static final int FUNCTION_8 = 0;
    public static final int FUNCTION_9 = 0;

    private String id;
    private String name;
    private String createDate;
    private String updateDate;
    private ArrayList<NData> functions;
    private Bitmap icon;

    public PData(){
        functions = new ArrayList<NData>();
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
        functions.add(new NData("root", NData.NTYPE_COMPLEX).addChild(new NData("root_0", NData.NTYPE_CONTENT)));
    }

    public static PData createNewPData(String name){
        PData pData = new PData();
        pData.setId(ZHLApplication.createUUID());
        pData.setCreateDate(ZHLApplication.getCurrentDateTime());
        pData.setName(name);
        pData.setCreateDate(ZHLApplication.getCurrentDateTime());
        pData.setUpdateDate(ZHLApplication.getCurrentDateTime());
//        pData.setIcon(ZHLApplication.toBitmap(R.drawable.design_node_101_prop_dir_1));

        return pData;
    }

    public static int getGroupIndex() {
        return groupIndex++;
    }

    public static void initGroupIndex() {
        groupIndex = 0;
    }

    public void setFunction(NData nData, int index){
        functions.set(index, nData);
    }

    public ArrayList<NData> getFunctions() {
        return functions;
    }

    public NData getFunctionAt(int index){
        return functions.get(index);
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setFunctions(ArrayList<NData> functions) {
        this.functions = functions;
    }

    public ProgramCache toDownloadData(){
        ProgramCache cache = new ProgramCache();
//        for(int i = 0; i < functions.size(); i++){
//            NData root_data = functions.get(i);
//            NData root_content_data = root_data.getChildAt(0);
//            List<NData> child_datalist = root_content_data.getChilds();
//            for(int j = 0; j < child_datalist.size(); j++){
//                NData child_data = child_datalist.get(j);
//                List<CodeRow> crlist = child_data.toCodeRow();
//                for(CodeRow codeRow : crlist){
//                    cache.addRow(i, codeRow);
//                }
//            }
//        }
        return cache;
    }

    private void putCacheRow(ProgramCache cache, int index, NData root){

//        List<NData> d_content_list = root.getChilds();
//        for(int i = 0; i < d_content_list.size(); i++){
//            NData data = d_content_list.get(i);
//            List<CodeRow> crlist = data.toCodeRow();
//            for(CodeRow codeRow : crlist) cache.addRow(index, codeRow);
//        }
    }
}

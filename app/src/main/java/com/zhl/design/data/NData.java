package com.zhl.design.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：刘启亮
 * 创建时间： 2017/11/11 0011
 * 描述：
 */
public class NData implements Serializable {

    public static final int NTYPE_SIMPLE = 0;
    public static final int NTYPE_COMPLEX = 1;
    public static final int NTYPE_CONTENT = 2;

    public static final Integer VTYPE_NUMBER = 0;
    public static final Integer VTYPE_VARIABLE = 1;

    private transient String serialNo = "";
    private String code = "";
    private int nType = NTYPE_SIMPLE; //0 simpleNData, 1 complexNData   2 complexNData->content
    private ArrayList<NData> childs = new ArrayList<NData>();
    private ArrayList<PropertyData> values = new ArrayList<PropertyData>();

    public  NData(String code, int ntype){
        this("", code, ntype);
    }

    public NData(String serialNo, String code, int ntype){
        this.serialNo = serialNo;
        this.code = code;
        this.nType = ntype;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getCode() {
        return code;
    }

    public void setNType(int nType) {
        this.nType = nType;
    }

    public boolean isSimple(){
        return nType == NTYPE_SIMPLE;
    }

    public boolean isComplex(){
        return nType == NTYPE_COMPLEX;
    }

    public boolean isContent(){
        return nType == NTYPE_CONTENT;
    }

    public NData addNumberProperty(int value){
        return addProperty(VTYPE_NUMBER, value);
    }

    public NData addVariableProperty(int value){
        return addProperty(VTYPE_VARIABLE, value);
    }

    public NData addProperty(int type, int value){
        PropertyData data = new PropertyData();
        data.type = type;
        data.value = value;
        values.add(data);
        return this;
    }

    public NData.PropertyData getPropertyData(int index){
        return values.get(index);
    }

    public int childCount(){
        return childs.size();
    }

    public NData addChild(NData nData){
        childs.add(nData);
        return this;
    }

    public NData getChildAt(int index){
        return childs.get(index);
    }

    public int getPropertyValue(int index){
        return values.get(index).getValue();
    }

    public int getPropertyType(int index){
        return values.get(index).getType();
    }

    public List<NData> getChilds(){
        return childs;
    }

    public static class PropertyData implements Serializable{
        private int type;
        private int value;

        public int getValue() {
            return value;
        }

        public int getType() {
            return type;
        }
    }

//    public List<CodeRow> toCodeRow(){
//        return toCodeRow(-1);
//    }
//
//    public List<CodeRow> toCodeRow(int groupIndex){
//        int group_index = groupIndex;
//        if(groupIndex == -1)
//            group_index = PData.getGroupIndex();
//        NodeDefined defined = NodeDefined.find(getCode());
//        List<CodeRow> codeRows = defined.convertToCodeRow(this, group_index);
//        return codeRows;
//    }
}

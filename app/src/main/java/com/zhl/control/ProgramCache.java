package com.zhl.control;

import com.zhl.control.codeRow.CodeRow;
import com.zhl.control.codeRow.LedTurnOn;
import com.zhl.control.codeRow.Parameter;
import com.zhl.control.codeRow.ParameterInteger;
import com.zhl.control.codeRow.ParameterVar;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ProgramCache {
    private Map<Integer,ArrayList<CodeRow>> programList = new HashMap<Integer,ArrayList<CodeRow>>();
    public void addRow(int subProgramIndex,CodeRow row){
        if(subProgramIndex>9)return;
        if(!programList.containsKey(subProgramIndex )){
            programList.put(subProgramIndex,new ArrayList<CodeRow>());
        }
        this.programList.get(subProgramIndex).add(row);
    }

    private void rowToArray(CodeRow row,byte[] array,int rowIndex){

        int funCode = row.getCode();
        int head = 4;
        array[head+rowIndex*11+0] = (byte)(funCode>>8);
        array[head+rowIndex*11+1] = (byte)(funCode);

        String params[] = row.getParameterNames();
        for(int i=0;i<params.length&&i<3;i++){
            String param = params[i];
            String methodName = "get"+param.substring(0,1).toUpperCase()+param.substring(1);
            try {
                Method m = row.getClass().getMethod(methodName,new Class<?>[] {});
                if(m.getReturnType()== ParameterInteger.class){
                    ParameterInteger paramObj = (ParameterInteger)m.invoke(row, new Object[]{});
                    if(paramObj==null)continue;
                    array[head+rowIndex*11+2+i] = 0;
                    array[head+rowIndex*11+5+i*2+0] = (byte)(paramObj.getValue()>>8);
                    array[head+rowIndex*11+5+i*2+1] = (byte)(paramObj.getValue());
                }else if(m.getReturnType()== ParameterVar.class){
                    ParameterVar paramObj = (ParameterVar)m.invoke(row, new Object[]{});
                    if(paramObj==null)continue;
                    array[head+rowIndex*11+2+i] = 1;
                    array[head+rowIndex*11+5+i*2+0] = (byte)(paramObj.getVarIndex()>>8);
                    array[head+rowIndex*11+5+i*2+1] = (byte)(paramObj.getVarIndex());
                }else{
                    Parameter paramObj = (Parameter)m.invoke(row, new Object[]{});
                    if(paramObj==null)continue;
                    if(paramObj.getParamType()==Parameter.PARAM_VALUE){
                        array[head+rowIndex*11+2+i] = 0;
                    }else{
                        array[head+rowIndex*11+2+i] = 1;
                    }
                    array[head+rowIndex*11+5+i*2+0] = (byte)(paramObj.getValue()>>8);
                    array[head+rowIndex*11+5+i*2+1] = (byte)(paramObj.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //每组20个包
    public byte[] getSubPak(int subProgramIndex,int groupIndex,boolean isLastProgramIndex){
        byte[] sub = new byte[226];
        sub[0] = 1;
        sub[1] = 2;
        sub[2] = (byte)subProgramIndex;
        sub[3] = (byte)groupIndex;
        ArrayList<CodeRow> rowList = this.programList.get(subProgramIndex);
        boolean isLastRow = false;
        for(int i=groupIndex*20;i<20;i++){
            if(i>=rowList.size()){
                isLastRow = true;
                break;
            }
            CodeRow row = rowList.get(i);
            rowToArray(row,sub,i);
        }

        //判断是否是程序的结尾
        if(isLastProgramIndex&&isLastRow){
            sub[224] = 1;
        }
        sub[225] = CommManage.getSum(sub,225);
        return sub;
    }

    public Map<Integer,ArrayList<CodeRow>> getProgramList(){
        return this.programList;
    }

    public int getPakCount(){
        int count = 0;
        Iterator<Integer> keySet = programList.keySet().iterator();
        while(keySet.hasNext()) {
            int programIndex = keySet.next();
            ArrayList<CodeRow> rowList = programList.get(programIndex);
            int groupCount = (rowList.size() % 20 == 0) ? (rowList.size() / 20) : (rowList.size() / 20 + 1);
            count += groupCount;
        }
        return count;
    }
}

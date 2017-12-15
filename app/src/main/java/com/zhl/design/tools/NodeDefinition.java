package com.zhl.design.tools;

import android.content.Context;

import com.zhl.R;
import com.zhl.common.ZHLApplication;
import com.zhl.control.codeRow.ConditionEnd;
import com.zhl.control.codeRow.ConditionIf;
import com.zhl.control.codeRow.MonitorSetup;
import com.zhl.control.codeRow.MonitorStart;
import com.zhl.control.codeRow.MonitorStop;
import com.zhl.control.codeRow.Parameter;
import com.zhl.control.codeRow.ParameterInteger;
import com.zhl.control.codeRow.ParameterVar;
import com.zhl.control.codeRow.SoundPlay;
import com.zhl.design.data.CodeRowConvertor;
import com.zhl.design.data.NData;
import com.zhl.design.node.base.Node;
import com.zhl.design.node.n1.N101;
import com.zhl.design.node.n1.N102;
import com.zhl.design.node.n3.N401IF;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 作者：刘启亮
 * 创建时间： 2017/12/13 0013
 * 描述：
 * 电机控制 101
 * 声音播放 102
 * 彩灯控制 103
 *
 * 灰度采集 201
 * 温度采集 202
 * 音量采集 203
 * 亮度采集 204
 * 超声测距 205
 * 按键检测 206
 *
 * 永久循环 301
 * 计次循环 302
 * 条件循环 303
 * 跳出循环 304
 * 继续循环 305
 * 调用函数 306
 * 跳出函数 307
 * 等待延时 308
 *
 * 变量判断 401
 * 大于判断 402
 * 大等判断 403
 * 小于判断 404
 * 小等判断 405
 * 等于判断 406
 * 非等判断 407
 * 区间判断 408
 * 定时判断 409
 *
 * 赋值 1001
 * 加法 1002
 * 减法 1003
 * 乘法 1004
 * 除法 1005
 * 求余 1006
 * 逻辑与 1007
 * 逻辑或 1008
 * 逻辑非 1009
 *
 */
public class NodeDefinition {

    public static final NodeDefinition N根节点 = new NodeDefinition(
            "根节点",
            "root",
            new NodeResources(
                    R.drawable.design_node_icon_101,
                    R.drawable.design_node_root,
                    new Node.Region(240, 140)),
            null, null, null
    );


    public static final NodeDefinition N101电机控制 = new NodeDefinition(
            "电机控制",
            "101",
            new NodeResources(
                    R.drawable.design_node_icon_101,
                    R.drawable.design_node_simple_blue,
                    new Node.Region(300, 180)),
            new NData("101", NData.NTYPE_SIMPLE)
                    .addNumberProperty(1)
                    .addNumberProperty(0)
                    .addNumberProperty(0)
                    .addNumberProperty(10)
                    .addNumberProperty(30),
            N101.class,
            new CodeRowConvertor() {
                @Override
                public void convert(NData nData, int group_index) {
                    ParameterInteger port_param = new ParameterInteger(nData.getPropertyValue(0));
                    Parameter seed_param = new Parameter(nData.getPropertyType(4), nData.getPropertyValue(4));
                    if (nData.getPropertyValue(1) == 4 || nData.getPropertyValue(1) == 5) {
                        MonitorStop monitorStop = new MonitorStop(port_param, new Parameter(Parameter.PARAM_VALUE, nData.getPropertyValue(1) == 4 ? 0 : 1));
                        add(monitorStop);
                    } else {

                        Parameter dir_param = null;
                        if (nData.getPropertyValue(1) == 0 || nData.getPropertyValue(1) == 1) {
                            dir_param = new Parameter(Parameter.PARAM_VALUE, 1);
                        } else if (nData.getPropertyValue(1) == 2 || nData.getPropertyValue(1) == 3) {
                            dir_param = new Parameter(Parameter.PARAM_VALUE, 2);
                        }
                        MonitorSetup monitorSetup = new MonitorSetup(port_param, dir_param, seed_param);

                        Parameter value_param = new Parameter(nData.getPropertyType(3), nData.getPropertyValue(3));
                        Parameter model_param = null;
                        if (nData.getPropertyValue(2) == 0) {
                            model_param = new Parameter(Parameter.PARAM_VALUE, 1);
                        } else if (nData.getPropertyValue(2) == 1) {
                            if (nData.getPropertyValue(1) == 0 || nData.getPropertyValue(1) == 2) {
                                model_param = new Parameter(Parameter.PARAM_VALUE, 2);
                            } else if (nData.getPropertyValue(1) == 1 || nData.getPropertyValue(1) == 3) {
                                model_param = new Parameter(Parameter.PARAM_VALUE, 3);
                            }
                        } else if (nData.getPropertyValue(2) == 3) {
                            model_param = new Parameter(Parameter.PARAM_VALUE, 4);
                        }

                        MonitorStart monitorStart = new MonitorStart(port_param, model_param, value_param);
                        add(monitorSetup);
                        add(monitorStart);
                    }
                }
            }
    );

    public static final NodeDefinition N102声音播放 = new NodeDefinition("声音播放", "102",
            new NodeResources(
                    R.drawable.design_node_icon_102,
                    R.drawable.design_node_simple_blue,
                    new Node.Region(240, 150)),
            new NData("104", NData.NTYPE_SIMPLE)
                    .addNumberProperty(0)
                    .addNumberProperty(1)
                    .addNumberProperty(0),
            N102.class,
            new CodeRowConvertor() {
                @Override
                public void convert(NData nData, int group_index) {
                    SoundPlay soundPlay = new SoundPlay(
                            new Parameter(nData.getPropertyType(0), nData.getPropertyValue(0)),
                            new Parameter(nData.getPropertyType(1), nData.getPropertyValue(1)),
                            new Parameter(nData.getPropertyType(2), nData.getPropertyValue(2))
                    );
                    add(soundPlay);
                }
            }
    );

    public static final NodeDefinition N103彩灯控制 = new NodeDefinition(
            "彩灯控制",
            "103",
            new NodeResources(
                    R.drawable.design_node_icon_103,
                    R.drawable.design_node_simple_blue,
                    new Node.Region(300, 180)),
            new NData("103", NData.NTYPE_SIMPLE)
                    .addNumberProperty(1)
                    .addNumberProperty(0)
                    .addNumberProperty(0)
                    .addNumberProperty(10)
                    .addNumberProperty(30),
            N101.class,
            new CodeRowConvertor() {
                @Override
                public void convert(NData nData, int group_index) {
                    ParameterInteger port_param = new ParameterInteger(nData.getPropertyValue(0));
                    Parameter seed_param = new Parameter(nData.getPropertyType(4), nData.getPropertyValue(4));
                    if (nData.getPropertyValue(1) == 4 || nData.getPropertyValue(1) == 5) {
                        MonitorStop monitorStop = new MonitorStop(port_param, new Parameter(Parameter.PARAM_VALUE, nData.getPropertyValue(1) == 4 ? 0 : 1));
                        add(monitorStop);
                    } else {

                        Parameter dir_param = null;
                        if (nData.getPropertyValue(1) == 0 || nData.getPropertyValue(1) == 1) {
                            dir_param = new Parameter(Parameter.PARAM_VALUE, 1);
                        } else if (nData.getPropertyValue(1) == 2 || nData.getPropertyValue(1) == 3) {
                            dir_param = new Parameter(Parameter.PARAM_VALUE, 2);
                        }
                        MonitorSetup monitorSetup = new MonitorSetup(port_param, dir_param, seed_param);

                        Parameter value_param = new Parameter(nData.getPropertyType(3), nData.getPropertyValue(3));
                        Parameter model_param = null;
                        if (nData.getPropertyValue(2) == 0) {
                            model_param = new Parameter(Parameter.PARAM_VALUE, 1);
                        } else if (nData.getPropertyValue(2) == 1) {
                            if (nData.getPropertyValue(1) == 0 || nData.getPropertyValue(1) == 2) {
                                model_param = new Parameter(Parameter.PARAM_VALUE, 2);
                            } else if (nData.getPropertyValue(1) == 1 || nData.getPropertyValue(1) == 3) {
                                model_param = new Parameter(Parameter.PARAM_VALUE, 3);
                            }
                        } else if (nData.getPropertyValue(2) == 3) {
                            model_param = new Parameter(Parameter.PARAM_VALUE, 4);
                        }

                        MonitorStart monitorStart = new MonitorStart(port_param, model_param, value_param);
                        add(monitorSetup);
                        add(monitorStart);
                    }
                }
            }
    );
//
//            灰度采集 201
//            温度采集 202
//            * 音量采集 203
//            * 亮度采集 204
//            * 超声测距 205
//            * 按键检测 206
//            *
//            * 永久循环 301
//            * 计次循环 302
//            * 条件循环 303
//            * 跳出循环 304
//            * 继续循环 305
//            * 调用函数 306
//            * 跳出函数 307
//            * 等待延时 308
//            *
//            * 变量判断 401
    public static final NodeDefinition N401变量判断 = new NodeDefinition(
            "变量判断",
            "401",
            new NodeResources(
                    R.drawable.design_node_icon_401,
                    R.drawable.design_node_complex_yellow,
                    new Node.Region(300, 180)
            ),
            new NData("401", NData.NTYPE_COMPLEX).addNumberProperty(1),
            N401IF.class,
            new CodeRowConvertor() {
                @Override
                public void convert(NData nData, int group_index) {
                    add(new ConditionIf(new ParameterVar(nData.getPropertyValue(0)), new ParameterInteger(group_index)));
                    NData content = nData.getChildAt(0);
                    for(int i = 0; i < content.childCount(); i++){
                        NData child_data = content.getChildAt(i);
//                        addAll(child_data.toCodeRow());
                    }
                    add(new ConditionEnd(new ParameterInteger(group_index)));
                }
            }

    );
//            * 大于判断 402
//            * 大等判断 403
//            * 小于判断 404
//            * 小等判断 405
//            * 等于判断 406
//            * 非等判断 407
//            * 区间判断 408
//            * 定时判断 409
//            *
//            * 赋值 1001
//            * 加法 1002
//            * 减法 1003
//            * 乘法 1004
//            * 除法 1005
//            * 求余 1006
//            * 逻辑与 1007
//            * 逻辑或 1008
//            * 逻辑非 1009





    private static final HashMap<String, NodeDefinition> map = new HashMap<String, NodeDefinition>();
    static {
        map.put(N101电机控制.code, N101电机控制);
        map.put(N102声音播放.code, N102声音播放);
        map.put(N401变量判断.code, N401变量判断);
    }

    public static NodeDefinition find(String code){
        return map.get(code);
    }

    public Node create(Context context){
        try {
            Constructor constructor = nodeClass.getConstructor(Context.class);
            Node node = (Node)constructor.newInstance(context);
            node.initProperties(defaultData);
            node.setId(ZHLApplication.createNodeID());
            return node;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String text;
    private String code;
    private NodeResources nodeResources;
    private NData defaultData;
    private Class nodeClass;
    private CodeRowConvertor convertor;

    public NodeDefinition(
            String text,
            String code,
            NodeResources nodeResources,
            NData defaultData,
            Class nodeClass,
            CodeRowConvertor convertor
    ){
        this.text = text;
        this.code = code;
        this.nodeResources = nodeResources;
        this.defaultData = defaultData;
        this.nodeClass = nodeClass;
        this.convertor = convertor;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }

    public NData getDefaultData() {
        return defaultData;
    }

    public Class<Node> getNodeClass() {
        return nodeClass;
    }

    public CodeRowConvertor getConvertor() {
        return convertor;
    }

    public NodeResources getNodeResources() {
        return nodeResources;
    }

    public static class NodeResources{
        private int iconResources; //菜单与节点上边的图标
        private int backgroundResource; //节点背景图
        private Node.Region region;

        public NodeResources(int iconResources, int backgroundResource, Node.Region region){
            this.iconResources=  iconResources;
            this.backgroundResource = backgroundResource;
            this.region = region;
        }

        public int getIconResources() {
            return iconResources;
        }

        public int getBackgroundResource() {
            return backgroundResource;
        }

        public Node.Region getRegion() {
            return region;
        }
    }
}

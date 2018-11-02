package com.gdsp.dev.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON的相关工具类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class JsonUtils {
	public static int count = 0;
	public static Boolean mapIconFlag = true;
	public static String[] dataIndexArr = null;
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
    /**
     * Json格式特殊字符处理
     * @param s 处理前字符
     * @return 处理后字符
     */
    public static String string2Json(String s) {
        if (s == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }
    /**
     * 根据list<VO>转换成TreeView能够识别的Json格式
     * @param listData	需要转换的list数据
     * @param displayName	树节点上显示的名称
     * @param id	节点ID
     * @return	返回TreeView能够识别的Json格式
     */
    @SuppressWarnings("rawtypes")
	public static String formatList2TreeViewJson(List<?> listData,Map<String,String> nodeAttr) {
    	if(listData.size()<=0){
    		return "";
    	}
    	JSONArray fromObject = JSONArray.fromObject(listData);
    	ListIterator listIterator = fromObject.listIterator();
    	Map<JSONObject,Integer> jsonMap = new LinkedHashMap<>();
    	List<String> innercodeList = new ArrayList<>();
    	Boolean iconFlag = false;
    	while(listIterator.hasNext()){
    		JSONObject currentNode = JSONObject.fromObject(listIterator.next());
    		String innercode = (String) currentNode.get("innercode");
    		JSONObject nodeJson = new JSONObject();
    		//节点ID
    		if(currentNode.get("id")!=null || currentNode.get(nodeAttr.get("id"))!=null)
    			nodeJson.put("id", currentNode.get(nodeAttr.get("id"))==null?currentNode.get("id"):currentNode.get(nodeAttr.get("id")));
    		//节点显示名称
    		nodeJson.put("text", currentNode.get(nodeAttr.get("text")));
    		//节点状态
    		StringBuilder sb = new StringBuilder();
    		if(currentNode.get("checked")!=null||currentNode.get(nodeAttr.get("checked"))!=null){
    			sb.append("checked: true,");
    		}
    		if(currentNode.get("disabled")!=null||currentNode.get(nodeAttr.get("disabled"))!=null)
    			sb.append("disabled: true,");
    		if(currentNode.get("expanded")!=null||currentNode.get(nodeAttr.get("expanded"))!=null)
    			sb.append("expanded: true,");
    		if(currentNode.get("selected")!=null||currentNode.get(nodeAttr.get("selected"))!=null)
    			sb.append("selected: true,");
    		if(sb.toString().endsWith(",")){
    			sb.substring(0, sb.length()-2);
    		}
    		nodeJson.put("state","{"+sb.toString().replaceAll("\"", "")+"}");
    		//能否被选中
			if (currentNode.get("selectable") != null || currentNode.get(nodeAttr.get("selectable")) != null)
				nodeJson.put("selectable",
						currentNode.get(nodeAttr.get("selectable")) == null ? currentNode
								.get("selectable") : currentNode.get(nodeAttr.get("selectable")));
			if (currentNode.get("tags") != null || currentNode.get(nodeAttr.get("tags")) != null)
				nodeJson.put("tags",
						currentNode.get(nodeAttr.get("tags")) == null ? currentNode
								.get("tags") : currentNode.get(nodeAttr.get("tags")));
			if (currentNode.get("href") != null || currentNode.get(nodeAttr.get("href")) != null)
				nodeJson.put("href",
						currentNode.get(nodeAttr.get("href")) == null ? currentNode
								.get("href") : currentNode.get(nodeAttr.get("href")));
			if (currentNode.get("icon") != null || currentNode.get(nodeAttr.get("icon")) != null){
				iconFlag = true;
				nodeJson.put("icon",
						currentNode.get(nodeAttr.get("icon")) == null ? currentNode
								.get("icon") : currentNode.get(nodeAttr.get("icon")));
				
			}else if(currentNode.get("isNode") != null&&"1".equals(currentNode.get("isNode"))){
				nodeJson.put("icon", "glyphicon glyphicon-file");
			}
			if (currentNode.get("selectedIcon") != null || currentNode.get(nodeAttr.get("selectedIcon")) != null)
				nodeJson.put("selectedIcon",
						currentNode.get(nodeAttr.get("selectedIcon")) == null ? currentNode
								.get("selectedIcon") : currentNode.get(nodeAttr.get("selectedIcon")));
			if (currentNode.get("color") != null || currentNode.get(nodeAttr.get("color")) != null)
				nodeJson.put("color",
						currentNode.get(nodeAttr.get("color")) == null ? currentNode
								.get("color") : currentNode.get(nodeAttr.get("color")));
			if (currentNode.get("backColor") != null || currentNode.get(nodeAttr.get("backColor")) != null)
				nodeJson.put("backColor",
						currentNode.get(nodeAttr.get("backColor")) == null ? currentNode
								.get("backColor") : currentNode.get(nodeAttr.get("backColor")));
			//自定义属性添加
            if(nodeAttr.get("customAttrs") != null && nodeAttr.get("customAttrs").toString().length()>0){
                String customAttrs = nodeAttr.get("customAttrs");
                String[] listAttrs = customAttrs.split(",");
                for(int j=0;j < listAttrs.length;j++){
                    String attr = listAttrs[j];
                    nodeJson.put(attr,currentNode.get(attr));
                }
            }
            
    		jsonMap.put(nodeJson,null);    // 先不定层级
    		innercodeList.add(innercode);    // 同步生成innercode列表
    	}
    	// 设置显示层级
    	int innerCodeOrder = 0;
    	for(Entry<JSONObject,Integer> entry : jsonMap.entrySet()) {
    		String curtInnercode = innercodeList.get(innerCodeOrder);
    		int level = getLevelByJsonMapAndInnerCode(innercodeList,curtInnercode);
    		entry.setValue(level);
    		innerCodeOrder++;
    	}
    	
    	
    	//如果自己手动设置了节点的ICON，则不增加默认
    	if(!iconFlag){
    		setDefaultLastnodeIcon(jsonMap);
    	}
    	return getJsonStringFromMap(jsonMap);
    }
    
    /**
     * 将list日期格式转换成网格数据
    * @Title: formatDDate2String
    * (将list日期格式转换成字符串)
    * @param listData
    * @param colModelMap
    * @return    参数说明
     */
    private static Object formatDDateToString(Map<String,String> colModelMap,Object obj,List<Field> fieldList,String index){
        
        try {
            for( Field f:fieldList ){  
                String name = f.getName();//获取属性名称
                //将属性名称首字母大写方便获取值和设置值
                if(name.equals(index)){
                    name = name.substring(0, 1).toUpperCase()+name.substring(1);
                    String type = f.getGenericType().toString();//获取属性类型
                    Method m = obj.getClass().getMethod("get"+name);
                    //日期类型解析字符串
                    if(type.equals("class com.gdsp.dev.base.lang.DDate")){
                        DDate value = (DDate)m.invoke(obj);
                        if(null != value){
                            return value.toString();
                        }else{
                            return "";
                        }
                    }else if(type.equals("class com.gdsp.dev.base.lang.DDateTime")){
                        DDateTime value = (DDateTime)m.invoke(obj);
                        if(null != value){
                            return value.toString();
                        }else{
                            return "";
                        }
                    }else{//非日期类型直接返回属性值
                        Object value = m.invoke(obj);
                        if(null != value){
                            return value;
                        }else{
                            return "";
                        }
                    }
                }
                
            }  
        } catch (NoSuchMethodException e) {
            logger.error("请检查您的实体类get方法是否与属性名相对应！",e);
        } catch (SecurityException e) {
            logger.error(e.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }
    
    /**
     * 将list<VO>数据转换成pqGrid可识别的json格式
     * @param listData
     * @param colModelMap
     * @return
     */
    public static String formatListToPqgridJson(List<?> listData , Map<String,String> colModelMap) {
        
        String titles;
        String dataIndxes;
        String[] titleArray = null;
        String[] dataIndxArray = null;
        if(colModelMap!=null && colModelMap.size()>0){
            titles = colModelMap.get("title");
            dataIndxes = colModelMap.get("dataIndx");
            titleArray = titles.split(",");
            dataIndxArray = dataIndxes.split(",");
            dataIndexArr = dataIndxArray;
        }
        
        //最终返回的网格数据
        List<Map<String,Object>>gridData = new ArrayList<>();
        //VO实体类对应index显示数据map
        Map<String,Object> gridObj = null;
        
        //选取列index对应的数据进行处理，并将date类型数据解析成字符串
        if(listData != null){
            for(Object obj:listData){
                //网格单元行对象
                gridObj = new HashMap<>();
                
                //java反射获取Object父类外的所有声明属性(获取继承自审计父类的属性值)
                List<Field> fieldList = new ArrayList<>();
                Class<?> tempClass = obj.getClass();
                while( tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object") ){//当父类为null时，说明达到了最上层的父类（object）
                    fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                    tempClass = tempClass.getSuperclass();//得到父类，赋值给自己
                }
                //唯一标识id（index中有无id均会赋值，用于网格单元行标识）
                try {
                    /*通过反射获取id值*/
                    for( Field f:fieldList ){  
                        String name = f.getName();//获取属性名称
                        //将属性名称首字母大写方便获取值
                        if(name.equals("id")){
                            name = name.substring(0, 1).toUpperCase()+name.substring(1);
                            Method m = obj.getClass().getMethod("get"+name);
                            Object value = m.invoke(obj);
                            if(null != value){
                                gridObj.put("id", value);
                            }else{
                                gridObj.put("id", "");
                            }
                        }
                    }  
                } catch (NoSuchMethodException e) {
                    logger.error("请检查您的实体类get方法是否与属性名相对应！",e);
                } catch (SecurityException e) {
                    logger.error(e.getMessage(),e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(),e);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage(),e);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage(),e);
                }
                /*通过反射获取dataIndx相对应的列值*/
                for(String indx:dataIndexArr){
                    Object value = formatDDateToString(colModelMap,obj,fieldList,indx);
                    gridObj.put(indx, value);
                }
                gridData.add(gridObj);//填充进网格数据List
            }
        }
        
        /* 数据请求(一次性获取表头及数据)
         * 列模型
         * 可选设置属性*/
        String[] width = null;                      //列宽
        String[] align = null;                      //网格是否水平居中
        String[] halign = null;                     //表头是否水平居中
        String[] resizable = null;                  //是否单列可调整大小
        String[] sortable = null;                   //是否单列可排序
        String[] dataTypeArray = null;              //数据类型用于排序，默认string类型排序
        String minWidth = "";                       //列最小宽度
        String maxWidth = "";                       //列最大宽度
        if(null != colModelMap.get("width")){
            width = colModelMap.get("width").split(",");
        }
        if(null != colModelMap.get("align")){
            align = colModelMap.get("align").split(",");
        }
        if(null != colModelMap.get("halign")){
            halign = colModelMap.get("halign").split(",");
        }
        if(null != colModelMap.get("resizable")){
            resizable = colModelMap.get("resizable").split(",");
        }
        if(null != colModelMap.get("sortable")){
            sortable = colModelMap.get("sortable").split(",");
        }
        if(null != colModelMap.get("minWidth")){
            minWidth = colModelMap.get("minWidth");
        }
        if(null != colModelMap.get("maxWidth")){
            maxWidth = colModelMap.get("maxWidth");
        }
        
        if(listData.size() > 0){
            dataTypeArray = judgeDataType(dataIndxArray , listData);
        }
        //列模型创建
        JSONArray colModelArray = new JSONArray();
        JSONObject colModelObj = new JSONObject();
        for(int i = 0 ; i < titleArray.length ; i++){
            colModelObj.put("title", titleArray[i]);
            colModelObj.put("dataIndx", dataIndxArray[i]);
            colModelObj.put("dataType", null != dataTypeArray ? dataTypeArray[i]:"string");
            colModelObj.put("width", null != width ? width[i]:798/dataIndxArray.length);
            colModelObj.put("align", null != align ? align[i]:"center");
            colModelObj.put("halign", null != halign ? halign[i]:"center");
            colModelObj.put("resizable", null != resizable ? DBoolean.valueOf(resizable[i]) : true);
            colModelObj.put("sortable", null != sortable ? DBoolean.valueOf(sortable[i]) : true);
            colModelObj.put("minWidth", StringUtils.isNotEmpty(minWidth)?minWidth:"100");//设置列最小宽度值，防止在列数过多时表头文字显示拥挤
            colModelObj.put("maxWidth",StringUtils.isNotEmpty(maxWidth)?maxWidth:"1000");//列最大宽度，默认1000
            colModelArray.add(colModelObj);
        }
        
        //数据模型创建
        JSONArray dataModelArray = new JSONArray();
        if(listData.size() > 0){
            dataModelArray = JSONArray.fromObject(gridData);
        }
        JSONObject dataObj = new JSONObject();
        dataObj.put("colModel", colModelArray);
        dataObj.put("dataModel", dataModelArray);
        return dataObj.toString();
    }
    
    /**
     * 判断列数据类型
     * @param dataIndxArray
     * @param listData
     */
    private static String[] judgeDataType(String[] dataIndxArray, List<?>listData) {
        //返回数据类型数组
        String[] dataTypeArray = new String[dataIndxArray.length];
        Object obj = listData.get(0);
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i = 0 ; i < fields.length; i++){ 
            Field f = fields[i]; 
            String name = f.getName();//获取属性名称
            String type = f.getGenericType().toString();//获取属性类型
            for(int j = 0 ; j < dataIndxArray.length ; j++){
                if(dataIndxArray[j].equals(name)){
                    if(type.equals("class java.lang.Integer")){
                        dataTypeArray[j] = "integer";
                    }else if(type.equals("class java.lang.Float")){
                        dataTypeArray[j] = "float";
                    }else if(type.equals("class java.util.Date")||type.equals("class com.gdsp.dev.base.lang.DDate")
                            ||type.equals("class com.gdsp.dev.base.lang.DDateTime")){
                        dataTypeArray[j] = "date";
                    }else{
                        dataTypeArray[j] = "string";
                    }
                }
            }
            
        }
        return dataTypeArray;
    }
    
    /**
     * 定位当前节点在树上的层级
     * @param innercodeList 树的所有innerCode列表
     * @param curtInnercode 当前节点的innerCode
     * @return
     */
    private static Integer getLevelByJsonMapAndInnerCode(List<String> innercodeList,String curtInnercode){
    	int level = curtInnercode.length() / 4;    // 给当前层级一个假定值(4为innercode的步进长度)
    	String fatherInnercode = curtInnercode.substring(0,curtInnercode.length() - 4);
    	if (innercodeList.contains(fatherInnercode)) {
    		level = getLevelByJsonMapAndInnerCode(innercodeList,fatherInnercode) + 1;    // 递归查找父的层级数
		}else {
			level = 1;    // 没有父级则默认为一级节点
		}
    	return level;
    }
    
    /**
     * 根据list<VO>转换成TreeView能够识别的Json格式
     * @param listData	需要转换的list数据
     * @param displayName	树节点上显示的名称
     * @param id	节点ID
     * @return	返回TreeView能够识别的Json格式
     */
    @SuppressWarnings("rawtypes")
	public static String formatMap2TreeViewJson(Map mapData,Map<String,String> nodeAttr) {
    	if(mapData.size()<=0){
    		return "";
    	}
    	List rootNodes = (List) mapData.get("__null_key__");
    	Map<JSONObject,Integer> jsonMap = new LinkedHashMap<JSONObject,Integer>();
    	getNode(mapData, rootNodes,jsonMap,nodeAttr);
    	//如果自己手动设置了节点的ICON，则不增加默认
    	if(mapIconFlag){
    		setDefaultLastnodeIcon(jsonMap);
    	}
    	return getJsonStringFromMap(jsonMap);
    }
    /**
     * 根据Map结构数据构建带层级的Map结构数据用于拼接json串
     * @param mapData
     * @param nodes
     * @param jsonMap
     * @param nodeAttr
     */
    @SuppressWarnings("rawtypes")
	private static void getNode(Map mapData,List nodes,Map<JSONObject,Integer> jsonMap,Map<String,String> nodeAttr){
    	count +=1;
    	for (int i = 0; i < nodes.size(); i++) {
    		JSONObject currentNode = JSONObject.fromObject(nodes.get(i));
    		JSONObject nodeJson = new JSONObject();
    		//节点ID
    		if(currentNode.get("id")!=null || currentNode.get(nodeAttr.get("id"))!=null)
    			nodeJson.put("id", currentNode.get(nodeAttr.get("id"))==null?currentNode.get("id"):currentNode.get(nodeAttr.get("id")));
    		//节点显示名称
    		if(currentNode.get("text")!=null || currentNode.get(nodeAttr.get("text"))!=null)
    			nodeJson.put("text", currentNode.get(nodeAttr.get("text"))==null?currentNode.get("text"):currentNode.get(nodeAttr.get("text")));
    		//节点状态
    		StringBuilder sb = new StringBuilder();
    		if(currentNode.get("checked")!=null || currentNode.get(nodeAttr.get("checked"))!=null){
    			if(currentNode.get(nodeAttr.get("checked"))!=null&&"y".equalsIgnoreCase(currentNode.get(nodeAttr.get("checked")).toString())){
    				sb.append("checked: true,");
    			}
    		}
    		if(currentNode.get("disabled")!=null || currentNode.get(nodeAttr.get("disabled"))!=null)
    			sb.append("disabled: true,");
    		if(currentNode.get("expanded")!=null || currentNode.get(nodeAttr.get("expanded"))!=null)
    			sb.append("expanded: true,");
    		if(currentNode.get("selected")!=null || currentNode.get(nodeAttr.get("selected"))!=null)
    			sb.append("selected: true,");
    		if(sb.toString().endsWith(",")){
    			sb.substring(0, sb.length()-2);
    		}
    		nodeJson.put("state","{"+sb.toString().replaceAll("\"", "")+"}");
    		//能否被选中
    		if(currentNode.get("selectable")!=null || currentNode.get(nodeAttr.get("selectable"))!=null)
    			nodeJson.put("selectable", currentNode.get(nodeAttr.get("selectable"))==null?currentNode.get("selectable"):currentNode.get(nodeAttr.get("selectable")));
    		if(currentNode.get("tags")!=null || currentNode.get(nodeAttr.get("tags"))!=null)
    			nodeJson.put("tags",currentNode.get(nodeAttr.get("tags"))==null?currentNode.get("tags"):currentNode.get(nodeAttr.get("tags")));
    		if(currentNode.get("href")!=null || currentNode.get(nodeAttr.get("href"))!=null)
    			nodeJson.put("href",currentNode.get(nodeAttr.get("href"))==null?currentNode.get("href"):currentNode.get(nodeAttr.get("href")));
    		if(currentNode.get("icon")!=null || currentNode.get(nodeAttr.get("icon"))!=null){
    			nodeJson.put("icon",currentNode.get(nodeAttr.get("icon"))==null?currentNode.get("icon"):currentNode.get(nodeAttr.get("icon")));
    			mapIconFlag = false;
    		}else if(currentNode.get("isNode") != null&&"1".equals(currentNode.get("isNode"))){
				nodeJson.put("icon", "glyphicon glyphicon-file");
			}
    		if(currentNode.get("selectedIcon")!=null || currentNode.get(nodeAttr.get("selectedIcon"))!=null)
    			nodeJson.put("selectedIcon",currentNode.get(nodeAttr.get("selectedIcon"))==null?currentNode.get("selectedIcon"):currentNode.get(nodeAttr.get("selectedIcon")));
    		if(currentNode.get("color")!=null || currentNode.get(nodeAttr.get("color"))!=null)
    			nodeJson.put("color",currentNode.get(nodeAttr.get("color"))==null?currentNode.get("color"):currentNode.get(nodeAttr.get("color")));
    		if(currentNode.get("backColor")!=null || currentNode.get(nodeAttr.get("backColor"))!=null)
    			nodeJson.put("backColor",currentNode.get(nodeAttr.get("backColor"))==null?currentNode.get("backColor"):currentNode.get(nodeAttr.get("backColor")));
    		//自定义属性添加
            if(nodeAttr.get("customAttrs") != null && nodeAttr.get("customAttrs").toString().length()>0){
                String customAttrs = nodeAttr.get("customAttrs");
                String[] listAttrs = customAttrs.split(",");
                for(int j=0;j < listAttrs.length;j++){
                    String attr = listAttrs[j];
                    nodeJson.put(attr,currentNode.get(attr));
                }
            }
    		
    		jsonMap.put(nodeJson, count);
			String key = (String)currentNode.get("id");
			if(mapData.get(key)!=null){
				getNode(mapData,(List)mapData.get(key),jsonMap,nodeAttr);
			}
			if(i==nodes.size()-1){
				count--;
			}
		}
    }
    /**
     * 根据拼接好的带层级关系的Map数据构造easytree识别的json格式数据
     * @param jsonMap
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static String getJsonStringFromMap(Map jsonMap){
    	Iterator<Entry<JSONObject, Integer>> iterator = jsonMap.entrySet().iterator();
    	Map<Integer,JSONObject> fatherNode = new LinkedHashMap<Integer,JSONObject>();
    	Map<Integer,JSONObject> jsonNode = new LinkedHashMap<Integer,JSONObject>();
    	int count=1;
    	while(iterator.hasNext()){
    		Entry<JSONObject, Integer> next = iterator.next();
    		if(next.getValue()==1){
    			if(fatherNode.get(1)!=null)
    				jsonNode.put(count++, fatherNode.get(1));
    			fatherNode.put(next.getValue(), next.getKey());
    		}else{
    			JSONObject jsonObject = fatherNode.get(next.getValue()-1);
    			JSONArray parentNodes = null;
    			if(jsonObject.get("nodes")!=null&&next.getValue()!=2){
    				parentNodes = (JSONArray) jsonObject.get("nodes");
    			}
    			for(int i=1;i<next.getValue();i++){
    				if(jsonObject.get("nodes")!=null&&i<next.getValue()-2&&parentNodes!=null){
    					JSONObject jsonObjectFor = (JSONObject) parentNodes.get(parentNodes.size()-1);
    					parentNodes = (JSONArray) jsonObjectFor.get("nodes");
    				}
    				if(i==next.getValue()-1){
    					if(parentNodes!=null){
    	    				JSONObject currentJsonObject = (JSONObject) parentNodes.get(parentNodes.size()-1);
    	    				if(currentJsonObject.get("nodes")!=null){
    	    					JSONArray currentJsonObjectArr = (JSONArray) currentJsonObject.get("nodes");
    	    					currentJsonObjectArr.add(next.getKey());
    	    					currentJsonObject.put("nodes", currentJsonObjectArr);
    	    				}else{
    	    					JSONArray nodesArray = new JSONArray();
    	    					nodesArray.add(next.getKey());
    	    					currentJsonObject.put("nodes", nodesArray);
    	    				}
    	    			}else{
    	    				if(jsonObject.get("nodes")!=null){
    	    					JSONArray jsonObjectArray = (JSONArray) jsonObject.get("nodes");
    	    					jsonObjectArray.add(next.getKey());
    	    					jsonObject.put("nodes", jsonObjectArray);
    	    				}else{
    	    					JSONArray nodesArray = new JSONArray();
    	    					nodesArray.add(next.getKey());
    	    					jsonObject.put("nodes", nodesArray);
    	    				}
    	    			}
    				}
    			}
    			fatherNode.put(next.getValue(), jsonObject);
    		}
    	}
    	jsonNode.put(count++, fatherNode.get(1));
    	JSONArray nodesArray = new JSONArray();
    	Iterator<Entry<Integer, JSONObject>> iterator2 = jsonNode.entrySet().iterator();
    	while (iterator2.hasNext()) {
			nodesArray.add(iterator2.next().getValue());
		}
		return nodesArray.toString();
    }
    /**
     * 设置默认末级节点图标
     * @param jsonMap
     */
    private static void setDefaultLastnodeIcon(Map<JSONObject,Integer> jsonMap){
    	Iterator<Entry<JSONObject, Integer>> setDefaultLastNodeIcon = jsonMap.entrySet().iterator();
		Iterator<Entry<JSONObject, Integer>> setDefaultLastNodeIconForNext = jsonMap.entrySet().iterator();
		if(setDefaultLastNodeIconForNext.hasNext())
			setDefaultLastNodeIconForNext.next();
		while(setDefaultLastNodeIcon.hasNext()){
			Entry<JSONObject, Integer> next = setDefaultLastNodeIcon.next();
			if(setDefaultLastNodeIconForNext.hasNext()){
				Entry<JSONObject, Integer> next2 = setDefaultLastNodeIconForNext.next();
				if(next.getValue()>=next2.getValue()){
					JSONObject key = next.getKey();
					if(key.get("")==null){
						key.put("icon", "glyphicon glyphicon-file");
					}
				}
			}else{
				JSONObject key = next.getKey();
				if(key.get("")==null){
					key.put("icon", "glyphicon glyphicon-file");
				}
			}
		}
    }
    
    /**
     * @Title: zTreeViewJsonAsync
     * @Description: 异步加载下根据list<VO>转换成zTreeView能够识别的Json格式 
     * @param listData 需要转换的list数据
     * @param nodeAttr
     * @return String  返回zTreeView能够识别的Json格式
     */ 
     @SuppressWarnings("rawtypes")
 	public static String zTreeViewJsonAsync(List<?> listData,Map<String,String> nodeAttr) {
     	if(listData.size()<=0){
     		return "";
     	}
     	JSONArray fromObject = JSONArray.fromObject(listData);
     	ListIterator listIterator = fromObject.listIterator();
     	List<JSONObject> nodejsons = new ArrayList<JSONObject>();
     	while(listIterator.hasNext()){
     		JSONObject currentNode = JSONObject.fromObject(listIterator.next());
     		JSONObject nodeJson = new JSONObject();
     		//节点ID
     		if(currentNode.get("id")!=null || currentNode.get(nodeAttr.get("id"))!=null)
     			nodeJson.put("id", currentNode.get(nodeAttr.get("id"))==null?currentNode.get("id"):currentNode.get(nodeAttr.get("id")));
     		//节点显示名称
     		nodeJson.put("name", currentNode.get(nodeAttr.get("name")));
     		//是否是父节点，不赋值时默认为true
     		if(currentNode.get("isParent")!=null&&currentNode.get("isParent").toString().length()>0)
     			nodeJson.put("isParent", currentNode.get("isParent"));
     		if(currentNode.get("isParent") == null || currentNode.get("isParent").toString().length() <= 0)
     			nodeJson.put("isParent", "true");
     		if(currentNode.get(nodeAttr.get("isParent"))!=null&&currentNode.get(nodeAttr.get("isParent")).toString().length()>0)
     			nodeJson.put("isParent", currentNode.get(nodeAttr.get("isParent")));
     		//节点状态
     		if(currentNode.get("checked")!=null&&currentNode.get("checked").toString().length()>0)
     			nodeJson.put("checked", currentNode.get("checked"));
     		if(currentNode.get(nodeAttr.get("checked"))!=null&&currentNode.get(nodeAttr.get("checked")).toString().length()>0)
     			nodeJson.put("checked", currentNode.get(nodeAttr.get("checked")));
     		if(currentNode.get("chkDisabled")!=null&&currentNode.get("chkDisabled").toString().length()>0)
     			nodeJson.put("chkDisabled", currentNode.get("chkDisabled"));
     		if(currentNode.get(nodeAttr.get("chkDisabled"))!=null&&currentNode.get(nodeAttr.get("chkDisabled")).toString().length()>0)
     			nodeJson.put("chkDisabled", currentNode.get(nodeAttr.get("chkDisabled")));
     		if(currentNode.get("chkEnabled")!=null&&currentNode.get("chkEnabled").toString().length()>0)
     			nodeJson.put("chkEnabled", currentNode.get("chkEnabled"));
     		if(currentNode.get(nodeAttr.get("chkEnabled"))!=null&&currentNode.get(nodeAttr.get("chkEnabled")).toString().length()>0)
     			nodeJson.put("chkEnabled", currentNode.get(nodeAttr.get("chkEnabled")));
     		if(currentNode.get("open")!=null&&currentNode.get("open").toString().length()>0)
     			nodeJson.put("open",currentNode.get("open")!=null);
     		if(currentNode.get(nodeAttr.get("open"))!=null&&currentNode.get(nodeAttr.get("open")).toString().length()>0)
     			nodeJson.put("open",currentNode.get(nodeAttr.get("open"))!=null);
     		if(currentNode.get(nodeAttr.get("nodeDisable"))!=null&&currentNode.get(nodeAttr.get("nodeDisable")).toString().length()>0)
     			nodeJson.put("nodeDisable",currentNode.get(nodeAttr.get("nodeDisable")));
     		if(currentNode.get("nodeDisable")!=null&&currentNode.get("nodeDisable").toString().length()>0)
     			nodeJson.put("nodeDisable",currentNode.get("nodeDisable"));
     		//设置自定义图标
     		if(currentNode.get(nodeAttr.get("iconUrl"))!=null&&currentNode.get(nodeAttr.get("iconUrl")).toString().length()>0)
     			nodeJson.put("iconUrl",currentNode.get(nodeAttr.get("iconUrl")));
     		if(currentNode.get("iconUrl")!=null&&currentNode.get("iconUrl").toString().length()>0)
     			nodeJson.put("iconUrl",currentNode.get("iconUrl"));
     		if(currentNode.get(nodeAttr.get("openUrl"))!=null&&currentNode.get(nodeAttr.get("openUrl")).toString().length()>0)
     			nodeJson.put("openUrl",currentNode.get(nodeAttr.get("openUrl")));
     		if(currentNode.get("openUrl")!=null&&currentNode.get("openUrl").toString().length()>0)
     			nodeJson.put("openUrl",currentNode.get("openUrl"));
     		if(currentNode.get(nodeAttr.get("closeUrl"))!=null&&currentNode.get(nodeAttr.get("closeUrl")).toString().length()>0)
     			nodeJson.put("closeUrl",currentNode.get(nodeAttr.get("closeUrl")));
     		if(currentNode.get("closeUrl")!=null&&currentNode.get("closeUrl").toString().length()>0)
     			nodeJson.put("closeUrl",currentNode.get("closeUrl"));
     		//自定义属性添加
     		if(nodeAttr.get("diyAttrs") != null && nodeAttr.get("diyAttrs").toString().length()>0){
      			String diyAttrs = nodeAttr.get("diyAttrs");
      			String[] listAttrs = diyAttrs.split(",");
      			for(int j=0;j < listAttrs.length;j++){
      				String attr = listAttrs[j];
      				nodeJson.put(attr,currentNode.get(attr));
      			}
      		}
     		nodejsons.add(nodeJson);
     	}
     	return nodejsons.toString();
     }
     
     /**
     * @Title: formatList2TreeViewJsonNoAsync
     * @Description: 非异步加载下根据list<VO>转换成zTreeView能够识别的Json格式 
     * @param listData 需要转换的list数据
     * @param nodeAttr
     * @return String   返回zTreeView能够识别的Json格式
     */ 
     @SuppressWarnings("rawtypes")
 	public static String formatList2ZTreeViewJsonNoAsync(List<?> listData,Map<String,String> nodeAttr) {
     	if(listData.size()<=0){
     		return "";
     	}
     	JSONArray fromObject = JSONArray.fromObject(listData);
     	ListIterator listIterator = fromObject.listIterator();
     	Map<JSONObject,Integer> jsonMap = new LinkedHashMap<JSONObject,Integer>();
     	List<String> parentNodeInnercode = new ArrayList<String>();
     	while(listIterator.hasNext()){
     		JSONObject currentNode = JSONObject.fromObject(listIterator.next());
     		String innercode = (String) currentNode.get("innercode");
     		int level = getLevelByJsonMapAndInnerCode(parentNodeInnercode,innercode);
     		JSONObject nodeJson = new JSONObject();
     		//节点ID
     		if(currentNode.get("id")!=null || currentNode.get(nodeAttr.get("id"))!=null)
     			nodeJson.put("id", currentNode.get(nodeAttr.get("id"))==null?currentNode.get("id"):currentNode.get(nodeAttr.get("id")));
     		//节点显示名称
     		nodeJson.put("name", currentNode.get(nodeAttr.get("name")));
     		//节点状态
     		if(currentNode.get("checked")!=null&&currentNode.get("checked").toString().length()>0)
     			nodeJson.put("checked", currentNode.get("checked"));
     		if(currentNode.get(nodeAttr.get("checked"))!=null&&currentNode.get(nodeAttr.get("checked")).toString().length()>0)
     			nodeJson.put("checked", currentNode.get(nodeAttr.get("checked")));
     		if(currentNode.get("chkDisabled")!=null&&currentNode.get("chkDisabled").toString().length()>0)
     			nodeJson.put("chkDisabled", currentNode.get("chkDisabled"));
     		if(currentNode.get(nodeAttr.get("chkDisabled"))!=null&&currentNode.get(nodeAttr.get("chkDisabled")).toString().length()>0)
     			nodeJson.put("chkDisabled", currentNode.get(nodeAttr.get("chkDisabled")));
     		if(currentNode.get("chkEnabled")!=null&&currentNode.get("chkEnabled").toString().length()>0)
     			nodeJson.put("chkEnabled", currentNode.get("chkEnabled"));
     		if(currentNode.get(nodeAttr.get("chkEnabled"))!=null&&currentNode.get(nodeAttr.get("chkEnabled")).toString().length()>0)
     			nodeJson.put("chkEnabled", currentNode.get(nodeAttr.get("chkEnabled")));
     		if(currentNode.get("open")!=null&&currentNode.get("open").toString().length()>0)
     			nodeJson.put("open",currentNode.get("open")!=null);
     		if(currentNode.get(nodeAttr.get("open"))!=null&&currentNode.get(nodeAttr.get("open")).toString().length()>0)
     			nodeJson.put("open",currentNode.get(nodeAttr.get("open"))!=null);
     		if(currentNode.get(nodeAttr.get("nodeDisable"))!=null&&currentNode.get(nodeAttr.get("nodeDisable")).toString().length()>0)
     			nodeJson.put("nodeDisable",currentNode.get(nodeAttr.get("nodeDisable")));
     		if(currentNode.get("nodeDisable")!=null&&currentNode.get("nodeDisable").toString().length()>0)
     			nodeJson.put("nodeDisable",currentNode.get("nodeDisable"));
     		//设置自定义图标
     		if(currentNode.get(nodeAttr.get("iconUrl"))!=null&&currentNode.get(nodeAttr.get("iconUrl")).toString().length()>0)
     			nodeJson.put("iconUrl",currentNode.get(nodeAttr.get("iconUrl")));
     		if(currentNode.get("iconUrl")!=null&&currentNode.get("iconUrl").toString().length()>0)
     			nodeJson.put("iconUrl",currentNode.get("iconUrl"));
     		if(currentNode.get(nodeAttr.get("openUrl"))!=null&&currentNode.get(nodeAttr.get("openUrl")).toString().length()>0)
     			nodeJson.put("openUrl",currentNode.get(nodeAttr.get("openUrl")));
     		if(currentNode.get("openUrl")!=null&&currentNode.get("openUrl").toString().length()>0)
     			nodeJson.put("openUrl",currentNode.get("openUrl"));
     		if(currentNode.get(nodeAttr.get("closeUrl"))!=null&&currentNode.get(nodeAttr.get("closeUrl")).toString().length()>0)
     			nodeJson.put("closeUrl",currentNode.get(nodeAttr.get("closeUrl")));
     		if(currentNode.get("closeUrl")!=null&&currentNode.get("closeUrl").toString().length()>0)
     			nodeJson.put("closeUrl",currentNode.get("closeUrl"));
     		//自定义属性添加
     		if(nodeAttr.get("diyAttrs") != null && nodeAttr.get("diyAttrs").toString().length()>0){
      			String diyAttrs = nodeAttr.get("diyAttrs");
      			String[] listAttrs = diyAttrs.split(",");
      			for(int j=0;j < listAttrs.length;j++){
      				String attr = listAttrs[j];
      				nodeJson.put(attr,currentNode.get(attr));
      			}
      		}
     		jsonMap.put(nodeJson,level);
     		parentNodeInnercode.add(innercode);
     	}
     	return getJsonStringFromMap(jsonMap);
     }
     /**
    * @Title: formatMap2ZTreeViewJsonNoAsync
    * @Description: 非异步加载下根据map<string,list<string,string>>转换成zTreeView能够识别的Json格式 
    * @param mapData
    * @param nodeAttr
    * @return    参数说明
    * @return String    返回值说明
    */ 
    @SuppressWarnings("rawtypes")
    	public static String formatMap2ZTreeViewJsonNoAsync(Map mapData,Map<String,String> nodeAttr) {
     	if(mapData.size()<=0){
     		return "";
     	}
     	List rootNodes = (List) mapData.get("__null_key__");
     	Map<JSONObject,Integer> jsonMap = new LinkedHashMap<JSONObject,Integer>();
     	getZTreeNode(mapData, rootNodes,jsonMap,nodeAttr);
     	return getJsonStringFromMap(jsonMap);
     }
     /**
      * @Title: getZTreeNode
      * @Description: 在使用ztree树控件时，根据Map结构数据构建带层级的Map结构数据用于拼接json串
      * @param mapData
      * @param nodes
      * @param jsonMap
      * @param nodeAttr    参数说明
      * @return void    返回值说明
      */ 
      @SuppressWarnings("rawtypes")
  	private static void getZTreeNode(Map mapData,List nodes,Map<JSONObject,Integer> jsonMap,Map<String,String> nodeAttr){
      	count +=1;
      	for (int i = 0; i < nodes.size(); i++) {
      		JSONObject currentNode = JSONObject.fromObject(nodes.get(i));
      		JSONObject nodeJson = new JSONObject();
      		//节点ID
      		if(currentNode.get("id")!=null || currentNode.get(nodeAttr.get("id"))!=null)
      			nodeJson.put("id", currentNode.get(nodeAttr.get("id"))==null?currentNode.get("id"):currentNode.get(nodeAttr.get("id")));
      		//节点显示名称
      		nodeJson.put("name", currentNode.get(nodeAttr.get("name")));
      		//节点状态
      		if(currentNode.get("checked")!=null&&currentNode.get("checked").toString().length()>0)
      			nodeJson.put("checked", currentNode.get("checked"));
      		if(currentNode.get(nodeAttr.get("checked"))!=null&&currentNode.get(nodeAttr.get("checked")).toString().length()>0)
      			nodeJson.put("checked", currentNode.get(nodeAttr.get("checked")));
      		if(currentNode.get("chkDisabled")!=null&&currentNode.get("chkDisabled").toString().length()>0)
      			nodeJson.put("chkDisabled", currentNode.get("chkDisabled"));
      		if(currentNode.get(nodeAttr.get("chkDisabled"))!=null&&currentNode.get(nodeAttr.get("chkDisabled")).toString().length()>0)
      			nodeJson.put("chkDisabled", currentNode.get(nodeAttr.get("chkDisabled")));
      		if(currentNode.get("chkEnabled")!=null&&currentNode.get("chkEnabled").toString().length()>0)
      			nodeJson.put("chkEnabled", currentNode.get("chkEnabled"));
      		if(currentNode.get(nodeAttr.get("chkEnabled"))!=null&&currentNode.get(nodeAttr.get("chkEnabled")).toString().length()>0)
      			nodeJson.put("chkEnabled", currentNode.get(nodeAttr.get("chkEnabled")));
      		if(currentNode.get(nodeAttr.get("nodeDisable"))!=null&&currentNode.get(nodeAttr.get("nodeDisable")).toString().length()>0)
      			nodeJson.put("nodeDisable",currentNode.get(nodeAttr.get("nodeDisable")));
      		if(currentNode.get("nodeDisable")!=null&&currentNode.get("nodeDisable").toString().length()>0)
      			nodeJson.put("nodeDisable",currentNode.get("nodeDisable"));
      		//设置自定义图标
      		if(currentNode.get(nodeAttr.get("iconUrl"))!=null&&currentNode.get(nodeAttr.get("iconUrl")).toString().length()>0)
      			nodeJson.put("iconUrl",currentNode.get(nodeAttr.get("iconUrl")));
      		if(currentNode.get("iconUrl")!=null&&currentNode.get("iconUrl").toString().length()>0)
      			nodeJson.put("iconUrl",currentNode.get("iconUrl"));
      		if(currentNode.get(nodeAttr.get("openUrl"))!=null&&currentNode.get(nodeAttr.get("openUrl")).toString().length()>0)
      			nodeJson.put("openUrl",currentNode.get(nodeAttr.get("openUrl")));
      		if(currentNode.get("openUrl")!=null&&currentNode.get("openUrl").toString().length()>0)
      			nodeJson.put("openUrl",currentNode.get("openUrl"));
      		if(currentNode.get(nodeAttr.get("closeUrl"))!=null&&currentNode.get(nodeAttr.get("closeUrl")).toString().length()>0)
      			nodeJson.put("closeUrl",currentNode.get(nodeAttr.get("closeUrl")));
      		if(currentNode.get("closeUrl")!=null&&currentNode.get("closeUrl").toString().length()>0)
      			nodeJson.put("closeUrl",currentNode.get("closeUrl"));
      		//自定义属性添加
      		if(nodeAttr.get("diyAttrs") != null && nodeAttr.get("diyAttrs").toString().length()>0){
      			String diyAttrs = nodeAttr.get("diyAttrs");
      			String[] listAttrs = diyAttrs.split(",");
      			for(int j=0;j < listAttrs.length;j++){
      				String attr = listAttrs[j];
      				nodeJson.put(attr,currentNode.get(attr));
      			}
      		}
      		jsonMap.put(nodeJson, count);
  			String key = (String)currentNode.get("id");
  			if(mapData.get(key)!=null){
  				getZTreeNode(mapData,(List)mapData.get(key),jsonMap,nodeAttr);
  			}
  			if(i==nodes.size()-1){
  				count--;
  			}
  		}
      }
}

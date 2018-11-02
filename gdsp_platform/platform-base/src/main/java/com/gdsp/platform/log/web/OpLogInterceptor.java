package com.gdsp.platform.log.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.entity.BaseEntity;
import com.gdsp.dev.persist.ext.MybatisObjectFactory;
import com.gdsp.dev.persist.ext.MybatisObjectWrapperFactory;
import com.gdsp.dev.persist.ext.SqlTemplateHelper;
import com.gdsp.platform.log.dao.ITempLogContentDao;
import com.gdsp.platform.log.dao.ITempLogOpDao;
import com.gdsp.platform.log.impl.LogAspect;
import com.gdsp.platform.log.model.TempLogContentVO;
import com.gdsp.platform.log.model.TempLogOpVO;
import com.gdsp.platform.log.utils.LogXmlAnalyzeUtils;
import com.gdsp.platform.log.utils.SqlParserUtil;

/**
 * 操作日志拦截器 <br/>
 * @author wcl
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class OpLogInterceptor implements Interceptor {
	/**
	 * 日志记录异常写入文件
	 */
	private static Logger logExFile = LoggerFactory.getLogger("logExcepFile");
	private static final Logger logger = LoggerFactory.getLogger(OpLogInterceptor.class);
    /**
     * 对象构造工厂
     */
    private static final ObjectFactory        DEFAULT_OBJECT_FACTORY         = new MybatisObjectFactory();
    /**
     * 对象包装工厂
     */
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new MybatisObjectWrapperFactory();
    /**
     * 操作临时表接口
     */
    private ITempLogOpDao    tempLogOpDao   =  null;
    /**
     * 操作字段临时表接口
     */
    private ITempLogContentDao   tempLogContentDao  =  null;
    
    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY);
     // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaObject.hasGetter("h")) {
            Object object = metaObject.getValue("h");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaObject.hasGetter("target")) {
            Object object = metaObject.getValue("target");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 将执行权交给下一个拦截器，此方法前为执行拦截，方法后为执行后执行
        Object rel=invocation.proceed();
        //拦截查询方法
        String type=mappedStatement.getSqlCommandType().toString();
        
        return judgeSqlOperation(metaObject, mappedStatement, rel, type);
    }

    /**
     * 判断sql操作（重构）
     * @param metaObject
     * @param mappedStatement
     * @param rel
     * @param type
     * @return
     * @throws Throwable
     */
    private Object judgeSqlOperation(MetaObject metaObject, MappedStatement mappedStatement, Object rel, String type) {
        //如果是插入操作
        if("insert".equalsIgnoreCase(type)){
        	try{
        		this.recordInsertOperation(metaObject,type);
        	}catch(Exception ex){
        		logExFile.info(ex.getMessage()+"拦截插入sql语句异常");
        		logger.debug(ex.getMessage(),ex);
        		return rel;
        	}
        //如果是更新操作
        }else if("update".equalsIgnoreCase(type)){
        	try{
        		this.recordUpdateOperation(metaObject,mappedStatement,type);
        	}catch(Exception ex){
        		logExFile.info(ex.getMessage()+"拦截更新sql语句异常");
        		logger.debug(ex.getMessage(),ex);
        		return rel;
        	}
        //如果是删除操作	
        }else if("delete".equalsIgnoreCase(type)){
        	try{
        		this.recordDeleteOperation(metaObject,mappedStatement,type);
        	}catch(Exception ex){
        		logExFile.info(ex.getMessage()+"拦截删除sql语句异常");
        		logger.debug(ex.getMessage(),ex);
        		return rel;
        	}
        }
        return rel;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */
    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
     */
    @Override
    public void setProperties(Properties properties) {
        // Do nothing because of parameter is undefined
    }

    /**
     * 获取接口
     * @return
     */
    public ITempLogOpDao getTempLogOpDao() {
        if (tempLogOpDao == null)
        	tempLogOpDao = AppContext.lookupBean(ITempLogOpDao.class);
        return tempLogOpDao;
    }
    
    /**
     * 获取接口
     * @return
     */
    public ITempLogContentDao getTempLogContentDao() {
        if (tempLogContentDao == null)
        	tempLogContentDao = AppContext.lookupBean(ITempLogContentDao.class);
        return tempLogContentDao;
    }
    
    /**
     * 记录插入数据的操作
     * @throws IOException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void recordInsertOperation(MetaObject metaObject,String type) throws IOException, IllegalAccessException, InvocationTargetException {
    	BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        //获取插入的sql语句
    	String insertSql = boundSql.getSql();
    	//获取插入语句的表名
    	String tname = this.getTableName(insertSql, type);
    	//插入记录的id，有可能是批量插入
    	List<String> idList = new ArrayList<>();
    	//判断该表是否需要记录字段变化
    	if(this.judgeInTables(tname)){
    		//获取参数值
    		Object parameterObj = boundSql.getParameterObject();
    		List<String> uuids = new ArrayList<>();
    		// 封装对象的list
            List<Object> objList = new ArrayList<>();
    		if(parameterObj instanceof BaseEntity){
    			//说明返回值中可以直接id
    			BaseEntity entity = (BaseEntity) parameterObj;
        		idList.add(entity.getId());
        		//插入操作
        		String optype = "1";
        		uuids = this.insertIntoTableTemp(idList,tname,optype);
        		String id = uuids.get(0);
    			this.insertIntoColumnTemp(parameterObj,id,tname);
    		}else if(parameterObj instanceof Map){
    			handleMapArguments(insertSql, tname, idList, parameterObj, objList);
    		}
    	}
    }

    /**
     * 对参数是map类型的处理
     * @param insertSql
     * @param tname
     * @param idList
     * @param parameterObj
     * @param objList
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void handleMapArguments(String insertSql, String tname, List<String> idList, Object parameterObj, List<Object> objList) throws IllegalAccessException, InvocationTargetException {
        List<String> uuids;
        Map<String,List> map = ( Map ) parameterObj;
        for(Map.Entry<String, List> entry : map.entrySet()){
            List list = entry.getValue();
            for(Object object : list){
                //获取sql中...
                objList.add(object);
            }
        }
        //参数是封装之后的
        String paramSql = insertSql.substring(insertSql.indexOf('(')+1,insertSql.indexOf(')'));
        String[] params = paramSql.split(",");
        //获取参数list
        List<String>  paramsList = Arrays.asList(params);
        List<Map<String,String>> objectList = new ArrayList<>();
        for (Object object : objList) {
        	Map<String,String>  parameterValue = new HashMap<>();
        	Method[] methods = object.getClass().getMethods();
        	for (Method method : methods) {
        		for (String column : paramsList) {
        			if(("get"+column).equalsIgnoreCase(method.getName())){
        				Object rsValue = method.invoke(object); 
        				if(rsValue == null){
        					parameterValue.put(column, null);
        				}else{
        					parameterValue.put(column, rsValue.toString());
        				}
        			}
        		}
        	}
        	objectList.add(parameterValue);
        }
        //得到数据id
        for (Map<String, String> parameterMap : objectList) {
        	idList.add(parameterMap.get("id"));
        }
        //插入操作
        String optype = "1";
        uuids = this.insertIntoTableTemp(idList,tname,optype);
        for(int i=0;i<uuids.size();i++){
        	for(int j=0;j<objectList.size();j++){
        		if(i == j){
        			String id = uuids.get(i);
            		String logId = id;
            		//需要记录变化的字段列表
                	Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
                	this.recordInsertContent(tableColumnMap, objectList, logId, j);
        		}
        	}
        }
    }
    
    /**
     * 记录更新数据的操作
     * @throws IOException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws ParseException 
     */
    private void recordUpdateOperation(MetaObject metaObject,MappedStatement mappedStatement,String type) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException   {
    	BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        //获取更新的sql语句
    	String updateSql = boundSql.getSql();
    	//将sql转化为小写，方便截取
    	updateSql = updateSql.toLowerCase();
    	//获取更新语句的表名
    	String tname = this.getTableName(updateSql, type);
        //截取sql语句，获取各字段数组
    	String result = SqlParserUtil.getParsedSql(updateSql);
    	String resultSql = result.trim();
    	String[] resultArray = resultSql.split("\n");
    	//更新记录的id，有可能是批量更新
    	List<String> idList = new ArrayList<>();
    	List<String> uuids;
    	//根据表名，判断该表是否需要记录
    	if(this.judgeInTables(tname)){
    		//获取参数值
    		Object parameterObj = boundSql.getParameterObject();
    		if(parameterObj instanceof BaseEntity){
    			//说明返回值中可以直接id
    			BaseEntity entity = (BaseEntity) parameterObj;
        		if(entity.getId() == null){
        			//说明更新的条件不包含id字段
        			//先获取id
        			Method[] parammethods = parameterObj.getClass().getMethods();
        			String[] paraArray = {"pk_role","pk_user"};
        			String queryCondition = "";
                	for (int i=0;i<paraArray.length;i++) {
                		for (Method method : parammethods) {
                			if(("get"+paraArray[i]).equalsIgnoreCase(method.getName())){
    							Object rsValue = method.invoke(parameterObj); 
    							if(i==0){
    								queryCondition += "where pk_role="+"\""+rsValue.toString()+"\"";
    							}else{
    								queryCondition += " and pk_user="+"\""+rsValue.toString()+"\""+"";
    							}
    						}
                		}
                	}
                	//拼接查询条件,查询记录id
                	List<String> updateIdList = getTempLogOpDao().queryFormerValueByCondition(tname,queryCondition);
                	idList.add(updateIdList.get(0));
        		}else{
        			idList.add(entity.getId());
        		}
        		//更新操作
        		String optype = "2";
    			uuids = this.insertIntoTableTemp(idList,tname,optype);
    			String logId = uuids.get(0);
    			String dataId = idList.get(0);
            	Map<String,String> oldValueMap;
            	//通过反射，找出旧记录字段值
            	oldValueMap = this.getOldValueMapById(mappedStatement, dataId, tname);
            	//获取需要记录变化的字段
        		Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
        		//获取设置的字段
        		List<String> needRecordColumn = this.getNeedRecordColumnList(resultArray, tname);
            	//通过反射，找出新记录的字段值
            	Map<String,String> newValueMap = new HashMap<>();
            	Method[] newmethods = parameterObj.getClass().getMethods();
            	for (String column : needRecordColumn) {
            		for (Method method : newmethods) {
            			if(("get"+column).equalsIgnoreCase(method.getName())){
							Object rsValue = method.invoke(parameterObj); 
							if(rsValue == null){
								newValueMap.put(column, null);
							}else if("getUpdate_pwd_time".equals(method.getName())){
								if(rsValue != null){
	        						long updatePwdTime = this.changeDateValue(rsValue.toString());
	        						newValueMap.put("update_pwd_time", Long.toString(updatePwdTime));
	        					}
							}else{
								newValueMap.put(column, rsValue.toString());
							}
						}
            			if("getAgingEndDate".equals(method.getName())){
        					Object rsValue = method.invoke(parameterObj); 
        					if(rsValue != null){
        						long agingTime = this.changeDateValue(rsValue.toString());
        						newValueMap.put("aging_enddate", Long.toString(agingTime));
        					}
        				}
            		}
            	}
            	this.recordUpdateContent(newValueMap, oldValueMap, tableColumnMap, logId);
    		}else if(parameterObj instanceof Map){
    			String queryParameter = "";
    			//截取sql，获取删除的条件
    			String queryCondition = updateSql.substring(updateSql.indexOf("where"), updateSql.length());
    			String lockedValue;
    			String lockedCondition = updateSql.substring(updateSql.indexOf('=')+1,updateSql.indexOf("where"));
    			lockedValue = lockedCondition.trim();
    			lockedValue = lockedValue.replace("\'", "");
    			Map<String,String[]> map = ( Map ) parameterObj;
    			for(Map.Entry<String, String[]> entry : map.entrySet()){
    			    String[] arr =  entry.getValue();
    			    for(int i = 0;i<arr.length;i++){
                        if(i!=arr.length-1){
                            queryParameter += "\""+arr[i]+"\""+",";
                        }else{
                            queryParameter += "\""+arr[i]+"\"";
                        }
                    }
    			}
        		queryCondition = queryCondition.replace("?", queryParameter);
        		//根据查询条件获取即将删除的数据id集合
        		List<String> updateIdList = getTempLogOpDao().queryFormerValueByCondition(tname,queryCondition);
        		String optype = "2";
        		uuids = this.insertIntoTableTemp(updateIdList,tname,optype);
        		for (int i=0;i<uuids.size();i++) {
        			for (int j=0;j<updateIdList.size();j++){
        				if(i==j){
        					String dataId = updateIdList.get(j);
        					String logId = uuids.get(i);
        					Map<String,String> oldValueMap;
                        	//通过反射，找出旧记录字段值
                        	oldValueMap = this.getOldValueMapById(mappedStatement, dataId, tname);
                        	//获取设置的字段
                    		Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
                    		//获取需要记录变化的字段
                    		List<String> needRecordColumn = this.getNeedRecordColumnList(resultArray, tname);
                        	//通过反射，找出新记录的字段值
                        	Map<String,String> newValueMap = new HashMap<>();
                        	Method[] newmethods = parameterObj.getClass().getMethods();
                        	for (String column : needRecordColumn) {
                        		for (Method method : newmethods) {
                        			if(("get"+column).equalsIgnoreCase(method.getName())){
            							Object rsValue = method.invoke(parameterObj); 
            							if(rsValue == null){
            								newValueMap.put(column, null);
            							}else{
            								newValueMap.put(column, rsValue.toString());
            							}
            						}
                        		}
                        	}
                        	if(newValueMap.size() == 0 && !"".equals(lockedValue)&&needRecordColumn.contains("islocked")){
                        		//说明参数直接写死在程序中
                        		newValueMap.put("islocked", lockedValue.toUpperCase());
                        	}
                        	this.recordUpdateContent(newValueMap, oldValueMap, tableColumnMap, logId);
        				}
        			}
        		}
        	}
		}
    }
    
    /**
     * 记录删除数据的操作
     * @throws IOException 
     * @throws ParseException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws Throwable 
     * 
     */
    private void recordDeleteOperation(MetaObject metaObject,MappedStatement mappedStatement,String type) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException  {
    	//如果是删除操作
    	BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        //获取sql语句
    	String deleteSql=boundSql.getSql();
    	//将sql转化为小写，方便截取
    	deleteSql = deleteSql.toLowerCase();
        //获取表名
    	String tname = this.getTableName(deleteSql, type);
    	//删除记录的id，有可能是批量删除
    	List<String> idList = new ArrayList<>();
    	List<String> uuids;
    	//根据表名，判断该表是否需要记录
    	if(this.judgeInTables(tname)){
    		Object parameterObj = boundSql.getParameterObject();
    		if(parameterObj instanceof String){
    			//说明返回值中可以直接id
    			String deleteId = (String) parameterObj;
        		idList.add(deleteId);
        		//删除操作
        		String optype = "3";
    			uuids = this.insertIntoTableTemp(idList,tname,optype);
    			String logId = uuids.get(0);
    			String dataId = idList.get(0);
    			Map<String,String> oldValueMap;
            	//旧值记录
            	oldValueMap = this.getOldValueMapById(mappedStatement, dataId, tname);
            	//需要记录的字段
            	Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
            	this.recordDeleteContent(tableColumnMap, oldValueMap, logId);
    		}else if(parameterObj instanceof Map){
    			List<String> ids = new ArrayList<>();
    			//原始sql语句的长度
            	int initSqlLength = deleteSql.length();
            	String sql= deleteSql.replace("?", "");
            	int changeSqlLength = sql.length();
            	//计算问号的个数
            	int mark = initSqlLength - changeSqlLength;
    			String queryParameter1 = "";
            	String queryParameter2 = "";
            	String dataDicParam1 = "";
            	String dataDicParam2 = "";
            	String dataDicParam3 = "";
            	List<String> deleteIds = new ArrayList<>();
        		//如果parameterObj是一个Map
        		Map<String,String[]> map = ( Map ) parameterObj;
        		if(map.size() == 1){
        		    for(Map.Entry<String, String[]> entry : map.entrySet()){
        		        String[] arr = entry.getValue();
        		        if(arr.length==1){
                            queryParameter1 = "\'"+arr[0]+"\'";
                        }else{
                            for(int i=0;i<mark;i++) {
                                if(i!=mark-1){
                                    queryParameter1 +=  "\'"+arr[i]+"\'"+",";
                                }else{
                                    queryParameter1 +=  "\'"+arr[i]+"\'";
                                }
                            }
                        }
        		    }
        		}
        		if(map.size() == 2){
        		    for(Map.Entry<String, String[]> entry : map.entrySet()){
        		        String[] arr = entry.getValue();
        		        if(arr.length==1){
                            queryParameter1 = "\'"+arr[0]+"\'";
                        }else{
                            for(int i=0;i<mark;i++) {
                                if(i!=mark-1){
                                    queryParameter1 +=  "\'"+arr[i]+"\'"+",";
                                }else{
                                    queryParameter1 +=  "\'"+arr[i]+"\'";
                                }
                            }
                        }
        		    }
        		}
        		if(map.size() == 4&& mark>=2){
        		    for(Map.Entry<String, String[]> entry : map.entrySet()){
        		        Object obj = entry.getValue();
        		        if(obj instanceof String){
                            queryParameter1 = obj.toString();
                        }
                        if(obj instanceof String[]){
                            String[] arr = entry.getValue();
                            if(arr.length==1){
                                queryParameter2 = "\'"+arr[0]+"\'";
                            }else{
                                for(int i=0;i<mark-1;i++) {
                                    if(i!=mark-2){
                                        queryParameter2 +=  "\'"+arr[i]+"\'"+",";
                                    }else{
                                        queryParameter2 +=  "\'"+arr[i]+"\'";
                                    }
                                }
                            }
                        }
                        if(obj instanceof List){
                            List<Object> objectList =  (List) obj;
                            for (Object object : objectList) {
                                if(object instanceof BaseEntity){
                                    BaseEntity bs = (BaseEntity) object;
                                    String queryId = bs.getId();
                                    ids.add(queryId);
                                }
                            }
                        }
        		    }
            	}
        		/**
        		 * 以下是我对下面代码的理解 wqh 2017年11月29日  --TODO 考虑更好的实现
        		 * 如果mybatis从dao接口里接收到的参数为6个（实际是3个），
        		 * 则将每个参数的值取到，并拼接成查询条件， 目的是为删除操作记录操作日志。
        		 * 但实际上这个方法目前发现应该是专门针对数据授权删除接口而写的？？
        		 */
        		boolean flag = false;
        		if(map.size() == 6&& mark>=2){
        		    for(Map.Entry<String, String[]> entry : map.entrySet()){
        		    	// 增加过滤条件，因为mybatis获取到的参数顺序不可预料
        		    	if (!StringUtils.startsWith(entry.getKey(), "param")) {
							continue;
						}
        		    	//--------------------------------------------------------//
        		        Object obj = entry.getValue();
        		        if(obj instanceof String){
                            if(!flag){
                                dataDicParam1 = obj.toString();
                                flag = true;
                            }else{
                                dataDicParam2 = obj.toString();
                            }
                        }
                        if(obj instanceof List){
                            List<Object> objectList =  (List) obj;
                            for (Object object : objectList) {
                                String queryId = object.toString();
                                deleteIds.add(queryId);
                            }
                        }
        		    }
        		}
        		//-------------条件拼接符号更正，应为单引号' 而不是双引号" --//
        		for(int m=0;m<deleteIds.size();m++) {
					if(m!=deleteIds.size()-1){
						dataDicParam3 +=  "\'"+deleteIds.get(m)+"\'"+",";
					}else{
						dataDicParam3 +=  "\'"+deleteIds.get(m)+"\'";
					}
				}
        		//------------------------------------------------------//
        		for(int j=0;j<ids.size();j++) {
					if(j!=ids.size()-1){
						queryParameter2 +=  "\'"+ids.get(j)+"\'"+",";
					}else{
						queryParameter2 +=  "\'"+ids.get(j)+"\'";
					}
				}
        		//截取sql，获取删除的条件
            	String queryCondition = deleteSql.substring(deleteSql.indexOf("where"), deleteSql.length());
            	String parameter;
            	//参数值
            	if("".equals(queryParameter2)){
            		//第二个参数为空
            		parameter = queryParameter1;
            		queryCondition = queryCondition.replace("?", parameter);
            	}else{
            		String parameter1 = "\""+queryParameter1+"\"";
            		String condition1 = queryCondition.substring(0,queryCondition.indexOf("and")).replace("?", parameter1);
            		String parameter2 = queryParameter2;
            		String condition2 = queryCondition.substring(queryCondition.indexOf("and"),queryCondition.length()).replace("?", parameter2);
            		queryCondition = condition1 + condition2;
            	}
            	if(!"".equals(dataDicParam1)){
            		queryCondition = "where pk_role="+"\'"+dataDicParam1+"\'"+" and pk_dic=" +"\'"+dataDicParam2+"\'"+" and pk_dicval in("+dataDicParam3+")";
        		}
            	List<String> deleteIdList = getTempLogOpDao().queryFormerValueByCondition(tname,queryCondition);
            	//删除操作
            	String optype = "3";
            	uuids = this.insertIntoTableTemp(deleteIdList,tname,optype);
            	if(!uuids.isEmpty()){
	            	for (int i=0;i<uuids.size();i++) {
	        			for (int j=0;j<deleteIdList.size();j++){
	        				if(i==j){
	        					String dataId = deleteIdList.get(i);
	        					String logId = uuids.get(j);
	        					Map<String,String> oldValueMap;
	                        	//旧值记录
	                        	oldValueMap = this.getOldValueMapById(mappedStatement, dataId, tname);
	                        	//需要记录的字段
	                        	Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
	                        	this.recordDeleteContent(tableColumnMap, oldValueMap, logId);
	        				}
	        			}
	            	}
            	}
    		}
    	}
    }
    
    /**
     * 截取sql，获取表名
     * @param sql
     * @param sqlType
     * @return
     * @throws IOException 
     * @throws Throwable
     */
    private String getTableName(String sql,String sqlType) throws IOException {
    	//对sql进行截取，获取表名
        sql = sql.toLowerCase();
    	String tname = "";
    	if("insert".equalsIgnoreCase(sqlType)){
    		tname = sql.substring(sql.indexOf("into")+"into".length(), sql.indexOf('('));
    		tname = tname.trim();
    		return tname;
    	}else if("update".equalsIgnoreCase(sqlType)){
        	String result = SqlParserUtil.getParsedSql(sql);
        	String resultSql = result.trim();
        	String[] resultArray = resultSql.split("\n");
        	String tablename = resultArray[1].trim();
        	tname = new String(tablename.getBytes("ISO-8859-1"),"utf8");
        	tname = tname.replaceAll("%3f", "").replaceAll("[?]", "");
        	return tname;
    	}else if("delete".equalsIgnoreCase(sqlType)){
    		tname = sql.substring(sql.indexOf("from")+4, sql.indexOf("where"));
        	tname = tname.trim();
    	}
    	return tname;
    }
    
    /**
     * 判断表名是否在预先设置的xml文件中
     */
    private Boolean judgeInTables(String tablename){
    	//解析xml，获取所有表名信息
    	List<String> recordTableNames = LogXmlAnalyzeUtils.getTableNameList();
    	for (String recordTableName : recordTableNames) {
			if(tablename.equals(recordTableName)){
				return true;
			}
    	}
    	return false;
    }
    
    /**
     * 把记录插入到临时操作表------表中
     */
    private List<String> insertIntoTableTemp(List<String> ids,String tname,String optype){
    	//获取参数
    	List<TempLogOpVO> tempOpList = new ArrayList<>();
    	List<String> uuids = new ArrayList<>();
    	if(!ids.isEmpty()){
	    	for (String insertid : ids) {
	    		TempLogOpVO tempLogOpVO = new TempLogOpVO();
	    		String id = UUIDUtils.getRandomUUID();
		    	//首先获取表的描述信息
				String tableDesc = LogXmlAnalyzeUtils.getTableDesc(tname);
				//获取操作人信息
				AppContext context = AppContext.getContext();
				String createBy = context.getContextUserId();
				//获取线程唯一标识
				String serviceId = "";
				if(LogAspect.getTempLogOpVO() != null){
					serviceId = LogAspect.getTempLogOpVO().getServiceId();
				}
				uuids.add(id);
				tempLogOpVO.setId(id);
				tempLogOpVO.setTableName(tname);
				tempLogOpVO.setTableDesc(tableDesc);
				tempLogOpVO.setCreateBy(createBy);
				DDateTime current = new DDateTime();
				tempLogOpVO.setCreateTime(current);
				tempLogOpVO.setServiceId(serviceId);
				tempLogOpVO.setDataId(insertid);
				tempLogOpVO.setType(optype);
				tempOpList.add(tempLogOpVO);
	    	}
	    	getTempLogOpDao().insertBatchOpVo(tempOpList);
			return uuids;
    	}
    	return Collections.emptyList();
    }
    
    /**
     * 把字段变化记录插入到临时操作字段表------表中
     */
    private void insertIntoColumnTemp(Object obj,String id,String tname){
		//说明这个表需要记录字段变化信息
		//字段操作表的外键
    	String logid = id;
    	//需要记录变化的字段列表
    	Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
    	//通过反射获取属性方法
    	Method[] methods = obj.getClass().getMethods();
    	Map<String,String>  parameterValue = new HashMap<>();
        // 遍历方法，判断get方法  
        for (Method method : methods) {  
            String methodName = method.getName();
            // 判断是不是get方法  
            if (methodName.indexOf("get") == -1) {// 不是get方法  
                continue;// 不处理  
            }
            Object rsValue = null;  
            try {  
                // 调用get方法，获取返回值 ，没有返回值按空字符串处理
                rsValue = method.invoke(obj);
                if (rsValue == null) {//没有返回值  
                	//把参数名和参数值放进map中
                	parameterValue.put(methodName.substring(3).toLowerCase(), null);  
                }else{
                	parameterValue.put(methodName.substring(3).toLowerCase(), rsValue.toString());  
                }
            } catch (Exception e) {
            	logger.debug(e.getMessage(),e);
                continue;  
            }
        }
        List<TempLogContentVO> tempOpContentList = new ArrayList<>();
        for(Map.Entry<String, String> entry : tableColumnMap.entrySet()){
            for(Map.Entry<String, String> paramEntry : parameterValue.entrySet()){
                String columnKey = entry.getKey();
                String paramKey = paramEntry.getKey();
                if(columnKey.equals(paramKey)){
                    TempLogContentVO tempLogContentVO = new TempLogContentVO();
                    String contentId = UUIDUtils.getRandomUUID();
                    tempLogContentVO.setId(contentId);
                    tempLogContentVO.setLogId(logid);
                    tempLogContentVO.setColName(columnKey);
                    tempLogContentVO.setColDesc(tableColumnMap.get(columnKey));
                    tempLogContentVO.setNewData(parameterValue.get(paramKey));
                    tempLogContentVO.setOldData(null);
                    tempOpContentList.add(tempLogContentVO);
                }
            }
        }
        getTempLogContentDao().insertBatch(tempOpContentList);
    }
    
    /**
     * 获取旧值记录
     * @param mappedStatement
     * @param dataId
     * @param tname
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws ParseException 
     * @throws Throwable
     */
    private Map<String,String> getOldValueMapById(MappedStatement mappedStatement,String dataId,String tname) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
    	//根据旧值记录的id获取旧值记录map
    	Map<String,String> oldValueMap = new HashMap<>();
    	//拦截的方法
    	String methodId=mappedStatement.getId();
    	String load;
    	//因为角色菜单关系，角色页面关系的load方法都在一个dao中，所以加以下条件区别
    	if(methodId.contains("IPowerMgtDao")&&"rms_power_menu".equals(tname)){
    		load = methodId.substring(0, methodId.lastIndexOf('.')) + ".loadMenu";
    	}else if(methodId.contains("IPowerMgtDao")&&"rms_power_page".equals(tname)){
    		load = methodId.substring(0, methodId.lastIndexOf('.')) + ".loadPage";
    	}else{
    		load = methodId.substring(0, methodId.lastIndexOf('.')) + ".load";
    	}
    	//获取将被更新或删除的记录对象
    	SqlSession sqlSession = SqlTemplateHelper.getSqlSession();
    	Object valueVO = sqlSession.selectOne(load, dataId);
		/*
		 * SqlSessionTemplate你不可以手动关闭。SqlSessionTemplate是一个代理类，
		 * 内部他会为每次请求创建线程安全的sqlsession,并与Spring进行集成. 
		 * 在你的方法调用完毕以后他会自动关闭的。
		 */
//    	sqlSession.close();
    	//需要记录变化的字段列表
    	Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
    	if(valueVO == null){
    		//说明做了无用删除，但是字段还是保留
    		for(String columnkey : tableColumnMap.keySet()){
    			oldValueMap.put(columnkey, null);
    		}
    	}
    	//通过反射，找出旧记录字段值
    	if(valueVO !=null){
	    	Method[] methods = valueVO.getClass().getMethods();
	    	for(String columnkey : tableColumnMap.keySet()){
	    		for (Method method : methods) {
					if(("get"+columnkey).equalsIgnoreCase(method.getName())){
						Object rsValue = method.invoke(valueVO); 
						if(rsValue == null){
							oldValueMap.put(columnkey, null);
						}else {
							oldValueMap.put(columnkey, rsValue.toString());
						}
					}else if("getAgingEndDate".equals(method.getName())){
						Object rsValue = method.invoke(valueVO); 
						if(rsValue == null){
							oldValueMap.put("aging_enddate", null);
						}else{
						    
							//获取的是2016-8-24 12:55:54格式的时间，数据库上是int
							//将时间字符串转化为毫秒
						    
							long agingTime = this.changeDateValue(rsValue.toString());
							oldValueMap.put("aging_enddate", Long.toString(agingTime));
						}
						
						//修改人：wangxiaolong
	                    //修改时间：2017-3-20
	                    //修改原因：之前代码将更新时间嵌套在了权限失效中，导致更新时间无法以正确格式显示;
						
					} else if("getUpdate_pwd_time".equals(method.getName())){
                        Object rsValue = method.invoke(valueVO);
                        if(rsValue == null){
                            oldValueMap.put("aging_enddate", null);
                        }else {
                            long updatePwdTime = this.changeDateValue(rsValue.toString());
                            oldValueMap.put("update_pwd_time", Long.toString(updatePwdTime));
                        }
                    }
				}
	    	}
    	}
    	return oldValueMap;
    }
    
    /**
     * 更新时，获取需要记录的字段列表
     * @throws IOException 
     * 
     */
    private List<String> getNeedRecordColumnList(String[] resultArray,String tname) throws IOException{
    	Map<String,String> tableColumnMap = LogXmlAnalyzeUtils.getTableColumnMap(tname);
    	//字段截取的区间，即set了多少个字段
    	int k = 0;
		for (int j = 0;j < resultArray.length;j++) {
        	if("where".equalsIgnoreCase(resultArray[j])){
        		k = j;
        	}
		}
    	//获取设置字段list
    	List<String> editColumn = new ArrayList<>( );
    	for (int m = 3;m< k ;m++) {
    		String columnValue = resultArray[m].trim();
        	String colValue = new String(columnValue.getBytes("ISO-8859-1"),"utf8");
        	colValue = colValue.replaceAll("%3f", "").replaceAll("[?]", "");
        	if("=".equals(colValue)||"?".equals(colValue)||"".equals(colValue)){
        		continue;
        	}
        	if(m == k-1){
        		colValue = colValue.substring(0,colValue.length()-1);
        	}
        	if(colValue.contains("=")){
        		colValue = colValue.substring(0,colValue.length()-2);
        	}
        	editColumn.add(colValue);
    	}
    	//需要记录变化的字段
    	List<String> needRecordColumn = new ArrayList<>();
    	for (String editcolumn : editColumn) {
    		for(String columnkey : tableColumnMap.keySet()) {
				if(editcolumn.equalsIgnoreCase(columnkey)){
					needRecordColumn.add(editcolumn);
				}
			}
		}
    	return needRecordColumn;
    }
    
    /**
     * 批量记录新增操作表字段变化信息
     */
    public void recordInsertContent(Map<String,String> tableColumnMap,List<Map<String,String>> objectList,String logid,int j){
    	List<TempLogContentVO> tempOpContentList = new ArrayList<>();
    	for(Map.Entry<String, String> entry : tableColumnMap.entrySet()){
    	    for(Map.Entry<String, String> objEntry : objectList.get(j).entrySet()){
    	        String columnKey = entry.getKey();
    	        String paramKey = objEntry.getKey();
    	        if(columnKey.equals(paramKey)){
                    TempLogContentVO tempLogContentVO = new TempLogContentVO();
                    String contentId = UUIDUtils.getRandomUUID();
                    tempLogContentVO.setId(contentId);
                    tempLogContentVO.setLogId(logid);
                    tempLogContentVO.setColName(columnKey);
                    tempLogContentVO.setColDesc(tableColumnMap.get(columnKey));
                    tempLogContentVO.setNewData(objectList.get(j).get(paramKey));
                    tempLogContentVO.setOldData(null);
                    tempOpContentList.add(tempLogContentVO);
                }
    	    }
    	}
    	getTempLogContentDao().insertBatch(tempOpContentList);
    }
    
    /**
     * 批量记录更新操作表字段变化信息
     */
    public void recordUpdateContent(Map<String,String> newValueMap,Map<String,String> oldValueMap,Map<String,String> tableColumnMap,String logid){
    	List<TempLogContentVO> tempOpContentList = new ArrayList<>();
    	for(Map.Entry<String, String> oldValueEntry : oldValueMap.entrySet()){
    	    for(Map.Entry<String, String> newValueEntry : newValueMap.entrySet()){
    	        String oldValueKey = oldValueEntry.getKey();
    	        String newValueKey = newValueEntry.getKey();
    	        if(oldValueKey.equals(newValueKey)){
                    TempLogContentVO tempLogContentVO = new TempLogContentVO();
                    String contentId = UUIDUtils.getRandomUUID();
                    tempLogContentVO.setId(contentId);
                    tempLogContentVO.setLogId(logid);
                    tempLogContentVO.setColName(newValueKey);
                    String coldEsc = "";
                    for(Map.Entry<String, String> columnEntry : tableColumnMap.entrySet()){
                        String columnkey = columnEntry.getKey();
                        if(oldValueKey.equals(columnkey)){
                            coldEsc = tableColumnMap.get(columnkey);
                        }
                    }
                    tempLogContentVO.setColDesc(coldEsc);
                    tempLogContentVO.setNewData(newValueMap.get(newValueKey));
                    tempLogContentVO.setOldData(oldValueMap.get(oldValueKey));
                    tempOpContentList.add(tempLogContentVO);
            }
    	    }
    	}
		getTempLogContentDao().insertBatch(tempOpContentList);
    }
    
    /**
     * 批量记录删除操作表字段变化信息
     * @param tableColumnMap
     * @param oldValueMap
     * @param logid
     */
    public void recordDeleteContent(Map<String,String> tableColumnMap,Map<String,String> oldValueMap,String logid){
    	List<TempLogContentVO> tempOpContentList = new ArrayList<>();
    	for(Map.Entry<String, String> columnEntry : tableColumnMap.entrySet()){
            for(Map.Entry<String, String> paramEntry : oldValueMap.entrySet()){
                String columnkey = columnEntry.getKey();
                String paramkey = paramEntry.getKey();
                if(columnkey.equals(paramkey)){
                    TempLogContentVO tempLogContentVO = new TempLogContentVO();
                    String contentId = UUIDUtils.getRandomUUID();
                    tempLogContentVO.setId(contentId);
                    tempLogContentVO.setLogId(logid);
                    tempLogContentVO.setColName(columnkey);
                    tempLogContentVO.setColDesc(tableColumnMap.get(columnkey));
                    tempLogContentVO.setNewData(null);
                    tempLogContentVO.setOldData(oldValueMap.get(paramkey));
                    tempOpContentList.add(tempLogContentVO);
                }
            }
    	}
    	getTempLogContentDao().insertBatch(tempOpContentList);
    }
    
    /**
     * 数据库中保存的时间为int类型,而从get方法得到的是转化后的时间，需要进一步转化
     * @throws ParseException 
     */
    public long changeDateValue(String dateValue) throws ParseException {
    	SimpleDateFormat sdf;
		if(dateValue.trim().length() > 10){
			sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			sdf= new SimpleDateFormat("yyyy-MM-dd");
		}
		long agingEndDateTime;
		long agingTime;
		agingEndDateTime = sdf.parse(dateValue.trim()).getTime();
		agingTime = agingEndDateTime/1000;  
		return agingTime;
    }
}
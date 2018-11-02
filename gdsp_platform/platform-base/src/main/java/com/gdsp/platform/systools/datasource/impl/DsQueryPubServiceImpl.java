/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */
package com.gdsp.platform.systools.datasource.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.global.helper.DefDocConst;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.systools.datasource.dao.IDataSourceDao;
import com.gdsp.platform.systools.datasource.helper.ConnectionFactory;
import com.gdsp.platform.systools.datasource.helper.ConnectionHelper;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.model.DatasourceConnection;
import com.gdsp.platform.systools.datasource.model.DsLibraryVO;
import com.gdsp.platform.systools.datasource.model.SimpleDatasourceVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceQueryPubService;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;

@Service
@Transactional(readOnly = true)
public class DsQueryPubServiceImpl implements IDataSourceQueryPubService {

	private static final Logger logger = LoggerFactory
			.getLogger(DsQueryPubServiceImpl.class);

	
	@Autowired
	private IDataSourceDao dataSourceDao;

	/** 系统码表服务 */
	@Autowired
	private IDefDocService defDocService;


	/**
	 * 根据单条ID查询数据
	 */
	@Override
	public DataSourceVO load(String id) {
		return dataSourceDao.load(id);
	}

	/**
	 * id加载数据源连接对象
	 * @throws SQLException 
	 */
	@Override
	public DatasourceConnection getDataSourceConn(String id) 
			throws SQLException, ClassNotFoundException,MalformedURLException, IOException {
	    /* 加载数据源 */
    	DataSourceVO datasource = dataSourceDao.load(id);
    	return this.getDataSourceConn(datasource);
	}

	// @Override
	// public List<DataSourceVO> queryDataSourceByType(String type) {
	// return dataSourceDao.queryDataSourceByType(type);
	// }

	@Override
	public DatasourceConnection getDataSourceConn(DataSourceVO datasource)
			throws SQLException, ClassNotFoundException, MalformedURLException, IOException {
	    DatasourceConnection datasourceConnection = new DatasourceConnection();
	    Connection connection = null;
	    try {
	        /* 加载数据源 */
    		String ds_type = datasource.getType();
    		String ds_version = datasource.getProduct_version();
    		/* 获取数据源驱动库中相对应的驱动版本 */
    		List<DsLibraryVO> proInfoList = dataSourceDao
    				.findDSProListByTypeAndVersion(ds_type, ds_version);
    		if (proInfoList != null && proInfoList.size() > 0) {
    			datasource.setPath(proInfoList.get(0).getJarPath());
    		}
    		String authtnType = datasource.getAuthentication_model();
    		String oldName = datasource.getUsername();
    		if((ds_type != null && DefDocConst.DATASOURCE_HUAWEI_HIVE.equals(ds_type) 
        			&& authtnType != null && IDataSourceService.KERBEROS_MODEL.equals(authtnType))){
    			//如果是安全模式，datasource参数中的用户名应该为空字符串
        		datasource.setUsername("");
        	}
    		/* 通过工厂获取连接对象，可能会抛出异常 */
    		ConnectionFactory connfactory = new ConnectionFactory(datasource);
    		
        	//判断数据源是否是kerberos认证模式下的华为hive类型的
        	if((ds_type != null && DefDocConst.DATASOURCE_HUAWEI_HIVE.equals(ds_type) 
        			&& authtnType != null && IDataSourceService.KERBEROS_MODEL.equals(authtnType))){
        		//安全验证中需要用到用户名，所以把之前保存下来的用户名再赋值进去datasource参数中
        		datasource.setUsername(oldName);
        		/**如果是的话，需要进行登录验证， 如果登录验证通过了，再继续获取连接对象*/
        		ConnectionHelper.safeAuthenOfHuaweiHive(datasource);
        	}
        	
			connection = connfactory.getConnection();
			if(null == connection){
				throw new BusinessException("未加载到数据源对象，请检查连接信息！");
			}else{
				datasourceConnection.setMessage("连接成功！");
			}
	        datasourceConnection.setConnection(connection);
		} catch (SQLException e){
            //非代码异常，外部原因
		    logger.error(e.getMessage(), e);
		    throw new SQLException("连接时错误，请检查验证信息！",e);
		} catch (ClassNotFoundException e){
            //非代码异常，外部原因
		    logger.error(e.getMessage(), e);
		    throw new ClassNotFoundException("未加载到数据源驱动！",e);
		} catch (MalformedURLException e){
	        //非代码异常，外部原因
		    logger.error(e.getMessage(), e);
		    throw new MalformedURLException("请检查驱动存储路径！");
	    } catch(IOException e){
			logger.error(e.getMessage(), e);
		    throw new IOException("未加载到文件！",e);
		} catch (NullPointerException e){
            logger.error(e.getMessage(), e);
            throw new NullPointerException("请检查驱动存储路径！");
        } catch (InstantiationException e){
            logger.error(e.getMessage(), e);
            datasourceConnection.setMessage("系统内部错误:"+e.getMessage());
            datasourceConnection.setCode("-1");
        } catch (IllegalAccessException e){
            logger.error(e.getMessage(), e);
            datasourceConnection.setMessage("系统内部错误:"+e.getMessage());
            datasourceConnection.setCode("-2");
        } catch (Throwable t){
            logger.error(t.getMessage(), t);
            throw new BusinessException("未知异常！",t);
        }
	    return datasourceConnection;
	}

	@Override
	public List<DataSourceVO> queryDBDataSource() {
		Condition cond = generateProductTypesCondition(IDataSourceQueryPubService.DATASOURCE_TYPE_DB);
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	/*@Override
	public List<DefDocVO> getDataSourceTypes() {
		return defDocService.findSubLevelDocsByTypeAndCode(
				DefDocConst.DATASOURCE_TYPE, DefDocConst.DB_DATASOURCE_TYPE);
	}*/

	/*
	 * (non Javadoc) <p>Description: </p>
	 * @param dsType
	 * @return
	 * @see
	 * com.gdsp.platform.systools.datasource.service.IDataSourceQueryPubService
	 * #lookupDataSourceList(java.lang.String)
	 */
	@Override
	public List<DataSourceVO> getDataSourceListByDsType(String dsType) {
		Condition cond= generateProductTypesCondition(dsType);
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	/**
     * 生成数据源查询条件
     * @param dsType 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
     * @author wqh 2017年8月28日
     */
	private Condition generateProductTypesCondition(String dsType) {
		// 根据类型获取数据库产品信息
		List<DefDocVO> docs = defDocService.findSubLevelDocsByCode(
				DefDocConst.DATASOURCE_TYPE, dsType);
		return encapsulateQueryCondition(docs);
	}
	
	/**
	* @Title: generateProductTypesCondition
	* @Description: 生成数据源查询条件
	* @param dsType 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
	* @param classifys 数据源分类常量(参照系统码表的数据源分类下常量)
	*/ 
	private Condition generateProductTypesCondition(String dsType,List<String> classifys) {
		// 根据类型获取数据库产品信息
		List<DefDocVO> docs = defDocService.findSubLevelDocsByCode(
				DefDocConst.DATASOURCE_TYPE, dsType);
		return encapsulateQueryCondition(docs,classifys);
	}
	
	/**
     * 生成数据源查询条件
     * @param dsType 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
     * @author wqh 2017年8月28日
     */
	private Condition generateProductTypesCondition(List<String> dsTypes) {
		List<DefDocVO> docs = defDocService.findSubLevelDocsByCode(
				DefDocConst.DATASOURCE_TYPE, dsTypes);
		return encapsulateQueryCondition(docs);
	}
	
	/**
	* @Title: generateProductTypesCondition
	* @Description: 生成数据源查询条件
	* @param dsTypes 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
	* @param classifys 数据源分类常量(参照系统码表的数据源分类下常量)
	*/ 
	private Condition generateProductTypesCondition(List<String> dsTypes,List<String> classifys) {
		List<DefDocVO> docs = defDocService.findSubLevelDocsByCode(
				DefDocConst.DATASOURCE_TYPE, dsTypes);
		return encapsulateQueryCondition(docs,classifys);
	}
	
	/** 封装查询条件 */ 
	private Condition encapsulateQueryCondition(List<DefDocVO> docs) {
		List<String> codeList = new ArrayList<String>();
		for (DefDocVO docVO : docs) {
			String doc_code = docVO.getDoc_code();
			codeList.add(doc_code);
		}
		Condition cond = new Condition();
		if (codeList.size() > 0) {
			cond.addExpression("type", codeList, "in");
		}
		return cond;
	}
	
	/** 封装查询条件 */ 
	private Condition encapsulateQueryCondition(List<DefDocVO> docs,List<String> classifys) {
		List<String> codeList = new ArrayList<String>();
		for (DefDocVO docVO : docs) {
			String doc_code = docVO.getDoc_code();
			codeList.add(doc_code);
		}
		Condition cond = new Condition();
		if (codeList.size() > 0) {
			cond.addExpression("type", codeList, "in");
		}
		if(classifys == null || classifys.size() == 0){
			cond.addExpression("classify", "", "in");
		}else{
			cond.addExpression("classify", classifys, "in");
		}
		return cond;
	}

	/* (non Javadoc)
	 * <p>Description: </p>
	 * @param dsTypes
	 * @return
	 * @see com.gdsp.platform.systools.datasource.service.IDataSourceQueryPubService#getDataSourceListByDsType(java.util.List)
	 */ 
	@Override
	public List<DataSourceVO> getDataSourceListByDsType(List<String> dsTypes) {
		Condition cond = generateProductTypesCondition(dsTypes);
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DefDocVO> getDataSourceTypes(String dsType) {
		return defDocService.findSubLevelDocsByCode( DefDocConst.DATASOURCE_TYPE, dsType);
	}

	@Override
	public List<DefDocVO> getDataSourceTypes(List<String> dsTypes) {
		return defDocService.findSubLevelDocsByCode( DefDocConst.DATASOURCE_TYPE, dsTypes);
	}

	@Override
	public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(String dsType, List<String> classifys) {
		Condition cond= generateProductTypesCondition(dsType,classifys);
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(List<String> dsTypes, List<String> classifys) {
		Condition cond= generateProductTypesCondition(dsTypes,classifys);
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}
	
	@Override
	public SimpleDatasourceVO getSimpleDataSourceById(String id) throws SQLException, ClassNotFoundException, MalformedURLException,IOException  {
		SimpleDatasourceVO simpleDatasourceVO = new SimpleDatasourceVO();
		DataSource dataSource = null;
		try{
			DataSourceVO datasourceVO = dataSourceDao.load(id);
			String ds_type = datasourceVO.getType();
			String ds_version = datasourceVO.getProduct_version();
			/* 获取数据源驱动库中相对应的驱动版本 */
			List<DsLibraryVO> proInfoList = dataSourceDao
					.findDSProListByTypeAndVersion(ds_type, ds_version);
			if (proInfoList != null && proInfoList.size() > 0) {
				datasourceVO.setPath(proInfoList.get(0).getJarPath());
			}
			
			String authtnType = datasourceVO.getAuthentication_model();
			String oldName = datasourceVO.getUsername();
    		if((ds_type != null && DefDocConst.DATASOURCE_HUAWEI_HIVE.equals(ds_type) 
        			&& authtnType != null && IDataSourceService.KERBEROS_MODEL.equals(authtnType))){
    			//如果是安全模式，datasourceVO参数中的用户名应该为空字符串
    			datasourceVO.setUsername("");
        	}
    		/* 通过工厂获取连接对象，可能会抛出异常 */
    		ConnectionFactory connfactory = new ConnectionFactory(datasourceVO);
    		
        	//判断数据源是否是kerberos认证模式下的华为hive类型的
        	if((ds_type != null && DefDocConst.DATASOURCE_HUAWEI_HIVE.equals(ds_type) 
        			&& authtnType != null && IDataSourceService.KERBEROS_MODEL.equals(authtnType))){
        		//安全验证中需要用到用户名，所以把之前保存下来的用户名再赋值进去datasourceVO参数中
        		datasourceVO.setUsername(oldName);
        		/**如果是的话，需要进行登录验证， 如果登录验证通过了，再继续获取连接对象*/
        		ConnectionHelper.safeAuthenOfHuaweiHive(datasourceVO);
        	}
			
			dataSource = connfactory.getSimpleDataSource();
			if(null == dataSource){
				throw new BusinessException("未加载到数据源对象，请检查连接信息！");
			}else{
				simpleDatasourceVO.setMessage("数据源创建成功！");
			}
			simpleDatasourceVO.setDataSource(dataSource);
		} catch (ClassNotFoundException e){
	        //非代码异常，外部原因
		    logger.error(e.getMessage(), e);
		    throw new ClassNotFoundException("未加载到数据源驱动！",e);
		} catch (MalformedURLException e){
	        //非代码异常，外部原因
		    logger.error(e.getMessage(), e);
		    throw new MalformedURLException("请检查驱动存储路径！");
	    } catch(IOException e){
			logger.error(e.getMessage(), e);
			throw new IOException("未加载到文件！",e);
		} catch (NullPointerException e){
	        logger.error(e.getMessage(), e);
	        throw new NullPointerException("请检查驱动存储路径！");
	    } catch (InstantiationException e){
	        logger.error(e.getMessage(), e);
	        simpleDatasourceVO.setMessage("系统内部错误:"+e.getMessage());
	        simpleDatasourceVO.setCode("-1");
	    } catch (IllegalAccessException e){
	        logger.error(e.getMessage(), e);
	        simpleDatasourceVO.setMessage("系统内部错误:"+e.getMessage());
	        simpleDatasourceVO.setCode("-2");
	    } catch (Throwable t){
            logger.error(t.getMessage(), t);
            throw new BusinessException("未知异常！",t);
        } 
		return simpleDatasourceVO;
	}

	@Override
	public List<DataSourceVO> getDataSourceListByClassify(String classify) {
		Condition cond = new Condition();
		cond.addExpression("classify", classify,"=");
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DataSourceVO> getDataSourceListByClassifys(List<String> classifys) {
		Condition cond = new Condition();
		if(classifys == null || classifys.size() == 0){
			cond.addExpression("classify", "","=");
		}else{
			cond.addExpression("classify", classifys,"in");
		}
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(List<String> dsTypes, String classify) {
		List<DefDocVO> docs = defDocService.findSubLevelDocsByCode(
				DefDocConst.DATASOURCE_TYPE, dsTypes);
		List<String> codeList = new ArrayList<String>();
		for (DefDocVO docVO : docs) {
			String doc_code = docVO.getDoc_code();
			codeList.add(doc_code);
		}
		Condition cond = new Condition();
		if (codeList.size() > 0) {
			cond.addExpression("type", codeList, "in");
		}
		if(classify == null ){
			cond.addExpression("classify", "", "=");
		}else{
			cond.addExpression("classify", classify, "=");
		}
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(String dsType, String classify) {
		List<DefDocVO> docs = defDocService.findSubLevelDocsByCode(
				DefDocConst.DATASOURCE_TYPE, dsType);
		List<String> codeList = new ArrayList<String>();
		for (DefDocVO docVO : docs) {
			String doc_code = docVO.getDoc_code();
			codeList.add(doc_code);
		}
		Condition cond = new Condition();
		if (codeList.size() > 0) {
			cond.addExpression("type", codeList, "in");
		}
		if(classify == null ){
			cond.addExpression("classify", "", "in");
		}else{
			cond.addExpression("classify", classify, "=");
		}
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DataSourceVO> getDataSourceListByCommon() {
		Condition cond = new Condition();
		cond.addExpression("classify", IDataSourceQueryPubService.DATASOURCE_DEFAULTCLASSIFY, "=");
		return dataSourceDao.queryDataSourceListByCondition(cond);
	}

	@Override
	public List<DataSourceVO> queryAllDataSourceList() {
		return dataSourceDao.queryAllDataSourceByType();
	}

}

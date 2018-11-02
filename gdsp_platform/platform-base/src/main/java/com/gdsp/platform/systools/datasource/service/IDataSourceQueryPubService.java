/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */
package com.gdsp.platform.systools.datasource.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import com.gdsp.platform.config.global.helper.DefDocConst;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.model.DatasourceConnection;
import com.gdsp.platform.systools.datasource.model.SimpleDatasourceVO;

/**
 * 数据源公共查询接口
 * @author yuchenglong
 * @date 2017年7月7日 下午4:28:18
 */
@Deprecated
public interface IDataSourceQueryPubService {

	/** 数据源类型 : 数据库类 */
	public static final String DATASOURCE_TYPE_DB = DefDocConst.DATASOURCE_TYPE_DB;
	/** 数据源类型 : 报表工具类 */
	public static final String DATASOURCE_TYPE_BI = DefDocConst.DATASOURCE_TYPE_BI;
	/**
	 * 数据源类型: 大数据类
	 */
	public static final String DATASOURCE_TYPE_BGDB = DefDocConst.DATASOURCE_TYPE_BGDB;
	
	/**
	* @Fields DEFAULT_CLASSIFY : 默认公共数据源分类
	*/ 
	public static final String DATASOURCE_DEFAULTCLASSIFY = DefDocConst.DATASOURCE_DEFAULTCLASSIFY;
	
    /**
    * 加载数据源对象
    * @param id 数据源ID
    * @return 数据源对象
    */
    public DataSourceVO load(String id);
    
    /**
     * 通过id获取数据库连接
     * @param id
     * @return 数据源连接
     * @throws Exception 
     */
    public DatasourceConnection getDataSourceConn(String id) throws SQLException, ClassNotFoundException, MalformedURLException,IOException ;
    
    /**
     * 通过数据源实体类获取数据库连接
     * @param datasourceVO
     * @return 数据源连接
     * @throws Exception 
     */
    public DatasourceConnection getDataSourceConn(DataSourceVO datasourceVO) throws SQLException, ClassNotFoundException, MalformedURLException,IOException ;

    /**
     * 已不再建议使用该接口，因为有了更符合逻辑的实现。
     * @see #getDataSourceListByDsType
     * @author yuchenglong 2017年09月06日
     * @return DataSourceVO集合
     */
    @Deprecated
    public List<DataSourceVO> queryDBDataSource();
    
    /**
     * 查找指定类型数据源集合,数据源结果集不分类
     * @param dsType 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
     * @return 数据源对象集合
     * @author wqh 2017年10月25日
     */
    public List<DataSourceVO> getDataSourceListByDsType(String dsType);
    
    /**
    * @Title: getDataSourceListByDsTypeAndClassify
    * @Description: 查找指定类型和分类的数据源集合
    * @param dsType 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
    * @param classifys 数据源分类常量(参照系统码表的数据源分类下常量),如果分类参数未null,默认返回结果包括通用数据源(即common)
    * @return List<DataSourceVO>  数据源对象集合
    */ 
    public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(String dsType,List<String> classifys);
    
    
    /**
     * 查找指定类型数据源集合,数据源结果集不分类
     * @param dsTypes 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
     * @return 数据源对象集合
     * @author wqh 2017年10月25日
     */
    public List<DataSourceVO> getDataSourceListByDsType(List<String> dsTypes);
    
    /**
    * @Title: getDataSourceListByDsTypeAndClassify
    * @Description: 查找指定类型和分类的数据源集合
    * @param dsTypes 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
    * @param classifys 数据源分类常量(参照系统码表的数据源分类下常量),如果分类参数未null,默认返回结果包括通用数据源(即common)
    * @return List<DataSourceVO>  数据源对象集合
    */ 
    public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(List<String> dsTypes,List<String> classifys);
    
    /**
     * 查找指定类型的数据源产品信息<br>
     * @param dsType  数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
     * @return 数据源对象集合
     * @author wqh 2017年10月30日
     */
    public List<DefDocVO> getDataSourceTypes(String dsType);
    
    /**
     * 查找指定类型的数据源产品信息<br>
     * @param dsType  数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
     * @return 数据源对象集合
     * @author wqh 2017年10月30日
     */
    public List<DefDocVO> getDataSourceTypes(List<String> dsTypes);
    
    /**
    * @Title: getJdbcTemplateById
    * @Description: 根据数据源id，返回包含SimpleDataSource,该数据源需要手动关闭、释放。
    * @param id
    * @throws SQLException
    * @throws ClassNotFoundException
    * @throws MalformedURLException
    * @throws IOException    参数说明
    * @return SimpleDatasource   
    */ 
    public SimpleDatasourceVO getSimpleDataSourceById(String id) throws SQLException, ClassNotFoundException, MalformedURLException,IOException ;
    
    /**
    * @Title: getDataSourceListByClassify
    * @Description: 查找指定分类的数据源集合
    * @param classify classifys 数据源分类常量(参照系统码表的数据源分类下常量)
    * @return List<DataSourceVO>    返回值说明
    */ 
    public List<DataSourceVO> getDataSourceListByClassify(String classify);
    
    /**
    * @Title: getDataSourceListByClassifys
    * @Description: 查找指定分类的数据源集合
    * @param classifys 数据源分类常量(参照系统码表的数据源分类下常量)
    * @return List<DataSourceVO>    返回值说明
    */ 
    public List<DataSourceVO> getDataSourceListByClassifys(List<String> classifys);
    
    /**
    * @Title: getDataSourceListByDsTypeAndClassify
    * @Description: 查找指定类型和分类的数据源集合
    * @param dsTypes 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
    * @param classify 数据源分类常量(参照系统码表的数据源分类下常量)
    * @return    参数说明
    * @return List<DataSourceVO>    返回值说明
    */ 
    public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(List<String> dsTypes,String classify);
    
    /**
    * @Title: getDataSourceListByDsTypeAndClassify
    * @Description: 查找指定类型和分类的数据源集合
    * @param dsType 数据源类型 (参照接口内常量,可选类型:数据库类型、报表工具类型、大数据类型。)
    * @param classify 数据源分类常量(参照系统码表的数据源分类下常量)
    * @return List<DataSourceVO>    返回值说明
    */ 
    public List<DataSourceVO> getDataSourceListByDsTypeAndClassify(String dsType,String classify);
    
    /**
    * @Title: getDataSourceListByCommon
    * @Description: 查找通用分类下的数据源集合
    * @return List<DataSourceVO>    返回值说明
    */ 
    public List<DataSourceVO> getDataSourceListByCommon();
    
    /**
    * @Title: queryAllDataSourceList
    * @Description: 查找所有数据源集合
    * @return List<DataSourceVO>    返回值说明
    */ 
    public List<DataSourceVO> queryAllDataSourceList();
}

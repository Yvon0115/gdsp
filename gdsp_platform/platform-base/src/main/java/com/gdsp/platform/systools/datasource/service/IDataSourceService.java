package com.gdsp.platform.systools.datasource.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.model.DsLibraryVO;
import com.gdsp.platform.systools.datasource.model.DsRegisterVO;

/**
 * @author songxiang
 * @date 2015年10月28日 下午12:25:17
 */
public interface IDataSourceService {
	
	/** 认证模式 。0代表简单模式；1代表LDAP模式；2代表KERBEROS模式;-1代表认证模式缺省。*/
	public static final String		 DEFAULT_MODEL      = "-1";//认证模式缺省
	public static final String       SIMPLE_MODEL 		= "0";//简单模式
	public static final String       LDAP_MODEL 		= "1";//LDAP模式
	public static final String       KERBEROS_MODEL 	= "2";//KERBEROS模式
	
	/** 简单认证模式可无密码；LDAP认证模式有密码；kerberos认证模式无密码，有秘钥文件和krb5配置文件*/
    public static final String[] AUTHENTICATION_MODEL   = {"简单认证模式","LDAP认证模式","KERBEROS认证模式"};

	/**
	 * 更新数据源
	 * @param vo 数据源对象
	 */
	public void saveDataSource(DataSourceVO vo);

	/**
	 * 删除数据源
	 * @param ids 数据源id列表
	 * */
	public void deleteDataSource(String... ids);

	/**
	 * 查询数据源)
	 * @param condition
	 * @param page
	 * @return Page<DataSourceVO>
	 */
	public Page<DataSourceVO> queryDataSourceByCondition(Condition condition, Pageable page, Sorter sort);

	/**
	 * 查询所有数据源
	 * @return List<DataSourceVO>
	 */
	public List<DataSourceVO> queryAllDataSourceByType();

	/**
	 * 查询所有数据源
	 * @return Map<String, DataSourceVO>
	 */
	public Map<String, DataSourceVO> queryAllDataSourceByTypeMap();
	
	/**
	 * 编码校验
	 * @param dateSourceVO
	 * @return
	 */
	public boolean synchroCheck(DataSourceVO dateSourceVO);

	
	/**
	 * 密码校验
	 * @param dateSourceVO
	 */
	public boolean passwordCheck(DataSourceVO dateSourceVO);
	
	/**
	 * 
	 * @Title: queryDataSourceByCode (查询ByCode)
	 * @param code
	 * @return 参数说明
	 * @return DataSourceVO
	 * */
	public DataSourceVO queryDataSourceByCode(String code);

	/**
	 * 
	 * @Title: checkDatasourceTypeUnique
	 * @Description: 检查数据源类型的唯一性
	 * @param dateSourceVO
	 * @return boolean 返回类型
	 */
	public boolean checkDatasourceTypeUnique(DataSourceVO dateSourceVO);

	/**
	 * 根据启用报表类型查询启用的数据源
	 * @param reportType
	 * @return 以list集合形式返回数据源
	 */
	public List<DataSourceVO> queryEnableDataSource(String[] reportType);

	/**
	 * 根据分类下查询所有下级目录的树状maplist
	 * @param type 类型
	 * @return 以parentId为键值的所有下级目录的树状maplist
	 */
	Map<String, List<Map>> findDirTreeByCategory(String[] type);

	/**
	 * 根据数据源id查询所有的注册服务信息
	 * @param Ds_id 数据源id
	 * @return List<DsRegisterVO>
	 */
	public List<DsRegisterVO> queryDatasourceRef(String... Ds_id);

	/**
	 * 根据type和version查询数据源模型列表
	 * @param ds_type 
	 * @param ds_version
	 * @return List<DsLibraryVO>
	 */
	public List<DsLibraryVO> queryDSProListByTypeAndVersion(String ds_type, String ds_version);
}

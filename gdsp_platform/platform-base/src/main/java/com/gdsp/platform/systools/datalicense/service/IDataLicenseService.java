package com.gdsp.platform.systools.datalicense.service;

import java.util.List;
import java.util.Map;

import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;

/**
 * @author wangliyuan
 */
@Deprecated
public interface IDataLicenseService {
    
	/**
	 * 查询角色的数据字典项
	 * @param roleId 角色ID
	 * @return
	 */
	public List<DataLicenseVO> queryDataDicByRoleId(String roleId);
	
   /**
    * 根据角色id查询有权限的数据字典
    * @param roleId 角色id集合
    * @return
    */
   public  List<DataLicenseVO> queryDataDicByRoleIds(List<?> roleIds);
   /**
    * 
    * @param dimvalList
    * @param dataLicenseRltList
    * @return
    */
   public  List<Map<String, Map<?,?>>> powerChecked( List<Map<String, Map<?,?>>> dimvalList, List<DataLicenseVO> dataLicenseRltList);
   /**
    * 
    * @param roleId
    * @param dicId
    * @return
    */
   public List<DataLicenseVO> queryPowerDataDicVal(List<String> roleId,List<String> dicIdList);

   /**
    * 保存新的数据字典授权
    * @param newAuthDatamap 新的授权数据
    * @param roleId 角色ID
    * @return
    */
   public Object addDicValToRole(Map<String,List<String>> newAuthDataMap,String roleId);
   /**
    * 查询维度是否授权
    * @param id
    * @return
    */
   public List<DataLicenseVO> queryRole(String[] ids);
   /**
    * 查询维值是否授权
    * @param id
    * @return
    */
   public List<DataLicenseVO> queryRoleDicval(String[] ids);
   /**
    * 查询角色权限所有数据
    * @param id
    * @return
    */
   public List<DataLicenseVO> queryAllRoleDataList();
   /**
    * 查询数据维度所有数据
    * @param id
    * @return
    */
   public List<RoleAuthorityVO> queryAllDimList();
   /**
    * 查询数据维度值所有数据
    * @param id
    * @return
    */
   public List<RoleAuthorityVO> queryAllDimValueList();
   
   public Map<?,?> getDimValueTree(String dicId,String roleId);

}

package com.gdsp.platform.systools.datalicense.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;


/**
 * @author wangliyuan
 * 
 */
@MBDao
public interface IDataLicenseDao extends ICrudDao {
    
	
	List<DataLicenseVO> queryDataDicByRoleId(String roleId);
	
    /**
     * 根据角色id查询有权限的数据字典
     * @param roleId
     * @return
     */
    List<DataLicenseVO> queryDataDicByRoleIds(List<?> roleIds);
    
    
    /**
     * 根据角色ID查询 有权限的数据字典和维度
     * @param dicId 字典
     * @param roleIds 角色
     * @return
     */ 
    List<DataLicenseVO> queryDimValueByRoleId(@Param("dicId")String dicId,@Param("roleIds")List roleIds);
    
    /**
     * 查询角色的数据权限值
     * @param roleId 角色ID
     * @param dicValId 数据字典ID
     * @return 包含角色的所有数据权限值的集合
     */
    List<DataLicenseVO> queryPowerDataDicVal (@Param("roleId")List roleId,@Param("dicIdList")List dicIdList);
    /**
     * 
     * @param list
     */
    void insertPowerConn(List<DataLicenseVO> list);
    /**
     * 
     * @param roleId
     * @param dicId
     * @param dicvalueId
     */
    void deletePowerConn(@Param("pk_role") String roleId,@Param("pk_dic") String dicId, @Param("pk_dicval") List<String> dicvalueId);
    
    
    
    /**
     * 查询维度是否授权
     * @param id
     * @return
     */
    List<DataLicenseVO> queryRole(String [] id);
    /**
     * 查询维值是否授权
     * @param id
     * @return
     */
    List<DataLicenseVO> queryRoleDicval(String [] id);
    /**
     * 查询角色数据权限所有数据
     * @return
     */
    List<DataLicenseVO> queryAllRoleDataList();
    /**
     * 查询数据维度所有数据
     * @return
     */
    List<RoleAuthorityVO> queryAllDimList();
    /**
     * 查询数据维度值所有数据
     * @return
     */
    List<RoleAuthorityVO> queryAllDimValueList();
    
    /**
     * 根据主键查找角色和数据维度关系
     * @param id
     * @return
     */
    DataLicenseVO load(@Param("id") String id);
    
    /**
     * 根据字典id查找数据维度(显示所有维度值)
     * @param id
     * @return
     */
    void getDimValueTree(MapListResultHandler handler,@Param("dicId") String dicId,@Param("roleId") String roleId);

    /**
     * 根据字典id和角色ID查找数据维度(只显示该角色ID的维度值)
     * @param id
     * @return
     */
	void getRoleDimValueTree(MapListResultHandler handler,@Param("dicId") String dicId,@Param("roleId") String roleId);

}

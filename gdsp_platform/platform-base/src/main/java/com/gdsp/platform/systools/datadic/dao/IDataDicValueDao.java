package com.gdsp.platform.systools.datadic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
/** 
 * @Description:数据字典信息dao
 * @author qishuo
 * @date 2016年12月9日
 */
@MBDao
public interface IDataDicValueDao extends ICrudDao {
    /**
     * 添加维值时查询维值树结构
     * @param handler
     */
    void queryDataDicValTree(MapListResultHandler handler,@Param("pk_dicId")String pk_dicId);
    /**
     * 新增维值
     * @param vo 新增的对象
     */
    void addDataDicValue(DataDicValueVO vo);
    /**
     * 删除维值
     * @param ids
     */
    void deleteDataDicVal(String[] id);
    /**
     * 编辑维度时根据id查询该维度信息
     */
    DataDicValueVO load(@Param("id")String id);
    /**
     * 维值编码与名称的唯一
     * @param dataDicValueVO
     * @return
     */
    int checkDataDicValCode(DataDicValueVO dataDicValueVO);
    /**
     * 编辑维值
     * @param vo
     */
    void updateDataDicValue(DataDicValueVO vo);
    /**
     * 查询是否存在子维值
     * @param pk_fatherId
     * @return
     */
    List<DataDicValueVO> queryChildDataDicValue(String []pk_fatherId);
    /**

     * 
     * @param list
     * @return
     */
    List<RoleAuthorityVO> findDataDicInfo(@Param("list")List<String> list);
    /*
     * 查询所有的对象的上级
     * @param id
     * @return
     */
    List<DataDicValueVO> queryParent(List<String> id);

}

package com.gdsp.platform.systools.datadic.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
/**
 * @Description:数据字典分类dao
 * @author qishuo
 * @date 2016年12月9日
 */
@MBDao
public interface IDataDicDao extends ICrudDao  {
    /**
     * 加载数据字典
     * @param con 查询条件
     * @param page 分页
     * @param sorter 排序
     * @return
     */
    Page<DataDicVO> queryDataDicPageByCon(@Param("con")Condition con, Pageable page, @Param("sorter")Sorter sorter);
    /**
     * 根据数据字典的维度的id查询维值
     * @param page 分页
     * @param pk_dicId 维度ID
     * @return
     */
    Page<DataDicValueVO>findDaDicValPageByDicId(Pageable page,@Param("pk_dicId")String pk_dicId);
    /**
     * 编辑维度时根据id查询该维度信息
     */
    DataDicVO load(@Param("id")String id);
    /**
     * 更新维度
     * @param dataDicVO
     */
    void updateDataDic(DataDicVO dataDicVO);
    /**
     * 新增维度
     * @param dataDicVO
     */
    void insert (DataDicVO dataDicVO);
    /**
     * 删除维度
     * @param ids
     */
    void deleteDataDic(String[] id);
    /**
     * 判断维度编码和名称是否重复
     * @param dataDicVO
     * @return
     */
    int existSameTypeCode(DataDicVO dataDicVO);
    /**
     * 删除时查询是否存在维值
     * @param pk_dicId
     * @return
     */
    List<DataDicValueVO> queryDataDicValue(String [] pk_dicId);
    /**
     * 
     * @param dicIds
     * @return
     */
    List<Map<String,String>> queryDataDicDetail(List dicIds);
    
    /**
     * 条件查询所有不在当前id集合中的数据字典数据
     * @param ids 数据字典id集合
     * @return  以分页的形式返回数据字典数据
     */
    Page<DataDicVO> queryDataDicExtDicIds(@Param("list")List<String> pk_dicId, @Param("condition") Condition condition, Pageable page);

    /**
     * 加载数据字典中所有数据
     * @return 以集合的形式返回数据字典VO
     */
    List<DataDicVO> queryAllDataDicList();
    /**
     * 
     * @param typeCode
     * @return
     */
    List<DataDicVO> queryDataDicValByTypeCode(String typeCode);
	/**
	 * @param typeCode
	 * @param dataCode
	 * @return
	 */ 
	DataDicValueVO queryDataDicValueByCode(@Param("typeCode")String typeCode, @Param("dataCode")String dataCode);
}

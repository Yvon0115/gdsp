/*
 * 类名: IDimDao
 * 创建人: wly    
 * 创建时间: 2016年2月22日
 */
package com.gdsp.platform.systools.indexanddim.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.model.DimRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.DimValueVO;

/**
 * 维度管理底层接口 <br/>
 * 
 * @author wly
 */
@MBDao
public interface IDimDao {

    /**
     * 
     * <功能简述> 通过查询条件查询维度信息<br/>
     * <功能详细描述>
     * @param condition
     * @param page
     * @return
     */
    Page<DimVO> queryDimByCondition(@Param("condition") Condition condition, Pageable page);

    /**
     * <功能简述> 添加维度信息<br/>
     * <功能详细描述>
     * @param dimVO
     */
    void insert(DimVO dimVO);

    /**
     * 修改维度信息
     * @param dimVO
     */
    void update(DimVO dimVO);

    /**
     * 唯一性校验
     * @param vo
     * @return
     */
    public int existSameDimName(DimVO vo);

    /**
     * 唯一性校验
     * @param vo
     * @return
     */
    public int existSameDimField(DimVO vo);

    /**
     * 
     * <功能简述>查询维度VO <br/>
     * <功能详细描述>
     * @param id
     * @return
     */
    DimVO load(String id);

    /**
     * 
     * <功能简述>删除维度 <br/>
     * <功能详细描述>
     * @param ids
     */
    void deleteDim(String[] ids);

    /**
     * 
     * <功能简述> 删除维度时同步删除关联的维值<br/>
     * <功能详细描述>
     * @param ids
     * @return
     */
    boolean deleteRltForDim(String[] ids);

    /**
     * 
     * <功能简述>授权（查询维度关联的维值信息） <br/>
     * <功能详细描述>
     * @param condition
     * @param pageable
     * @return
     */
    List<DimRltVO> queryDimValueByDimId(@Param("condition") Condition condition, Pageable pageable);

    /**
     * 
     * <功能简述> 添加维值信息<br/>
     * <功能详细描述>
     * @param dimValueVO
     */
    void insertDimValue(DimValueVO dimValueVO);

    /**
     * 修改维值信息
     * @param dimValueVO
     */
    void updateDimValue(DimValueVO dimValueVO);

    /**
     * 
     * <功能简述>添加维度维值关联关系 <br/>
     * <功能详细描述>
     * @param vo
     */
    void insertdimRltVO(DimRltVO vo);

    /**
     * 
     * <功能简述>查询维值VO <br/>
     * <功能详细描述>
     * @param id
     * @return
     */
    DimValueVO loadDimValue(String id, @Param("addCond") String addCond);

    /**
     * 
     * <功能简述>删除维度关联的维值 <br/>
     * <功能详细描述>
     * @param id
     */
    void deleteRltDimValue(String[] id);

    /**
     * 
     * <功能简述>删除维值 <br/>
     * <功能详细描述>
     * @param id
     */
    void deleteDimValue(String[] id);

    /**
     * 
     * <功能简述> 查询维度的维值<br/>
     * <功能详细描述>
     * @param addCond
     * @return
     */
    void queryDimValue(MapListResultHandler handler, @Param("addCond") String addCond);

    /**
     * 
     * <功能简述>通过维度名称查询id <br/>
     * <功能详细描述>
     * @param dimName
     * @return
     */
    String queryDimId(String dimName);

    /**
     * 汇总灵活查询
     * 根据维值ID串获取关联维度
     * 
     * @param dimValIDs
     * @return
     */
    List<DimRltVO> getDimRltByValIds(String[] dimValIDs);
}

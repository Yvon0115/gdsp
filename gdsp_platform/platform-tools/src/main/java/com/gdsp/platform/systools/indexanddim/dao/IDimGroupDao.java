/*
 * 类名: IDimGroupDao
 * 创建人: wly    
 * 创建时间: 2016年2月3日
 */
package com.gdsp.platform.systools.indexanddim.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.systools.indexanddim.model.DimGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimGroupVO;
import com.gdsp.platform.systools.indexanddim.model.DimVO;

/**
 * 维度分组管理底层接口类 <br/>
 * 
 * @author wly
 */
@MBDao
public interface IDimGroupDao {

    /**
     * 
     * 查询维度分组信息 <br/>
     * 
     * @param condition
     * @param sort
     * @return
     */
    List<DimGroupVO> queryDimGroupByCondition(@Param("condition") Condition condition);

    /**
     * 
     * 新增维度组 <br/>
     * 
     * @param dimGroup
     */
    void insert(DimGroupVO dimGroup);

    /**
     * 
     *  修改维度组<br/>
     * 
     * @param dimGroup
     */
    void update(DimGroupVO dimGroup);

    /**
     * 
     * 查询维度组VO <br/>
     * 
     * @param id
     * @return
     */
    DimGroupVO load(String id);

    /**
     * 
     * 维度组编码、名称唯一性校验 <br/>
     * 
     * @param dimGroup
     * @return
     */
    public int existSameDimGroupCode(DimGroupVO dimGroup);

    public int existSameDimGroupName(@Param("addCond") String addCond);

    public int isSameDimGroupName(DimGroupVO dimGroup);

    /**
     * 
     *  通过查询条件查询维度分组信息<br/>
     * 
     * @param condition
     * @param sort
     * @return
     */
    List<DimGroupVO> queryDimGroupListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    /**
     * 
     *  通过维度组id查询维度信息<br/>
     * 
     * @param condition
     * @param page
     * @return
     */
    Page<DimGroupRltVO> queryDimByGroupId(@Param("condition") Condition condition, Pageable page);

    /**
     * 通过id删除维度分组
     * <功能简述> <br/>
     * <功能详细描述>
     * @param id
     */
    void delete(String id);

    /**
     * 通过维度组id查询可关联的维度
     * <功能简述> <br/>
     * <功能详细描述>
     * @param dimGroupId
     * @param page
     * @return
     */
    Page<DimVO> queryDimForDimGroupPower(@Param("addCond") String addCond, Pageable page);

    /**
     * 
     * 通过维度组id查询包含的维度信息 <br/>
     * 
     * @param condition
     * @param page
     * @return
     */
    Page<DimGroupRltVO> queryDimByDimGroupId(@Param("condition") Condition condition, Pageable page);

    /**
     * 
     * 保存维度到维度组 <br/>
     * 
     * @param vo
     */
    void insertDimGroupRlt(List<DimGroupRltVO> list);

    /**
     * 
     *  删除维度组关联的维度<br/>
     * 
     * @param ids
     */
    void deleteDim(String[] ids);

    /**
     * 
     * 查询id <br/>
     * 
     * @param innercode
     * @return
     */
    String queryId(String innercode);
}

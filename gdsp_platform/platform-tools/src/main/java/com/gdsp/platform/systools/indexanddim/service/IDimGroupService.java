/*
 * 类名: IDimGroupService
 * 创建人: wly   
 * 创建时间: 2016年2月3日
 */
package com.gdsp.platform.systools.indexanddim.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.indexanddim.model.DimGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimGroupVO;
import com.gdsp.platform.systools.indexanddim.model.DimVO;

/**
 * 维度组服务<br/>
 * @author wly
 */
public interface IDimGroupService {

    /**
     * 查询维度分组信息 <br/>
     * @param condition
     * @return
     */
    public List<DimGroupVO> queryDimGroupByCondition(Condition condition);

    /**
     * 保存维度组信息 <br/>
     * @param vo
     * @return
     */
    public boolean saveDimGroup(DimGroupVO vo);

    /**
     * 根据主键查询维度组信息 <br/>
     * @param id
     * @return
     */
    public DimGroupVO load(String id);

    /**
     * 唯一性校验维度组编码 <br/>
     * @param vo
     * @return
     */
    public boolean uniqueCheck(DimGroupVO vo);

    /**
     * 唯一性校验维度组名称 <br/>
     * @param vo
     * @return
     */
    public boolean uniqueNameCheck(DimGroupVO vo);

    /**
     * 根据维度组id查询维度分组列表 <br/>
     * @param dimGroupID
     * @param condition
     * @param sort
     * @param containItself
     * @return
     */
    public List<DimGroupVO> queryChildDimGroupListById(String dimGroupID, Condition condition, Sorter sort, boolean containItself);

    /**
     * 通过维度组id查询维度 <br/>
     * @param dimGroupID
     * @param condition
     * @param sort
     * @param containItself
     * @return
     */
    public Page<DimGroupRltVO> queryDimByGroupId(String dimGroupId, Condition cond, Pageable page);

    /**
     * 通过id删除维度分组 <br/>
     * @param id
     * @return
     */
    public boolean deleteDimGroup(String id);

    /**
     * 根据维度组id查询可关联的维度 <br/>
     * @param dimGroupId
     * @param page
     * @return
     */
    public Page<DimVO> queryDimForDimGroupPower(String dimGroupId, Pageable page);

    /**
     * 通过维度组id查询维度组中包含的维度<br/>
     * @param dimGroupId
     * @param cond
     * @param page
     * @return
     */
    public Page<DimGroupRltVO> queryDimByDimGroupId(String innercode, Condition cond, Pageable page);

    /**
     * 保存维度到维度组 <br/>
     * @param dimGroupId 维度组id
     * @param dimId 维度id
     */
    public void addDimOnDimgroup(String dimGroupId, String... id);

    /**
     * 删除维度组关联的维度 <br/>
     * @param ids
     * @return
     */
    public boolean deleteDim(String... ids);
}

/*
 * 类名: IDimService
 * 创建人: wly    
 * 创建时间: 2016年2月22日
 */
package com.gdsp.platform.systools.indexanddim.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.indexanddim.model.DimRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.DimValueVO;

/**
 *  维度管理接口 <br/>
 * 
 * @author wly
 */
public interface IDimService {

    /**
     * 
     *  通过查询条件查询维度信息<br/>
     * 
     * @param condition
     * @param page
     * @return
     */
    public Page<DimVO> queryDimByCondition(Condition condition, Pageable page);

    /**
     * 
     * 保存维度 <br/>
     * 
     * @param vo
     * @return
     */
    public boolean saveDim(DimVO vo);

    /**
     * 
     * 唯一性校验 <br/>
     * 
     * @param vo
     * @return
     */
    public boolean uniqueCheckName(DimVO vo);

    /**
     * 
     * 唯一性校验 <br/>
     * 
     * @param vo
     * @return
     */
    public boolean uniqueCheckField(DimVO vo);

    /**
     * 通过id查询维度信息
     * @param id
     * @return
     */
    public DimVO load(String id);

    /**
     * 
     * 删除维度 <br/>
     * 
     * @param ids
     * @return
     */
    public boolean deleteDim(String... ids);

    /**
     * 
     * 授权（维度关联的维值信息） <br/>
     * 
     * @param dimId
     * @param condition
     * @param pageable
     * @return
     */
    public List<DimRltVO> queryDimValueByDimId(String dimId, Condition condition, Pageable pageable);

    /**
     * 
     * 保存维值 <br/>
     * 
     * @param vo
     * @return
     */
    public boolean saveDimValue(DimValueVO vo, String dimId);

    /**
     * 
     * 通过id查询维值信息 <br/>
     * 
     * @param id
     * @return
     */
    public DimValueVO loadDimValue(String id);

    /**
     * 
     * 删除维度关联的维值 <br/>
     * 
     * @param id
     * @return
     */
    public boolean deleteDimValue(String... id);

    /**
     * 
     * 通过维度id查询维值信息<br/>
     * 
     * @param dimId
     * @return
     */
    public Map queryDimValue(String dimId);

    /**
     * 根据维值ID获取维度维值关系
     * 
     * @param dimValIDs
     * @return
     */
    public List<DimRltVO> getDimRltByValIds(String[] dimValIDs);
}

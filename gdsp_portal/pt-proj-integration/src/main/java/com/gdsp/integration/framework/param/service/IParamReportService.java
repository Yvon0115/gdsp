package com.gdsp.integration.framework.param.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.model.AcParamRelation;

public interface IParamReportService {

    /**
     * 
     * @Title: addParamRelation 
     * @Description: 添加参数与报表关系
     * @param vo	参数与报表关系VO
     * @return void    返回类型
     */
    public void addParamRelation(AcParamRelation vo);

    /**
     * 
     * @Title: deleteParamRelation 
     * @Description: 删除参数与报表关系
     * @param ids	参数与报表关系表ID
     * @return void    返回类型
     */
    public void deleteParamRelation(String[] ids);

    /**
     * 
     * @Title: queryParamByReport 
     * @Description: 获取存在关系的参数ID列表
     * @param id	报表ID
     * @return List<AcParam>    返回参数ID集合
     */
    public List<String> queryParamIdListByReport(String id);

    /**
     * 
     * @Title: queryParamPageByReport 
     * @Description: 获取存在关系的参数列表
     * @param id	报表ID
     * @param condition
     * @param sort
     * @param page
     * @return
     * @return Page<AcParam>    返回类型
     */
    public Page<AcParam> queryParamPageByReport(String id, Condition condition, Sorter sort, Pageable page);
}

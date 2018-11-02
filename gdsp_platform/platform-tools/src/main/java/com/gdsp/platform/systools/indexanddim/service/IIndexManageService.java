package com.gdsp.platform.systools.indexanddim.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;

/**
 * 指标管理服务
 * @author Administrator
 *
 */
public interface IIndexManageService {

    /**
     * 
     * @param condition 查询条件
     * @param pageable 分页请求
     * @param sort 排序规则
     * @return 分页结果
     */
    public Page<IndexInfoVO> queryIndexInfoByCondition(Condition condition, Pageable pageable,
            Sorter sort);

    /**
     * 保存指标信息
     * @param indexInfoVO 指标实体
     */
    public boolean saveIdxInfo(IndexInfoVO indexInfoVO);

    /**
     * 根据ID查询指标信息
     * @param id 
     * @return
     */
    public IndexInfoVO findIdxInfoById(String id);

    /**
     * 根据ID删除指标
     * @param id
     */
    public boolean deleteIdxInfo(String[] id);

    /**
     * 唯一性检查
     * @param indexInfoVO
     * @return
     */
    public boolean uniqueCheck(IndexInfoVO indexInfoVO);

    /**
     * 查询指标表，归属部门数据
     * @param condition
     * @param pageable
     * @param sort
     * @return
     */
    public Page<IndexInfoVO> queryIdxDepartment(Condition condition, Pageable pageable,
            Sorter sort);

    /**
     * 保存Excel数据
     * @param excelList
     * @param errorList
     */
    public void saveExcelIdxInfo(ArrayList<String[]> excelList,
            List<String> errorList);

    /**
     * 根据部门ID获取部门名称
     * @param comedepart
     * @return
     */
    public OrgVO queryOrgByID(String comedepart);
    /**
     * 根据指标ID获取指标信息
     */
    public List<IndexInfoVO> queryIndexByIds(List<String> ids);
}

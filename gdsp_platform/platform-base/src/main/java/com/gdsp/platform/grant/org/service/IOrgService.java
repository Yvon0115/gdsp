package com.gdsp.platform.grant.org.service;

import java.util.List;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.org.model.OrgVO;

/**
 * 机构服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IOrgService {

    /**
     * 保存机构
     * @param vo 机构
     * @return 是否成功
     */
//    public boolean saveOrg(OrgVO vo);

	//删除无用接口
/*    *//**
     * 根据机构id查询的机构列表
     * @param orgID 机构id
     * @param containItself 查询结果是否含其本身
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     *//*
    public List<OrgVO> queryChildOrgListById(String orgID, Condition condition, Sorter sort, boolean containItself);
*/
    /**
     * 通用查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return 分页结果
     */
    public List<OrgVO> queryOrgListByCondition(Condition condition, Sorter sort);

    /**
     * @Title: uniqueCheck
     * (唯一性校验)
     * @param vo
     * @return boolean true 唯一  false 不唯一
     *      */
    public boolean uniqueCheck(OrgVO vo);

    /**
     * @Title: uniqueNameCheck
     * (名称唯一性校验)
     * @param vo
     * @return boolean true 唯一  false 不唯一
     *      */
    public boolean uniqueNameCheck(OrgVO vo);
}

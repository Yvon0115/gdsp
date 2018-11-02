package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.FormTypeVO;

/**
 * 预置单据类型服务
 * @author sun
 */
public interface IFormTypeService {

    /**
     * 通用分页结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @param pageable 分页参数
     * @return Page
     */
    public Page<FormTypeVO> queryFormTypePageByCondition(Condition condition, Sorter sort, Pageable pageable);

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<FormTypeVO> queryFormTypeListByCondition(Condition condition, Sorter sort);

    /**
     * 保存单据类型
     */
    public boolean saveFormType(FormTypeVO formTypeVO);

    /**
     * 级联删除注册单据类型表和其变量表信息
     * @param ids 单据类型表主键集合
     * @return
     */
    public boolean deleteFormType(String... ids);

    public FormTypeVO findTypeAndVariableById(String Id);

    /**
     * 根据formid查询单据注册的url
     * @param formId
     * @return	url
     */
    public String queryFormURLByFormId(String formId);

    /**
     * 校验单据编码唯一性
     * @param categoryCode
     * @return
     */
	public boolean checkCode(String formCode);

	/**
	 * 校验单据名称
	 * @param id
	 * @param categoryName
	 * @return
	 */
	public boolean checkName(String id, String categoryName);
}

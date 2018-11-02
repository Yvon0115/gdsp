package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.FormTypeVO;

/**
 * 
 * @author sun
 *
 */
@MBDao
public interface IFormTypeDao {

    public Page<FormTypeVO> queryFormTypePageByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort, Pageable pageable);

    public List<FormTypeVO> queryFormTypeListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public boolean insertFormType(FormTypeVO formTypeVO);

    public boolean updateFormType(FormTypeVO formTypeVO);

    public boolean deleteFormType(String[] ids);

    /**
     * 根据formid查询单据注册的url
     * @param formId
     * @return
     */
    public String queryFormURLByFormId(@Param("formId") String formId);

    /**
     * 校验编码
     * @param formCode
     * @return
     */
	public int countFormDefByCode(@Param("formCode")String formCode);

	/**
	 * 校验名称
	 * @param id
	 * @param formName
	 * @return
	 */
	public int countFormDefByName(@Param("id")String id,@Param("formName") String formName);
}

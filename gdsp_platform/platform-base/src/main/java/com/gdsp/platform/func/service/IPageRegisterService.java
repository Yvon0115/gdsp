package com.gdsp.platform.func.service;

import java.util.List;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.model.PageRegisterVO;

/**
 * @author
 *
 */
public interface IPageRegisterService {

    /**
     * 保存页面
     * @param vo 页面
     */
    public void insertPageRegister(PageRegisterVO vo);

    /**
     * 调整页面优先级
     * @param pageIDs 页面IDs
     */
    public void sort(String[] pageIDs);

    /**
     * 删除页面
     * @param id 页面ID
     */
    public void deletePageRegister(String id);

    /**
    * @Title: queryPageRegisterListByCondition
    * (根据条件查询页面注册信息)
    * @param condition
    * @param sort
    * @return List<PageRegisterVO>  页面注册信息
    *     */
    public List<PageRegisterVO> queryPageRegisterListByCondition(Condition condition, Sorter sort);

    public PageRegisterVO loadPageRegister(String id);

    public int loadPageID(String pageId);

    public boolean synchroCheck(String funname, String menuid);

    public boolean isUniqueName(String funname, String menuid, String id);

    public List<PageRegisterVO> queryAllPageList();
}

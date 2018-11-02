package com.gdsp.platform.func.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.model.ResourceRegisterVO;

public interface IResourceRegisterService {

    /**
    * @Title: getResourceByMumeid
    * (根据当前页面ID获取资源列表)
    * @param muneID
    * @param page
    * @return    参数说明
    * @return Page<>    返回值说明
    *      */
    public Page<ResourceRegisterVO> getResourceByMumeid(Condition condition, Pageable page);

    /**
    * @Title: getResourceByID
    * (根据ID获取资源)
    * @param id
    * @return    参数说明
    * @return ResourceRegisterVO    返回值说明
    *      */
    public ResourceRegisterVO getResourceByID(String id);

    /**
    * @Title: updateResRegister
    * (更新资源信息)
    * @param vo    参数说明
    * @return void    返回值说明
    *      */
    public void updateResRegister(ResourceRegisterVO vo);

    /**
    * @Title: insertResRegister
    * (新增资源信息)
    * @param vo    参数说明
    * @return void    返回值说明
    *      */
    public void insertResRegister(ResourceRegisterVO vo);

    /**
    * @Title: delete
    * (删除资源信息)
    * @param id    参数说明
    * @return void    返回值说明
    *      */
    public void delete(String... id);

    public boolean synchroCheck(ResourceRegisterVO vo);
}

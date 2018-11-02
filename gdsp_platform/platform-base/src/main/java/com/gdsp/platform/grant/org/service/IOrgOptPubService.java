package com.gdsp.platform.grant.org.service;

import com.gdsp.platform.grant.org.model.OrgVO;

/**
 * 机构相关操作公共接口
 * @author jingf
 * @see OrgVO 机构信息 : {@code OrgVO}
 * @date 2016年8月2日 下午5:01:30 
 */
public interface IOrgOptPubService {

    /**
     * 添加机构
     * @param orgVO 机构信息
     */
    public void insert(OrgVO orgVO);

    /**
     * 修改机构
     * @param orgVO 机构对象
     */
    public void update(OrgVO orgVO);

    /**
     * 根据机构id删除机构
     * @param ids 机构id集合
     * @return boolean 是否删除成功
     */
    public boolean deleteOrg(String[] ids);
}

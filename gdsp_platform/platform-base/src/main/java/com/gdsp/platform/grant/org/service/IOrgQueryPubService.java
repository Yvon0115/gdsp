package com.gdsp.platform.grant.org.service;

import java.util.List;

import com.gdsp.platform.grant.org.model.OrgVO;

/**
 * 
 * @ClassName: IOrgPubService 
 * @Description: 机构服务
 * @author jingf
 * @date 2016年8月2日 下午5:01:30 
 *
 */
public interface IOrgQueryPubService {

    /**lyf 2016.12.27修改 原因：权限拆分，修改了联表查询语句，返回的OrgVO没有了lastModifyByName和createByName，所以需要修改实现
     * 根据主键查询机构
     * @return 机构对象
     */
    public OrgVO load(String id);

    /**
     * 
     * @Title: queryAllOrgList 
     * @Description: 查询所有机构信息
     * @return List<OrgVO>    返回机构信息List集合
     */
    public List<OrgVO> queryAllOrgList();

    /**
     * 
     * @Title: queryOrgListByIDs 
     * @Description: 根据ID集合查询机构信息
     * @param list ID集合
     * @return List<OrgVO>    返回机构信息集合
     */
    public List<OrgVO> queryOrgListByIDs(List list);

    /**lyf 2016.12.23 权限拆分
     * 根据机构id查询的子机构列表
     * @param orgID 机构id
     * @return List	返回机构VO集合
     */
    public List<OrgVO> queryChildOrgListById(String orgID);
    
    /**权限拆分
     * 根据机构id查询的本机构及子机构列表
     * @param orgID 机构id
     * @return List	返回机构VO集合
     */
    public List<OrgVO> querySelftAndChildOrgListById(String orgID);

    /**
     * 根据机构名字查询机构 lyf 2016.12.22 权限拆分
     * 模糊查询 “like”
     * @param orgname
     * @return
     */
    public List<OrgVO> queryOrgListByCondition(String orgname);
    
    /**
     * @Title: queryAllOrgList 
     * @Description: 查询所有机构信息
     * @return List<OrgVO>    返回机构信息List集合,机构信息只包含id和orgname
     */
    public List<OrgVO> findAllOrgList();
}

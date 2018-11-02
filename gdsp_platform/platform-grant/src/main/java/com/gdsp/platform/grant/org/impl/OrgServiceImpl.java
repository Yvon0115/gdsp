package com.gdsp.platform.grant.org.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.org.dao.IOrgDao;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

@Service
@Transactional(readOnly = true)
public class OrgServiceImpl implements IOrgService {

    @Autowired
    private IOrgDao      orgDao;
    @Autowired
    private IOrgPowerDao orgPowerDao;
    @Autowired
    private IUserQueryPubService userQueryPubService;

    /*@Transactional
    @Override
    public boolean saveOrg(OrgVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            orgDao.update(vo);
        } else {
            String parentId = vo.getPk_fatherorg();
            if (!StringUtils.isEmpty(parentId)) {
                OrgVO parentVO = orgDao.load(parentId);
                vo = (OrgVO) TreeCodeHelper.generateTreeCode(vo, parentVO.getInnercode());
            } else {
                vo = (OrgVO) TreeCodeHelper.generateTreeCode(vo, null);
            }
            orgDao.insert(vo);
            // 增加机构后，查询其上级机构有权限的角色，增加角色机构权限
            if (StringUtils.isNotEmpty(vo.getPk_fatherorg())) {
                Condition cond = new Condition();
                cond.addExpression("resource_id", vo.getPk_fatherorg());
                List<OrgPowerVO> powerVOs = orgPowerDao.queryOrgPowerByCondition(cond);
                OrgPowerVO orgPowerVO;
                for (OrgPowerVO powerVO : powerVOs) {
                    orgPowerVO = new OrgPowerVO();
                    orgPowerVO.setPk_role(powerVO.getPk_role());
                    orgPowerVO.setResource_id(vo.getId());
                    orgPowerDao.insert(orgPowerVO);
                }
            }
        }
        return true;
    }*/
    
    //重构接口删除无用代码
/*
    @Override
    public List<OrgVO> queryChildOrgListById(String orgID, Condition condition, Sorter sort, boolean containItself) {
        // 增加机构查询条件
        //OrgVO orgVO = orgDao.load(orgID);
        *//**lyf 2016.12.27修改 原因：权限拆分，修改了联表查询语句，返回的OrgVO没有了lastModifyByName和createByName**//*
        OrgVO orgVO = orgDao.load(orgID);
        List<UserVO> uservos = userQueryPubService.findAllUsersList();
        for (UserVO uservo : uservos) {
            if(orgVO.getLastModifyBy() != null&&orgVO.getLastModifyBy().equalsIgnoreCase(uservo.getId())){
                orgVO.setLastModifyByName(uservo.getUsername());
            }
            if(orgVO.getCreateBy().equalsIgnoreCase(uservo.getId())){
                orgVO.setCreateByName(uservo.getUsername());
            }
        }
        *//**end**//*
        String innercode = orgVO.getInnercode();
        condition.addExpression("id", "select id from rms_orgs where innercode like '" + innercode + "%' ", "in");
        if (!containItself) {
            condition.addExpression("id", orgID, "<>");
        }
        return orgDao.queryOrgListByCondition(condition, sort);
    }*/

    @Override
    public List<OrgVO> queryOrgListByCondition(Condition condition, Sorter sort) {
        return orgDao.queryOrgListByCondition(condition, sort);
    }

    @Override
    public boolean uniqueCheck(OrgVO vo) {
        return orgDao.existSameOrg(vo) == 0;
    }

    @Override
    public boolean uniqueNameCheck(OrgVO vo) {
        return orgDao.existSameNameOrg(vo) == 0;
    }
}

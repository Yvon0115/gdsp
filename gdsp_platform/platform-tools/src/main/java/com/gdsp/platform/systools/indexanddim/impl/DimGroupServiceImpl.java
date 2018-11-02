/*
 * 类名: DimGroupServiceImpl
 * 创建人: wly   
 * 创建时间: 2016年2月3日
 */
package com.gdsp.platform.systools.indexanddim.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.platform.systools.indexanddim.dao.IDimGroupDao;
import com.gdsp.platform.systools.indexanddim.model.DimGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimGroupVO;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.service.IDimGroupService;

/**
 * 维度分组管理实现类 <br/>
 * 
 * @author wly 
 */
@Service
@Transactional(readOnly = true) //不启用事务管理 声明式的事务管理方法
public class DimGroupServiceImpl implements IDimGroupService {

    @Autowired
    private IDimGroupDao dimGroupDao;

    @Override
    public List<DimGroupVO> queryDimGroupByCondition(Condition condition) {
        return dimGroupDao.queryDimGroupByCondition(condition);
    }

    @Override
    @Transactional
    public boolean saveDimGroup(DimGroupVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            dimGroupDao.update(vo);
        } else {
            String innercode = vo.getInnercode();
            if (!StringUtils.isEmpty(innercode)) {
                String pk_fatherid = dimGroupDao.queryId(innercode);
                vo.setPk_fatherid(pk_fatherid);
                vo = (DimGroupVO) TreeCodeHelper.generateTreeCode(vo, innercode);
            } else {
                vo = (DimGroupVO) TreeCodeHelper.generateTreeCode(vo, null);
            }
            dimGroupDao.insert(vo);
        }
        return true;
    }

    @Override
    public DimGroupVO load(String id) {
        return dimGroupDao.load(id);
    }

    @Override
    public boolean uniqueCheck(DimGroupVO vo) {
        return dimGroupDao.existSameDimGroupCode(vo) == 0;
    }

    @Override
    public boolean uniqueNameCheck(DimGroupVO vo) {
        String innercode = vo.getInnercode();
        String groupname = vo.getGroupname();
        String id = vo.getId();
        if (innercode != null && innercode != "") {
            String addCond = " groupname='" + groupname + "' and innercode like'" + innercode + "%' ";
            if (id != null && id != "") {
                addCond += " and id<>'" + id + "'";
            }
            return dimGroupDao.existSameDimGroupName(addCond) == 0;
        } else {
            return dimGroupDao.isSameDimGroupName(vo) == 0;
        }
    }

    @Override
    public List<DimGroupVO> queryChildDimGroupListById(String dimGroupID, Condition condition, Sorter sort, boolean containItself) {
        DimGroupVO dimGroupVO = dimGroupDao.load(dimGroupID);
        String innercode = dimGroupVO.getInnercode();
        condition.addExpression("id", "select id from bp_dimgroup where innercode like '" + innercode + "%' ", "in");
        if (!containItself) {
            condition.addExpression("id", dimGroupID, "<>");
        }
        return dimGroupDao.queryDimGroupListByCondition(condition, sort);
    }

    public Page<DimGroupRltVO> queryDimByGroupId(String dimGroupId, Condition cond, Pageable page) {
        cond.addExpression("rlt.pk_dimgroup", dimGroupId);
        Page<DimGroupRltVO> dims = dimGroupDao.queryDimByDimGroupId(cond, page);
        return dims;
    }

    @Transactional
    @Override
    public boolean deleteDimGroup(String id) {
        dimGroupDao.delete(id);
        return true;
    }

    @Override
    public Page<DimVO> queryDimForDimGroupPower(String dimGroupId, Pageable page) {
        String addCond = "dim.id not in( select pk_dim from bp_dimgroup_rlt where pk_dimgroup='" + dimGroupId + "') ";
        Page<DimVO> queryDimForDimGroupPower = dimGroupDao.queryDimForDimGroupPower(addCond, page);
        return queryDimForDimGroupPower;
    }

    @Override
    public Page<DimGroupRltVO> queryDimByDimGroupId(String innercode, Condition cond, Pageable page) {
        cond.addExpression("rlt.pk_dimgroup", "(select id from bp_dimgroup where innercode like'" + innercode + "%' )", "in");
        System.out.println(cond);
        Page<DimGroupRltVO> dims = dimGroupDao.queryDimByDimGroupId(cond, page);
        return dims;
    }

    @Transactional
    @Override
    public void addDimOnDimgroup(String dimGroupId, String... id) {
        List<DimGroupRltVO> list = new ArrayList<DimGroupRltVO>();
        for (String dimIds : id) {
            DimGroupRltVO dimGroupRltVO = new DimGroupRltVO();
            dimGroupRltVO.setPk_dim(dimIds);
            dimGroupRltVO.setPk_dimgroup(dimGroupId);
            list.add(dimGroupRltVO);
        }
        dimGroupDao.insertDimGroupRlt(list);
    }

    @Transactional
    @Override
    public boolean deleteDim(String... ids) {
        dimGroupDao.deleteDim(ids);
        return true;
    }
}

package com.gdsp.platform.systools.indexanddim.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.dao.IIndGroupDao;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.IndTreeVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;
import com.gdsp.platform.systools.indexanddim.service.IIndGroupService;

@Service
@Transactional(readOnly = true)
public class IndGroupServiceImpl implements IIndGroupService {

    @Autowired
    IIndGroupDao indGroupDao;

    @Override
    public List<IndTreeVO> queryAllIndGroup() {
        return indGroupDao.queryAllIndGroup();
    }

    @Override
    public boolean uniqueCheck(IndTreeVO indTreeVO) {
        return indGroupDao.existSameCode(indTreeVO) != 1;
    }

    @Override
    @Transactional(readOnly = false)
    public void insert(IndTreeVO indTreeVO) {
        // 版本号初始值为0
        indTreeVO.setVersion(0);
        indGroupDao.insert(indTreeVO);
    }

    @Override
    public IndTreeVO queryIndGroupByInn(String parentId) {
        return indGroupDao.queryIndGroupByInn(parentId);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(IndTreeVO indTreeVO) {
        indGroupDao.update(indTreeVO);
    }

    @Override
    public IndTreeVO queryIndGroupById(String id) {
        return indGroupDao.queryIndGroupById(id);
    }

    @Override
    public List<IndTreeVO> queryChildIndGroupById(String indGroupId, Condition condition, Sorter sort, boolean containItself) {
        IndTreeVO indTreeVO = indGroupDao.queryIndGroupById(indGroupId);
        String innercode = indTreeVO.getInnercode();
        condition.addExpression("id",
                "select id from bp_indgroups where innercode like '"
                        + innercode + "%' ",
                "in");
        if (!containItself) {
            condition.addExpression("id", indGroupId, "<>");
        }
        return indGroupDao.queryIndGroupListByCondition(condition, sort);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteIndGroup(String id) {
        indGroupDao.deleteIndGroup(id);
        return true;
    }

    @Override
    public void changeVersion(IndTreeVO indTreeVO) {
        //修改后版本号+1
        indTreeVO.setVersion(indTreeVO.getVersion() + 1);
    }

    @Override
    public Page<IndGroupRltVO> queryChildInd(String innercode, Condition cond, Pageable page) {
        if(innercode==null){
            return indGroupDao.queryChildInd(cond, page); 
        }else{
            cond.addExpression("b.pk_indexgroup",
                    "(select id from bp_indgroups where innercode like'"
                            + innercode + "%' )",
                    "in");
            return indGroupDao.queryChildInd(cond, page);
        }
        
    }

    @Override
    public Page<IndexInfoVO> queryRemainInd(String id, Pageable page, Sorter sort, Condition cond) {
        cond.addExpression("a.id", "( select pk_index from bp_indgroup_rlt where pk_indexgroup='" + id + "') ", "not in");
        Page<IndexInfoVO> inds = indGroupDao.queryRemainInd(page, sort, cond);
        return inds;
    }

    @Override
    @Transactional(readOnly = false)
    public void addRelation(String groupId, String[] ids) {
        List<IndGroupRltVO> list = new ArrayList<IndGroupRltVO>();
        for (String id : ids) {
            IndGroupRltVO indGroupRltVO = new IndGroupRltVO();
            indGroupRltVO.setPk_index(id);
            indGroupRltVO.setPk_indexgroup(groupId);
            list.add(indGroupRltVO);
        }
        indGroupDao.insertIndGroupRlt(list);
    }

    @Override
    public List<IndGroupRltVO> queryRltByGroupId(String id) {
        return indGroupDao.queryRltByGroupId(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteRelation(String[] ids) {
        indGroupDao.deleteRelation(ids);
        return true;
    }

    @Override
    public void queryKpiTreeMap(ResultHandler handler) {
        
        indGroupDao.queryKpiTreeMap(handler);
    }

    @Override
    public Page<IndGroupRltVO> queryKpiPageByCondition(Condition condition, Pageable page) {
        
        return indGroupDao.queryChildInd(condition, page);
    }
}

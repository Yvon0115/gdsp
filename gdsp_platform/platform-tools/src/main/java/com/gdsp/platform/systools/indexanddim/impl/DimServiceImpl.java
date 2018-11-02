/*
 * 类名: DimServiceImpl
 * 创建人: wly    
 * 创建时间: 2016年2月22日
 */
package com.gdsp.platform.systools.indexanddim.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.systools.indexanddim.dao.IDimDao;
import com.gdsp.platform.systools.indexanddim.model.DimRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.DimValueVO;
import com.gdsp.platform.systools.indexanddim.service.IDimService;

/**
 * 维度管理实现类 <br/>
 * 
 * @author wly
 */
@Service
@Transactional(readOnly = true)
// 不启用事务管理 声明式的事务管理方法
public class DimServiceImpl implements IDimService {

    @Autowired
    private IDimDao        dimDao;
    @Resource
    private IDefDocService defDocService;

    @Override
    public Page<DimVO> queryDimByCondition(Condition condition, Pageable page) {
        return dimDao.queryDimByCondition(condition, page);
    }

    @Transactional
    @Override
    public boolean saveDim(DimVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            dimDao.update(vo);
        } else {
            dimDao.insert(vo);
        }
        return true;
    }

    @Override
    public boolean uniqueCheckName(DimVO vo) {
        return dimDao.existSameDimName(vo) == 0;
    }

    @Override
    public boolean uniqueCheckField(DimVO vo) {
        return dimDao.existSameDimField(vo) == 0;
    }

    @Override
    public DimVO load(String id) {
        return dimDao.load(id);
    }

    @Transactional
    @Override
    public boolean deleteDim(String... ids) {
        dimDao.deleteDim(ids);
        // 同步删除已添加维值信息
        dimDao.deleteRltForDim(ids);
        return true;
    }

    @Override
    public List<DimRltVO> queryDimValueByDimId(String dimId, Condition condition, Pageable pageable) {
        if (StringUtils.isEmpty(dimId))
            return null;
        condition.addExpression("rlt.pk_dim", dimId);
        return dimDao.queryDimValueByDimId(condition, pageable);
    }

    @Transactional
    @Override
    public boolean saveDimValue(DimValueVO vo, String dimId) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            dimDao.updateDimValue(vo);
        } else {
            dimDao.insertDimValue(vo);
            String dimValueId = vo.getId();
            DimRltVO dimRltVO = new DimRltVO();
            dimRltVO.setPk_dim(dimId);
            dimRltVO.setPk_dimvalue(dimValueId);
            dimDao.insertdimRltVO(dimRltVO);
        }
        return true;
    }

    @Override
    public DimValueVO loadDimValue(String id) {
        String addCond = "=(SELECT pk_dimvalue from bp_dim_rlt where id='" + id + "')";
        return dimDao.loadDimValue(id, addCond);
    }

    @Transactional
    @Override
    public boolean deleteDimValue(String... id) {
        if (id != null && id.length > 0) {
            //删除维值 
            dimDao.deleteDimValue(id);
            //删除维度关联的维值
            dimDao.deleteRltDimValue(id);
        }
        return true;
    }

    @Override
    public Map queryDimValue(String dimId) {
        String add = "select id,dimvaluememo,pk_fatherid from bp_dimvalue where id in (select pk_dimvalue from bp_dim_rlt where pk_dim='" + dimId + "') order by dimvalue";
        MapListResultHandler handler = new MapListResultHandler("pk_fatherid");
        dimDao.queryDimValue(handler, add);
        return handler.getResult();
    }

    @Override
    public List<DimRltVO> getDimRltByValIds(String[] dimValIDs) {
        return dimDao.getDimRltByValIds(dimValIDs);
    }
}

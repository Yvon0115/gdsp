package com.gdsp.platform.systools.indexanddim.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.dao.IDimDao;
import com.gdsp.platform.systools.indexanddim.dao.IIndexDimRelDao;
import com.gdsp.platform.systools.indexanddim.dao.IIndexManageDao;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.IdxDimRelVO;
import com.gdsp.platform.systools.indexanddim.service.IIdxDimRelService;

@Service
public class IndexDimRelServiceImpl implements IIdxDimRelService {

    @Autowired
    private IIndexDimRelDao idxDimRelDao;
    @Autowired
    private IIndexManageDao iIndexManageDao;
    @Autowired
    private IDimDao         iDimDao;

    @SuppressWarnings("rawtypes")
    @Override
    public Map queryIdxList() {
        MapListResultHandler handler = new MapListResultHandler("pk_fatherid");
        idxDimRelDao.queryIdxList(handler);
        return handler.getResult();
    }

    @Override
    public Page<IdxDimRelVO> queryDimListByIdxId(Condition condition, String indexid, Pageable page) {
        condition.addExpression("idx.indexid", indexid);
        return idxDimRelDao.queryDimListByIdxId(condition, page);
    }

    @Override
    public Page<DimVO> queryNoDimListByIdxId(String indexId, Pageable page, Condition condition) {
        condition.addExpression("r.id", "(select dimid from bp_idxdimrel where indexid ='" + indexId + "') ", "not in");
        return idxDimRelDao.queryNoDimListByIdxId(condition, page);
    }

    @Override
    public void saveDim(String indexId, String[] id) {
        List<IdxDimRelVO> list = new ArrayList<IdxDimRelVO>();
        for (String dimid : id) {
            IdxDimRelVO idxDimRelVO = new IdxDimRelVO();
            idxDimRelVO.setIndexid(indexId);
            idxDimRelVO.setDimid(dimid);
            list.add(idxDimRelVO);
        }
        idxDimRelDao.saveDim(list);
    }

    @Override
    public void deleteDim(String[] id) {
        idxDimRelDao.deleteDim(id);
    }

    @Override
    @Transactional
    public int isDimCite(String... id) {
        return idxDimRelDao.isDimCite(id);
    }

    @Override
    public int findIdxDimCount(String[] id) {
        return idxDimRelDao.findIdxDimCount(id);
    }

    @Override
    @Transactional
    public void saveExcelIdxDim(ArrayList<String[]> excelList, List<String> errorList) {
        List<IdxDimRelVO> list = new ArrayList<IdxDimRelVO>();
        if (excelList != null && excelList.size() > 0) {
            for (int i = 0; i < excelList.size(); i++) {
                String[] strings = excelList.get(i);
                String indexCode = strings[0];
                if (indexCode != null && !indexCode.isEmpty()) {
                    String indexName = strings[1];
                    if (indexName != null && !indexName.isEmpty()) {
                        String queryIdexId = iIndexManageDao.queryIdexId(indexCode, indexName);
                        if (queryIdexId != null) {
                            String dimName = strings[2];
                            if (dimName != null && !dimName.isEmpty()) {
                                String queryDimId = iDimDao.queryDimId(dimName);
                                if (queryDimId != null) {
                                    if (idxDimRelDao.isExistRelation(queryIdexId, queryDimId) == 0) {
                                        IdxDimRelVO idxDimRelVO = new IdxDimRelVO();
                                        idxDimRelVO.setIndexid(queryIdexId);
                                        idxDimRelVO.setDimid(queryDimId);
                                        list.add(idxDimRelVO);
                                    } else {
                                        errorList.add("第" + (i + 2) + "行，关系已存在");
                                    }
                                } else {
                                    errorList.add("第" + (i + 2) + "行，第三列，维度名称不存在");
                                }
                            } else {
                                errorList.add("第" + (i + 2) + "行，信息不完整");
                            }
                        } else {
                            errorList.add("第" + (i + 2) + "行，第一列，第二列，指标编码与指标名称不匹配");
                        }
                    } else {
                        errorList.add("第" + (i + 2) + "行，信息不完整");
                    }
                } else {
                    errorList.add("第" + (i + 2) + "行，信息不完整");
                }
            }
            if (list != null && list.size() > 0) {
                idxDimRelDao.insertIndDimId(list);
            }
        } else {
            errorList.add("无可导入的指标维度关联关系");
        }
    }

    @Override
    public List<String> queryDimIdsByIndexIds(String[] indexid) {
        return idxDimRelDao.queryDimIdsByIndexIds(indexid);
    }

    @Override
    public List<String> queryIndexIdsByDim(List<String> dimid) {
        return idxDimRelDao.queryIndexIdsByDim(dimid);
    }
}

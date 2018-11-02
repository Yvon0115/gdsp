package com.gdsp.platform.systools.indexanddim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.IdxDimRelVO;

public interface IIdxDimRelService {

    /**
     * 查询指标树
     * @return
     */
    @SuppressWarnings("rawtypes")
    Map queryIdxList();

    /**
     * 根据指标ID查询维度
     * @param indexid
     * @param condition
     * @param pageable
     * @return
     */
    Page<IdxDimRelVO> queryDimListByIdxId(Condition condition, String indexid, Pageable pageable);

    /**
     * 根据指标ID查询未关联维度
     * @param indexId
     * @param pageable
     * @return
     */
    Page<DimVO> queryNoDimListByIdxId(String indexId, Pageable pageable, Condition condition);

    /**
     * 保存维度
     * @param indexId
     * @param id
     */
    void saveDim(String indexId, String[] id);

    /**
     * 删除指标维度关联关系
     * @param id
     */
    void deleteDim(String[] id);

    /**
     * 判断维度是否被引用 <br/>
     * @param ids
     * @return
     */
    public int isDimCite(String... id);

    /**
     * 判断指标是否被指标维度关联关系引用
     * @param id
     * @return
     */
    int findIdxDimCount(String[] id);

    /**
     * 通过指标ID查询公共的维度ID
     * @param indexid
     * @return
     */
    List<String> queryDimIdsByIndexIds(String[] indexid);

    /**
     * 保存指标维度关联关系 <br/>
     * @param excelList
     * @param errorList
     */
    public void saveExcelIdxDim(ArrayList<String[]> excelList,
            List<String> errorList);

    /**
     * 通过维度ID查询公共的指标ID
     * @return
     */
    List<String> queryIndexIdsByDim(List<String> dimIds);
}

package com.gdsp.platform.systools.indexanddim.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.IdxDimRelVO;

/**
 * 指标维度管理关系数据访问层接口
 * @author Administrator
 */
@MBDao
public interface IIndexDimRelDao {

    /**
     * 获取指标树集合
     * @param handler
     */
    @SuppressWarnings("rawtypes")
    void queryIdxList(MapListResultHandler handler);

    /**
     * 根据指标ID获取维度信息
     * @param condition
     * @param page
     * @return
     */
    Page<IdxDimRelVO> queryDimListByIdxId(@Param("condition") Condition condition, Pageable page);

    /**
     * 根据指标ID查询为关联维度信息
     * @param addCond
     * @param page
     * @return
     */
    Page<DimVO> queryNoDimListByIdxId(@Param("condition") Condition condition, Pageable page);

    /**
     * 保存维度信息
     * @param list
     */
    void saveDim(List<IdxDimRelVO> list);

    /**
     * 删除指标维度关联关系
     * @param id
     */
    void deleteDim(String[] id);

    /**
     * 判断维度是否被引用 <br/>
     * @param id
     * @return
     */
    int isDimCite(String[] id);

    /**
     * 判断指标是否被指标关联关系引用
     * @param id
     * @return
     */
    int findIdxDimCount(String[] id);

    /**
     * 通过指标ID查询公共的维度ID
     * @return
     */
    List<String> queryDimIdsByIndexIds(String[] indexid);

    /**
     * 通过维度ID查询公共的指标ID
     * @return
     */
    List<String> queryIndexIdsByDim(List<String> dimid);

    /**
     * 判断指标维度关系是否已存在 <br/>
     * @param id
     * @return
     */
    int isExistRelation(@Param("indexid") String indexid, @Param("dimid") String dimid);

    /**
     * 保存指标维度关联关系 <br/>
     * @param list
     */
    void insertIndDimId(List<IdxDimRelVO> list);
}

package com.gdsp.integration.framework.param.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.model.AcParamRelation;

@MBDao
public interface IAcParamRelationDao {

    int deleteByPrimaryKey(String id);

    void deleteByIds(String[] ids);

    int insert(AcParamRelation record);

    int insertSelective(AcParamRelation record);

    AcParamRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AcParamRelation record);

    int updateByPrimaryKey(AcParamRelation record);

    List<String> queryParamIdListByReport(String reportId);

    Page<AcParam> queryParamPageByReport(@Param("reportId") String reportId, @Param("condition") Condition condition,
            @Param("sort") Sorter sort, Pageable page);
}
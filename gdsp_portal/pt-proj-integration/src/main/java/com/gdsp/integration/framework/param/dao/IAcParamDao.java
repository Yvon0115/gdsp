package com.gdsp.integration.framework.param.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.integration.framework.param.model.AcParam;

@MBDao
public interface IAcParamDao {

    int deleteByPrimaryKey(String id);

    int insert(AcParam record);

    int insertSelective(AcParam record);

    AcParam load(String id);

    int updateByPrimaryKeySelective(AcParam record);

    int updateByPrimaryKeyWithBLOBs(AcParam record);

    int updateByPrimaryKey(AcParam record);

    void deleteParamByIds(String[] ids);

    List<AcParam> queryAllParam();

    Page<AcParam> queryByCondition(@Param("condition") Condition condition, @Param("pageable") Pageable pageable, @Param("sort") Sorter sort);

    List<AcParam> queryByIds(List<String> ids);
}
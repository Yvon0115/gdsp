package com.gdsp.integration.framework.param.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.ResultHandler;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.integration.framework.param.model.AcParamLibrary;

@MBDao
public interface IAcParamLibraryDao {

    int deleteByPrimaryKey(String id);

    int insert(AcParamLibrary record);

    int insertSelective(AcParamLibrary record);

    AcParamLibrary load(String id);

    int updateByPrimaryKeySelective(AcParamLibrary record);

    int updateByPrimaryKey(AcParamLibrary record);

    void deleteParamLibraryByIds(String[] ids);

    List<AcParamLibrary> queryAllParamLibrary();

    int existsParamLibraryName(AcParamLibrary vo);

    List<AcParamLibrary> queryByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    @Select("<script>select * from ac_param_library <if test=\"id != null\">where id &lt;&gt; #{id} </if></script>")
    @ResultType(AcParamLibrary.class)
    void queryTreeMap(ResultHandler handler, @Param("id") String id);
    void queryTree(ResultHandler handler,@Param("condition") Condition condition,@Param("sort") Sorter sort);
}
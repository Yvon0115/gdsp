package com.gdsp.integration.framework.param.service;

import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.param.model.AcParamLibrary;

public interface IParamLibraryService {

    /**
     * 
     * @Title: addParamLbrary 
     * @Description: 插入参数类型
     * @param vo	参数类型VO
     * @return void    返回类型
     */
    public void addParamLbrary(AcParamLibrary vo);

    /**
     * 
     * @Title: updateParamLibrary 
     * @Description: 更新参数类型
     * @param vo	参数类型VO
     * @return void    返回类型
     */
    public void updateParamLibrary(AcParamLibrary vo);

    /**
     * 
     * @Title: deleteParamLibrary 
     * @Description: 删除参数类型
     * @param id	参数类型ID
     * @return void    返回类型
     */
    public void deleteParamLibrary(String id);

    /**
     * 
     * @Title: deleteParamLibrary 
     * @Description: 删除参数类型（删除多个）
     * @param ids	参数类型ID集合
     * @return void    返回类型
     */
    public void deleteParamLibraryByIds(String[] ids);

    /**
     * 
     * @Title: load 
     * @Description: 通过主键查询参数类型
     * @param id	参数类型ID
     * @return AcParamLibrary    返回参数类型VO
     */
    public AcParamLibrary load(String id);

    /**
     * 
     * @Title: queryAllParamLibrary 
     * @Description: 查询所有参数类型
     * @return List<AcParamLibrary>    返回参数类型VO集合
     */
    public List<AcParamLibrary> queryAllParamLibrary();

    /**
     * 
     * @Title: checkParamLibraryName 
     * @Description: 检查参数类型名称的唯一性
     * @param vo
     * @return
     * @return boolean    返回类型
     */
    public boolean checkParamLibraryName(AcParamLibrary vo);

    /**
     * 
     * @Title: queryByCondition 
     * @Description: 通过条件查询参数分类
     * @param condition
     * @param sort
     * @return
     * @return List<AcParamLibrary>    返回类型
     */
    public Map queryByCondition(Condition condition, Sorter sort);

    /**
     * @Title: queryParamLibraryTreeMap 
     * @Description: 获取参数分组树型，不包含自身节点，如若要包含，id传null值
     * @param id	自身节点ID,如若要包含，传null值
     * @return
     * @return Map<String,List<AcParamLibrary>>    返回类型
     */
    public Map<String, List<AcParamLibrary>> queryParamLibraryTreeMap(String id);
}

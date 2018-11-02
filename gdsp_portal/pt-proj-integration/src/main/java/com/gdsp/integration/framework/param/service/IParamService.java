package com.gdsp.integration.framework.param.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.model.TemplateVO;

public interface IParamService {

    /**
     * 
     * @Title: addParamLbrary 
     * @Description: 插入参数
     * @param vo	参数VO
     * @return void    返回类型
     */
    public void addParam(AcParam vo);

    /**
     * 
     * @Title: updateParamLibrary 
     * @Description: 更新参数
     * @param vo	参数VO
     * @return void    返回类型
     */
    public void updateParam(AcParam vo);

    /**
     * 
     * @Title: deleteParamLibrary 
     * @Description: 删除参数
     * @param id	参数ID
     * @return void    返回类型
     */
    public void deleteParam(String id);

    /**
     * 
     * @Title: deleteParamLibrary 
     * @Description: 删除参数（删除多个）
     * @param ids	参数ID集合
     * @return void    返回类型
     */
    public void deleteParamByIds(String[] ids);

    /**
     * 
     * @Title: load 
     * @Description: 通过主键查询参数
     * @param id	参数ID
     * @return AcParam   返回参数VO
     */
    public AcParam load(String id);

    /**
     * 
     * @Title: queryAllParamLibrary 
     * @Description: 查询所有参数
     * @return List<AcParam>    返回参数VO集合
     */
    public List<AcParam> queryAllParam();

    /**
     * 
     * @Title: queryByCondition 
     * @Description: 通用查询方法（带分页，带排序）
     * @param condition
     * @param pageable
     * @param sort
     * @return
     * @return Page<AcParam>    返回带分页的参数VO集合
     */
    public Page<AcParam> queryByCondition(Condition condition, Pageable pageable,
            Sorter sort);

    /**
     * 
     * @Title: queryByIds 
     * @Description: 根据ID集合查询参数列表
     * @param ids
     * @return
     * @return List<AcParam>    返回类型
     */
    public List<AcParam> queryByIds(List<String> ids);

    /**
     * 
     * @Title: loadQueryTemplate 
     * @Description: 查询某路径下的模板文件信息
     * @param path	模板路径，相对路径
     * @return
     * @return List<TemplateVO>    返回类型
     */
    public void loadQueryTemplate(String rootPath, String path, Map<String, List<TemplateVO>> map);
}

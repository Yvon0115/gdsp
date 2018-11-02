package com.gdsp.platform.config.global.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.global.model.ParamVO;

/**
 * 
* @ClassName: IParamService
* @Description: 参数管理服务类
* @author liuyongqiang
* @date 2015年10月28日 上午10:21:29
*
 */
public interface IParamService {

    /**
    * 新增参数定义(过时方法)
    * @param param  参数定义VO类
    * @return void    返回值说明
     */
	@Deprecated
    public void insertParamDef(ParamVO param);

    /**
    * 删除参数定义(过时方法)
    * @param id    参数定义主键
    * @return void    返回值说明
     */
	@Deprecated
    public void deleteParamDef(String id);

    /**
     * 更新参数(过时方法)
    * @param param    参数说明
    * @return void    返回值说明
    */
	@Deprecated
    public void updateParamDef(ParamVO param);

    /**
     * 
    * @Title: queryParamValue
    * @Description: 根据参数编码返回参数记录（唯一）
    * @param paramCode  参数唯一编码
    * @return    参数记录对象
    * @return Object    返回值说明
    *      */
    public Object queryParamValue(String paramCode);

    /**
     * 
    * @Title: setParamValue
    * @Description: 设置参数值
    * @param paramCode  参数编码
    * @param paramValue  参数值
    * @return void    返回值说明
    *      */
    public void setParamValue(String paramCode, Object paramValue);

    /**
     * 
    * @Title: queryParamDefList
    * @Description: 根据查询条件查询参数定义列表
    * @param condition  查询条件
    * @param page 分页信息
    * @param sort 排序条件
    * @return    参数说明
    * @return Page<ParamVO>  参数定义结果列表
    *      */
    public Page<ParamVO> queryParamDefList(Condition condition, Pageable page, Sorter sort);

    /**
     * 
    * @Title: restoreDefault
    * @Description: 恢复参数默认值
    * @param id  参数定义主键
    * @return void  
    *      */
    public void restoreDefault(String id);

    /**
     * 
    * @Title: loadParam
    * @Description: 根据ID返回参数记录（唯一）
    * @param id  参数唯一编码
    * @return ParamVO    参数记录对象
    *      */
    public ParamVO loadParam(String id);
}

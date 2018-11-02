package com.gdsp.platform.config.customization.service;



import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.customization.model.FuncDecDataIOVO;
import com.gdsp.platform.config.customization.model.FunctionDecVO;
/**
 * 
* @ClassName: IFunctionDecService
* (functionDec接口)
* @author songxiang
* @date 2015年10月28日 上午11:04:53
*
 */
public interface IFunctionDecService {
	
	/**
	 * 
	* @Title: queryFunctionDecVOListByCondition
	* (根据id查看菜单下的功能 )
	* @param id
	* @return    参数说明
	* @return List<FunctionDecVO>    返回值说明
	* 	 */
	List<FunctionDecVO> queryFunctionDecVOListByCondition(String id);
	
	/**
	 * 根据ID查询 功能说明配置VO
	 * @param id
	 * @return FunctionDecVO 
	 */
	public FunctionDecVO loadFunctionDecVOById(String id);

	/**
	 * 
	* @Title: queryFunctionDecVOsPages
	* (查看菜单下的功能 )
	* @param condition
	* @param page
	* @return    参数说明
	* @return Page<FunctionDecVO>    返回值说明
	* 	 */
    public Page<FunctionDecVO> queryFunctionDecVOsPages(Condition condition,Pageable page);
    /**
     * 
    * @Title: update
    * (修改某个菜单)
    * @param functionDecVO    参数说明
    * @return void    返回值说明
    *      */
	public void update(FunctionDecVO functionDecVO);
	/**
	 * 
	* @Title: queryMenuRegisterVOsByCondReturnMap
	* (树的加载)
	* @param condition
	* @param sort
	* @return    参数说明
	* @return Map    返回值说明
	* 	 */
	public Map queryMenuRegisterVOsByCondReturnMap(Condition condition,Sort sort);
	/**
	 * 根据功能ID查询需要导出的功能说明
	 * @param commonDirId 功能ID集合（以逗号隔开的ID）
	 * @return
	 */
	public List<FuncDecDataIOVO> findExportList(String commonDirId);
	
}

package com.gdsp.platform.config.customization.dao;




import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.customization.model.FuncDecDataIOVO;
import com.gdsp.platform.config.customization.model.FunctionDecVO;
import com.gdsp.platform.func.model.MenuRegisterVO;

/**
 * 
* @ClassName: IFunctionDecDao
* (功能菜单管理)
* @author songxiang
* @date 2015年10月28日 上午11:14:13
*
 */
@MBDao
public interface IFunctionDecDao {

	/**
	 * 
	* @Title: queryFunctionDecVOListByCondition
	* (根据id查看菜单下的功能)
	* @param id
	* @return    参数说明
	* @return List<FunctionDecVO>    返回值说明
	* 	 */
	@Select(" select * from st_menuregister where id=#{id}  and (isenable = 'Y' or isenable = 'y')") 
	List<FunctionDecVO> queryFunctionDecVOListByCondition(String id);
	
	/**
	 * 
	* @Title: update
	* (修改fileUrl)
	* @param functionDecVO    参数说明
	* @return void    返回值说明
	* 	 */
	@Update("update st_menuregister set fileUrl=#{fileUrl} where id=#{id}")
	void update(FunctionDecVO functionDecVO);
	  
	/**
	 * 根据id查询菜单
	 * @param id
	 * @return FunctionDecVO 功能说明
	 */
	@Select("select * from st_menuregister where id=#{id} and (isenable = 'Y' or isenable = 'y')")
	FunctionDecVO load(String id);
	
	/**
	 * 
	* @Title: queryFunctionDecVOsPages
	* (查看菜单下的功能)
	* @param condition
	* @param page
	* @return    参数说明
	* @return Page<FunctionDecVO>    返回值说明
	* 	 */
	@Select("<script> select * from st_menuregister where (isenable = 'Y' or isenable = 'y') "
			+ "<if test=\"condition!=null and condition._CONDITION_ != null\">and ${condition._CONDITION_}</if> </script>  ")
	Page<FunctionDecVO> queryFunctionDecVOsPages(@Param("condition") Condition condition, @Param("page") Pageable page );
	

	/**
	 * 
	* @Title: queryMenuRegisterVOMapListByCondition
	* (查看树)
	* @param condition
	* @param sort
	* @param handler    参数说明
	* @return void    返回值说明
	* 	 */
	@Select("<script> select * from st_menuregister  where (isenable = 'Y' or isenable = 'y') <if test=\"condition!=null and condition._CONDITION_ != null\">and ${condition._CONDITION_}</if>"
			+ " <if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if>  </script> ")
	@ResultType(MenuRegisterVO.class)
	void queryMenuRegisterVOMapListByCondition(@Param("condition") Condition condition, @Param("sort") Sort sort,MapListResultHandler handler);

	/**
	 * 
	* @Title: findExportFunDecList
	* (导出功能)
	* @param commonDirIdArr
	* @return    参数说明
	* @return List<FuncDecDataIOVO>    返回值说明
	* 	 */
	List<FuncDecDataIOVO> findExportFunDecList(@Param("commonDirIdArr")String[] commonDirIdArr);
}

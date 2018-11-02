package com.gdsp.platform.config.customization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.config.customization.model.FuncDecVO;

/**
 * 
* @ClassName: IFuncDecDao
* (功能管理)
* @author songxiang
* @date 2015年10月28日 上午11:09:15
*
 */

@MBDao
public interface IFuncDecDao extends ICrudDao<FuncDecVO>{

	/**
	 * 
	* @Title: load
	* (加载操作对象)
	* @param id
	* @return    参数说明
	* @return FuncDecVO    返回值说明
	* 	 */
	@Select("select * from st_funcdec where id=#{id}")
	FuncDecVO load(String id);
	
	/**
	 * 
	* @Title: delete
	* (删除某功能)
	* @param id    参数说明
	* @return void    返回值说明
	* 	 */
	@Delete("delete from st_funcdec where id=#{id}")
	void delete(String id);
	/**
	 * @return 
	 * 
	* @Title: sava
	* (新增某功能)
	* @param funcDecVO    参数说明
	* @return void    返回值说明
	* 	 */
	@Insert("insert into st_funcdec (id ,name,memo,fileUrl,menuid,createtime,createby,lastmodifytime,lastmodifyby,version) "
			+ "values (#{id},#{name},#{memo},#{fileUrl},#{menuid},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
	int insert(FuncDecVO funcDecVO);
	/**
	 * 
	* @Title: queryFuncDecListByCondition
	* (根据菜单id查看功能)
	* @param menuid
	* @return    参数说明
	* @return List<FuncDecVO>    返回值说明
	* 	 */
    @Select(" select * from st_funcdec where menuid=#{menuid} order by sortnum") 
	List<FuncDecVO> queryFuncDecListByCondition(String menuid);
      /**
     * @return 
       * 
      * @Title: update
      * (批量修改)
      * @param funcDecVO    参数说明
      * @return void    返回值说明
      *        */
     int update(FuncDecVO funcDecVO);
    
     /**
      *  
     * @Title: deleteByMenuId
     * (根据menu_id 删除)
     * @param menuid    参数说明
     * @return void    返回值说明
     *       */
    @Delete("delete from st_menuregister where id=#{id}")
	void deleteByMenuId(@Param("id")String id);

    /**
     *  
    * @Title: findDataSourceNlslang
    * (查询数据库编码集)
    * @param menuid    参数说明
    * @return void    返回值说明
    *      */
    @Select("select userenv('language') from dual")
	String findDataSourceNlslang();
    
}

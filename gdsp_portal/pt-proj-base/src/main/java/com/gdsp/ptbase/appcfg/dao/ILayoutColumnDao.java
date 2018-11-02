package com.gdsp.ptbase.appcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;

/**
 * 列布局dao
 *
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
@MBDao
public interface ILayoutColumnDao {

    /**
     * 根据列布局id加载所有布局列
     * @param layoutId 布局主键
     * @return 布局列
     */
    List<LayoutColumnVO> findLayoutColumns(String layoutId);

    
    /**
     * 查询排序号
     * @return 最大排序号
     */
    @Select("select max(sortnum) from ac_layout_column")
    public int findSortnum();
    
	/**
	 * 插入自定义布局的 对照数据
	 * @param layoutColumnVO 布局对照表
	 */
	void insert(LayoutColumnVO layoutColumnVO);


	/**
	 * 删除自定义布局
	 * @param layout_id
	 */
    @Delete("delete from ac_layout_column where layout_id=#{layout_id}")
	void delete(@Param("layout_id")String layout_id);


    
}

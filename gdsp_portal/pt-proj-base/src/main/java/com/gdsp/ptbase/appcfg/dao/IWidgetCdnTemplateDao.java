package com.gdsp.ptbase.appcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.ptbase.appcfg.model.WidgetCdnTemplateVO;

/**
 * 
* @ClassName: IWidgetCdnTemplateDao
* (资源模板管理)
* @author songxiang
* @date 2015年10月28日 下午12:46:03
*
 */
@MBDao
public interface IWidgetCdnTemplateDao {

    /**
     * 
    * @Title: insert
    * (添加操作)
    * @param widgetParamsTypeVO    参数说明
    * @return void    返回值说明
    *      */
    @Insert("insert into ac_cdn_template (id,name,code,type,comments,createtime,createby,lastmodifytime,lastmodifyby,version) "
            + "values (#{id},#{name},#{code},#{type},#{comments},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insert(WidgetCdnTemplateVO widgetParamsTypeVO);

    /**
     * 
    * @Title: queryByCondition
    * (查询所有资源模板)
    * @param condition
    * @param page
    * @return    参数说明
    * @return Page<WidgetCdnTemplateVO>    返回值说明
    *      */
    Page<WidgetCdnTemplateVO> queryByCondition(@Param("condition") Condition condition, Pageable page);

    /**
     * 
    * @Title: update
    * (更新操作)
    * @param widgetParamsTypeVO    参数说明
    * @return void    返回值说明
    *      */
    @Update("update ac_cdn_template set name=#{name},code=#{code},type=#{type},comments=#{comments},lastModifyBy=#{lastModifyBy}, lastModifyTime=#{lastModifyTime:BIGINT}, version=#{version} where id=#{id}")
    void update(WidgetCdnTemplateVO widgetParamsTypeVO);

    /**
     * 
    * @Title: load
    * (加载操作对象)
    * @param id
    * @return    参数说明
    * @return WidgetCdnTemplateVO    返回值说明
    *      */
    @Select("select * from ac_cdn_template where id=#{id}")
    WidgetCdnTemplateVO load(String id);

    /**
     * 
    * @Title: deletes
    * (删除操作)
    * @param ids    参数说明
    * @return void    返回值说明
    *      */
    @Delete("<script>delete from ac_cdn_template  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deletes(String[] ids);

    /**
     * 
    * @Title: queryByConditionNoPage
    * (查询所以资源模板)
    * @param condition
    * @return    参数说明
    * @return List<WidgetCdnTemplateVO>    返回值说明
    *      */
    List<WidgetCdnTemplateVO> queryByConditionNoPage(@Param("condition") Condition condition);
}

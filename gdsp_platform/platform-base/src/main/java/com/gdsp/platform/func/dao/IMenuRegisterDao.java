package com.gdsp.platform.func.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.func.model.MenuRegisterVO;

/**
 * 菜单注册DAO
 * @author zhouyuh
 *
 */
@MBDao
public interface IMenuRegisterDao {

    @Insert("insert into st_menuregister (id,funcode,funname,parentid,funtype,dispcode,innercode,ispower,url,helpname,hintinf,isenable,memo,createtime,createby,lastmodifytime,lastmodifyby,version,pageid,safelevel,isreport) "
            + "values (#{id},#{funcode},#{funname},#{parentid},#{funtype},#{dispcode},#{innercode},#{ispower},#{url},#{helpname},#{hintinf},#{isenable},#{memo},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version},#{pageid},#{safeLevel},#{isreport})")
    void insert(MenuRegisterVO menuRegVo);

    @Update("update st_menuregister set isreport=#{isreport},funcode=#{funcode},funname=#{funname},parentid=#{parentid},funtype=#{funtype},dispcode=#{dispcode},ispower=#{ispower},url=#{url},helpname=#{helpname},hintinf=#{hintinf},isenable=#{isenable},memo=#{memo},"
            + "lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy} ,version=#{version}+1,safelevel=#{safeLevel} where id=#{id}")
    void update(MenuRegisterVO menuRegVo);

    @Delete("<script>delete from st_menuregister where id=#{id} </script>")
    void delete(String ids);

    @Select("select * from st_menuregister where id=#{id} and (isenable = 'Y' or isenable = 'y')")
    MenuRegisterVO load(String id);

    @Select("select * from st_menuregister where url=#{url} and (isenable = 'Y' or isenable = 'y')")
    MenuRegisterVO loadMenuByMenuUrl(String url);

    @Select("select m.* from st_menuregister m inner join st_pageregister p on m.id=p.menuid  where p.url=#{url} and (m.isenable = 'Y' or m.isenable = 'y')")
    MenuRegisterVO loadMenuByPageUrl(String url);

    // 根据条件查询
    @Select("<script> select * "
    		+ "from st_menuregister "
    		+ "where (isenable = 'Y' or isenable = 'y')  <if test=\"condition!=null and condition._CONDITION_ != null\">and ${condition._CONDITION_}</if>"
            + " <if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if>  </script> ")
    List<MenuRegisterVO> queryMenuRegisterVOListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    /**
     * 查询菜单树 map
     * @author xue
     * @since 2017年3月13日
     * 
     * @param condition 条件
     * @param sort 排序
     * @param handler 树结构处理器
     */
    @Select("<script> select * from st_menuregister where (isenable = 'Y' or isenable = 'y') "
            +"<if test=\"condition!=null and condition._CONDITION_ != null\"> and ${condition._CONDITION_}</if> <if test=\"sort != null\">" 
            +" order by <foreach collection=\"sort\" item=\"order\" separator=\",\"> ${order.property} ${order.direction}</foreach></if>  </script> ")
    @ResultType(MenuRegisterVO.class)
    void queryMenuRegisterVOMapListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort, MapListResultHandler handler);

    @Select("<script> select * from st_menuregister where (isenable = 'Y' or isenable = 'y') <if test=\"condition!=null and condition._CONDITION_ != null\"> and ${condition._CONDITION_}</if></script>  ")
    Page<MenuRegisterVO> queryMenuRegisterVOsPages(@Param("condition") Condition condition, @Param("page") Pageable page);

    @Select("<script>select id from st_menuregister sm where (sm.isenable = 'Y' or sm.isenable = 'y') and sm.id in <foreach collection=\"menuIds\" item=\"menuId\" separator=\",\" open=\"(\" close=\")\" >'${menuId}'</foreach> and sm.funtype in (0,2,3,4)</script>")
    List<String> queryMenuIdsByType234(@Param("menuIds") String[] menuIds);

    @Select("select parentid from st_menuregister where id=#{menuId} and (isenable = 'Y' or isenable = 'y')")
    String queryMenuParentId(@Param("menuId") String menuId);

    @Select("<script>select * from st_menuregister m where (m.isenable = 'Y' or m.isenable = 'y') <if test=\"idSet != null\">and m.id in <foreach collection=\"idSet\" item=\"id\" separator=\",\" open=\"(\" close=\")\">'${id}'</foreach></if></script>")
    @ResultType(MenuRegisterVO.class)
    void queryMenuMapByIds(@Param("idSet") Set idSet, MapListResultHandler handler);

    List<MenuRegisterVO> queryMenuPathByMenuId(@Param("menuId") String menuId);

    @Select("select is_system_menu from st_menuregister where id=#{id} and (isenable = 'Y' or isenable = 'y')")
    String isSystemMemu(String id);

    @Update("update st_menuregister set parentid=#{parentid},innercode=#{innercode},lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy} ,version=#{version}+1 where id=#{id}")
    void updateParentMenuId(MenuRegisterVO menuRegVo);

    @Delete("delete from st_menuregister where pageid=#{pageId}")
    void deleteByPageId(String pageId);

    @Select("<script>select count(1) from st_menuregister where (isenable = 'Y' or isenable = 'y') and funname = #{funname} and parentid=#{parentid} <if test='id'>and id&lt;&gt;#{id}</if></script>")
    public int existSameName(@Param("funname") String funname, @Param("parentid") String parentid, @Param("id") String id);

    @Update("update st_menuregister set innercode=#{innercode}  where id=#{id} and (isenable = 'Y' or isenable = 'y')")
    void updateInnercode(MenuRegisterVO menuRegVo);

    @Select("select id,funname,funtype,url,pageid from st_menuregister where isenable = 'Y' or isenable = 'y'")
    List<MenuRegisterVO> queryAllMenuList();
    @Select("select count(*) from st_menuregister where pageid=#{pageid} and (isenable = 'Y' or isenable = 'y')")
    int loadPageID(String pageid);
    
    // 根据条件查询用户的功能菜单
    List<MenuRegisterVO> queryMenuListByCondForPower(@Param("addCond") String addCond);
    
    // 查询过滤时效的用户菜单
    List<MenuRegisterVO> queryAgingFilteredMenuList(@Param("condition") Condition condition, @Param("addCond") String addCond);
    
}

package com.gdsp.platform.grant.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.model.UserDefaultPageVO;
import com.gdsp.platform.grant.role.model.RoleVO;

/**
 * @version 2015/6/25.
 */
@MBDao
public interface IPowerMgtDao {

    @Select("<script>select distinct ma.* "
    		+ "from st_menuregister ma  left join rms_power_menu mp on mp.resource_id = ma.id "
    		+ "where ma.isenable in ('y', 'Y')  <if test=\"addCond != null and addCond!=''\"> and (${addCond})</if> order by ma.dispcode,ma.innercode </script> ")
    List<MenuRegisterVO> queryMenuListByAddCond(@Param("addCond") String addCond);

    /**
     * 根据条件查询有权限的菜单列表
     * @param addCond 附加条件
     * @param condition 自由条件
     * @param page 分页请求
     * @return 分页数据
     */
    Page<MenuRegisterVO> queryMenuPageByCondForPower(@Param("addCond") String addCond, @Param("condition") Condition condition, Pageable page);

    /**
     * 根据条件所有的菜单列表
     * @param addCond 附加条件
     * @param condition 自由条件
     * @param page 分页请求
     * @return 分页数据
     */
    Page<MenuRegisterVO> queryMenuPageByCond(@Param("addCond") String addCond, @Param("condition") Condition condition, Pageable page);

    @Select("<script>select distinct ma.*,sf.doc_name as funname_safelevel from st_menuregister ma  "
            + "left join rms_power_menu mp on mp.resource_id = ma.id "
            + "left join cp_defdoc sf on ma.safelevel=sf.id "
            + "where ma.isenable in ('y', 'Y') "
            + "<if test=\"addCond != null and addCond!=''\">and (${addCond})</if> order by ma.dispcode,ma.innercode </script> ")
    @ResultType(MenuRegisterVO.class)
    void queryMenuMapByAddCond(@Param("addCond") String addCond, MapListResultHandler handler);

    void queryMenuMapByCondForPower(@Param("addCond") String addCond, MapListResultHandler handler);

    @Delete("<script>delete from rms_power_menu  where pk_role in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean deleteRoleMenuPower(String[] roleIDs);

    @Delete("<script>delete from rms_power_page  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deletePagePower(String[] ids);

    @Delete("<script>delete from rms_power_page  where pk_role in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean deleteRolePagePower(String[] roleIDs);

    @Delete("delete from rms_power_page  where resource_id = #{pageID} ")
    void deletePagePowerByPageID(String pageID);

    void insert(PowerMgtVO powerMgtVO);

    @Insert("insert into rms_power_page (id,pk_role,resource_id,objtype,createby,createtime ) "
            + "values (#{id},#{pk_role},#{resource_id},#{objtype},#{createBy},#{createTime:BIGINT})")
    void insertPagePower(PagePowerVO vo);

    List<PowerMgtVO> queryMenuRoleListByRoleId(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    //通过角色id查询出菜单id
    List<String> queryMenuIdsByRoleId(@Param("condition") Condition condition, @Param("sort") Sorter sort);
    
    @Select("<script>select id,funname,parentid as menuid,dispcode from st_menuregister where (isenable = 'Y' or isenable = 'y') and funtype = 4 union select sp.id,sp.funname,sp.menuid, sp.dispcode from st_pageregister sp inner join ac_page ap on sp.pageid = ap.id order by dispcode,funname <if test=\"addCond != null and addCond!=''\">where (${addCond}) </if></script> ")
    @ResultType(PageRegisterVO.class)
    void queryPageListForPowerByAddCond(@Param("addCond") String addCond, MapListResultHandler handler);

    @Select("select sp.id,sp.funname,sp.menuid,sp.dispcode from st_pageregister sp inner join ac_page ap on sp.pageid = ap.id where sp.menuid=#{menuID} order by sp.dispcode,sp.funname")
    List<PageRegisterVO> queryPageListByMenu(String menuID);

    Page<PagePowerVO> queryPageUserByAddCond(@Param("pageID") String pageID, @Param("condition") Condition condition, Pageable page);

    /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
    // 更改前
    //    Page<PagePowerVO> queryPageRoleByAddCond(@Param("condition") Condition condition, Pageable page);
    // 更改后
    List<PagePowerVO> queryPageRoleByAddCond(@Param("condition") Condition condition);
    /**---------------------- 更改至此结束 ----------------------------------*/
    

    @Select("<script>select id, pk_role, resource_id, createby, createtime from rms_power_menu m <trim prefix=\"where\" prefixOverrides=\"and |or \"><if test=\"condition!= null\">${condition._CONDITION_}</if></trim><if test=\"sort != null\"> order by	<foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if> </script> ")
    @ResultType(PowerMgtVO.class)
    void queryPowerMgtMapByRoleId(@Param("condition") Condition condition, @Param("sort") Sorter sort, MapListResultHandler handler);

    /** 根据用户id查询页面菜单 */
    @Select("select * from (select m.id as id,m.funname as funname,m.funcode as funcode,m.dispcode as dispcode,m.funtype as funtype,m.helpname as helpname,m.hintinf as hintinf,m.innercode as innercode,p.PAGEID as pageid,p.FUNNAME as pageName,p.url as url,pp.objtype, p.id as tabid, 'PageRegisterVO' as tabEntityType from st_menuregister m inner join st_pageregister p on m.id=p.menuid inner join rms_power_page pp on p.id = pp.resource_id where (isenable = 'Y' or isenable = 'y') and funtype=4 and (pp.pk_role=#{userID} or pp.pk_role in (select pk_role from rms_user_role where pk_user = #{userID} ) )  order by m.dispcode,pp.objtype,p.dispcode) menuInf ")
    List<MenuRegisterVO> queryPageMenuListByUser(String userID);

    @Select("select count(1) from rms_power_page rp inner join st_pageregister p on rp.resource_id = p.id where p.pageid = #{pageID} and (rp.pk_role = #{userID} or rp.pk_role in (select pk_role from rms_user_role where pk_user=#{userID}))")
    public int existPowerPage(@Param("pageID") String pageID, @Param("userID") String userID);

    @Select("select count(1) from rms_power_menu rm inner join st_menuregister m on rm.resource_id = m.id where (m.isenable = 'Y' or m.isenable = 'y') and m.pageid = #{pageID} and (rm.pk_role = #{userID} or rm.pk_role in (select pk_role from rms_user_role where pk_user=#{userID}))")
    public int existPowerMenu(@Param("pageID") String pageID, @Param("userID") String userID);

    public int existPowerForMenuByUrl(@Param("menuUrl") String menuUrl, @Param("userID") String userID);

    @Delete("<script>delete from rms_power_menu   where pk_role=#{userId} and resource_id in <foreach collection='userMenuList' index='index' item='item' open='(' separator=',' close=')'>#{item.id}</foreach></script>")
    void deleteUserMenuPower(@Param("userMenuList") List<MenuRegisterVO> userMenuList, @Param("userId") String userId);

    /** 查询菜单是否关联到了角色 */
    List<PowerMgtVO> isConnectToRole(@Param("id") String id);

    /** TODO 无引用  待删除 */
//    int getNumPageUserByAddCond(@Param("condition")Condition cond);
    
    /** 查询用户的默认首页 */
    List<UserDefaultPageVO> queryUserDefaultPages(@Param("userID")String userID);
    
    /** 查询角色菜单资源关系*/
    List<PowerMgtVO> findAllRoleMenuList();
    
    /** 查询角色页面资源关系*/
    List<PowerMgtVO> findAllRolePageList();
    
    /**
     * 批量插入
     * @param list
     */
	void insertBatch(List<PowerMgtVO> list);
	
	/**
	 * 根据角色id和菜单id数组删除角色和菜单关联关系
	 * @param roleId 角色id
	 * @param deleteIDs 菜单id数组
	 */
	void deleteByRoleIdAndMenuIds(@Param("roleId")String roleId, @Param("menuIds")String[] menuIds);
	
	/**
     * 根据主键查找角色和菜单关系
     * @param id
     * @return
     */
	PowerMgtVO loadMenu(@Param("id") String id);
	
	/**
     * 根据主键查找角色和页面关系
     * @param id
     * @return
     */
	PowerMgtVO loadPage(@Param("id") String id);
	
	/****
	 * 根据角色id查询出与它关联的页面
	 * @param roleids
	 * @return
	 */
	List<MenuRegisterVO> queryPageListByRole(@Param("list") List<String> roleids);
	
}

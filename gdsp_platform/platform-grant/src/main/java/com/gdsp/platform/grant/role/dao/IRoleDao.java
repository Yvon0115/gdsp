package com.gdsp.platform.grant.role.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.role.model.RoleVO;

/**
 * @version 2015/6/25.
 */
@MBDao
public interface IRoleDao {

    // lyf 2017.01.06修改 原因：角色时效相关，新增了权限时效时长和时效单位字段，所以修改了更新的sql
    @Insert("insert into rms_role (id ,rolename,pk_org ,memo,agingLimit,createtime,createby,lastmodifytime,lastmodifyby,version,permissionaging,agingunit) "
            + "values (#{id},#{rolename},#{pk_org},#{memo},#{agingLimit},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version},#{permissionAging},#{agingUnit})")
    void insert(RoleVO role);

    // lyf 2017.01.06修改 原因：角色时效相关，新增了权限时效时长和时效单位字段，所以修改了更新的sql
    @Update("update rms_role set rolename=#{rolename},pk_org=#{pk_org},memo=#{memo},agingLimit=#{agingLimit},"
            + "lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy},version=#{version},permissionaging=#{permissionAging},agingunit=#{agingUnit} where id=#{id}")
    void update(RoleVO role);

    @Delete("<script>delete from rms_role  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    @Select("select r.* from rms_role r where r.id=#{id}")
    RoleVO load(String id);

    Page<RoleVO> queryRoleByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    List<RoleVO> queryRoleListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    /***
     * 去除分页参数 Pageable
     * 返回类型改为list,在前台实现分页
     * @author wqh 
     * @since 2016年12月26日
     */
    @Select("<script>select r.* from rms_role r  <if test=\"addCond != null and addCond!=''\">where (${addCond}) </if><trim prefix=\"and\" prefixOverrides=\"and |or \"> <if test=\"condition!= null\">${condition._CONDITION_}</if> </trim> order by  rolename </script> ")
//    Page<RoleVO> queryRoleByAddCond(@Param("condition") Condition condition, @Param("addCond") String addCond, Pageable page);
    List<RoleVO> queryRoleByAddCond(@Param("condition") Condition condition, @Param("addCond") String addCond);

    /** TODO 无调用者  待确认后删除 */
//    @Select("<script>select r.* from rms_user_role ur inner join rms_role r on ur.pk_role = r.id <if test=\"addCond != null and addCond!=''\">where (${addCond}) </if></script> ")
//    List<RoleVO> queryRoleByAddCond2(@Param("addCond") String addCond);

    @Select("<script>select * from rms_role  "
            + "<trim prefix=\"where\" suffixOverrides=\"and |or \">"
            + " 1=1 "
            + " <if test=\"condition!= null\">and  ${condition._CONDITION_}</if>"
            + " <if test=\"addCond != null and addCond!=''\">and  (${addCond}) </if>"
            + " </trim>"
            + "</script> ")
    Page<RoleVO> queryRolePorwer(@Param("addCond") String addCond, @Param("condition") Condition condition,
            Pageable pageable);

    @Select("<script>select count(1) from rms_role "
    		 + " <trim prefix=\"where\" prefixOverrides=\"and |or \">"
             + " <if test=\"rolename != null and rolename!=''\">rolename = #{rolename}</if>"
             + " <if test=\"pk_org != null and pk_org!=''\">and pk_org = #{pk_org}</if>"
             + " <if test=\"id != null and id!=''\">and (id&lt;#{id} or id&gt;#{id})</if>"
             + " </trim>"
    		 + " </script>")
    public int existSameRole(RoleVO role);

    @Select("select * from rms_role where rolename=#{roleName}")
    public RoleVO queryRoleByName(String roleName);

    /**
     * 根据机构id集合查询角色 - 单表查询<br>
     * @since 2016年12月27日  查询sql更改（原因：权限拆分）
     * @author wqh
     * @param list
     */
    public List<RoleVO> queryRoleListByOrgIds(List orgIdList);

//    /** 根据用户id查角色   role中不允许出现的sql  TODO 待删除 */
//    @Select("<script>select r.* from rms_role r,rms_user_role ur where r.ID = ur.PK_ROLE and ur.PK_USER=#{id}</script> ")
//    List<RoleVO> queryRolePorwerPub(String id);
    
    /**
     * 根据机构id查询单个机构下的角色 - 单表查询
     * @param pk_org
     * @since 2016年12月27日
     */
    List<Map<String,String>> queryRoleList(String pk_org);
    
    /**
     * 查询所有角色 - 单表查询
     * @since 2016年12月27日
     */
    List<RoleVO> findAllRoleList();
    
    /**
     * 更新角色时效控制状态
     * @author wqh 2016/12/16
     */
    void updateRoleAgingStatus(@Param("roleID")String roleID, @Param("agingLimit")String agingLimit);
    
}

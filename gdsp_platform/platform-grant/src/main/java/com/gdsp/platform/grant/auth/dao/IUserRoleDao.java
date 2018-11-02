package com.gdsp.platform.grant.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.role.model.RoleVO;

/**
 * @version 2015/6/25.
 */
@MBDao
public interface IUserRoleDao {

	/**	添加角色用户关联 */
    @Insert("insert into rms_user_role (id,pk_role,pk_user,aging_enddate,createtime,createby,isprompted) "
            + "values (#{id},#{pk_role},#{pk_user},#{agingEndDate:BIGINT},#{createTime:BIGINT},#{createBy},#{isPrompted})")
    void insert(UserRoleVO userRoleVO);

    @Delete("<script>delete from rms_user_role  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String... ids);

    @Delete("<script>delete from rms_user_role  where pk_user in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void removeByUser(String[] userIds);

    @Delete("<script>delete from rms_user_role  where pk_role in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void removeByRole(String[] roleIds);
    
    /** 根据角色ID查询关联用户 - 联表查询  TODO 待删除 */
//    @Select("<script>select u.*,ur.aging_enddate as agingEndDate from rms_user_role ur inner join rms_user u on ur.pk_user = u.id where ur.pk_role=#{roleID} <if test=\"sort != null\">order by <foreach collection=\"sort\" item=\"order\" separator=\",\"> ${order.property} ${order.direction}</foreach></if> </script>")
//    List<UserVO> queryUserListByRoleId(@Param("roleID") String roleID, @Param("sort") Sorter sort);

    /** 无调用者  TODO 待删除 */
//    @Select("select r.* from rms_user_role ur inner join rms_role r on ur.pk_role = r.id where ur.pk_user=#{userID}")
//    public Page<RoleVO> queryRoleByUserId(@Param("userID") String userID, Pageable page);
    
/*
    @Select("select "
            + "r.id,"
            + "r.rolename,"
            + "r.pk_org,"
            + "r.memo,"
            + "r.aging_limit as agingLimit,"
            + "r.createBy,"
            + "r.createTime,"
            + "r.lastModifyTime,"
            + "r.lastModifyBy,"
            + "r.version, "
            + "ur.aging_enddate as agingEndDate "
            + "from rms_user_role ur "
            + "inner join rms_role r "
            + "on ur.pk_role = r.id "
            + "where ur.pk_user=#{userID}")
    List<RoleVO> queryRoleListByUserId(String userID);
*/
//    Page<RoleVO> queryRoleUserByRoleId(@Param("roleId") String roleId, @Param("loginUserID") String loginUserID, Pageable page);

    /**
     * 根据角色id和用户有权限的角色id删除关联表记录
     * @param roles
     * @param ids
     */
    @Delete("<script>delete from rms_user_role where pk_role = #{id} and "
            + "pk_role in <foreach collection='roles' index='index' item='item' open='(' separator=',' close=')'>#{item.id}</foreach></script>")
    void deleteUserRole(@Param("roles") List<RoleVO> roles, @Param("id") String id);

    @Delete("<script>delete from rms_user_role where pk_role=#{roleId} and pk_user in <foreach collection='userids' index='index' item='item' open='(' close=')' separator=','>#{item}</foreach></script>")
    void deleteUserRoles(@Param("roleId") String roleId, @Param("userids") String[] id);

    /** 根据角色id查询关联的用户id集合 */
    @Select("select pk_user from rms_user_role where pk_role=#{roleID}")
    List<String> queryUserIdsByRoleId(String roleID);
    
    /** 根据用户id查询关联的角色id集合 */
    @Select("select pk_role from rms_user_role where pk_role=#{userID}")
    List<String> queryRoleIdsByUserId(String userID);

    /**通过用户id从关联表中查询关联关系  lyf 2016-12-16 */
    List<UserRoleVO> queryUserRoleRelationsByUserId(String userID);
    
    /** 无引用    TODO 待确认后删除 */
//    List<UserVO> queryUsersByRoleIDAndUserID(@Param("roleID") String roleID, @Param("loginUserID") String loginUserID, @Param("userType") int userType);

    /** 根据条件查询关联关系 */
    List<UserRoleVO> queryUserRoleRelationsByCondition(@Param("condition") Condition condition);
    
    /** 无引用  TODO 待确认后删除 */
//    List<UserVO> queryUsersByRoleIDAndUserIDAndUserName(@Param("roleID") String roleID,
//            @Param("loginUserID") String loginUserID, @Param("userType") int usertype, @Param("username") String username);
    
    /**
     * 修改角色用户关联关系 - 权限时效
     * @param userRoleVO
     */
    void update(UserRoleVO userRoleVO);
    
    /**
     * 查询所有用户角色关联关系
     * @return
     */
    public List<UserRoleVO> queryAllRelations();
    
    /**
     * 查询所有用户角色关联关系(单表查询)
     * @return
     */
    public List<UserRoleVO> findAllRelations();
    
    /**
     * 根据id删除关联表记录
     * @param ids  关联表主键
     */
    public void deleteRelationsByIds(@Param("ids")String[] ids);
    
    /**
     * 标记已发送权限到期通知的用户
     * @param ids  用户角色关联表主键
     */
	void updateIsPromptedByIds(@Param("ids")String[] ids);
	
	/**
	 * 根据主键查找用户和角色关系
	 * @param id
	 * @return
	 */
	UserRoleVO load(@Param("id") String id);
}

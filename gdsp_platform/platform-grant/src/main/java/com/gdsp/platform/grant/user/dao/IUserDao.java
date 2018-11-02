package com.gdsp.platform.grant.user.dao;

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
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * @version 2015/6/25.
 */
@MBDao
public interface IUserDao {

    @Insert("INSERT INTO rms_user ("
            + "id, account, username, user_password, mobile, email, sex, pk_org, tel, memo,"
            + "usertype,onlyadmin,isreset,update_pwd_time,islocked,"
            + "isdisabled,abletime,disabletime,"
            + "createtime,createby,lastmodifytime,lastmodifyby,version,origin) "
            + "VALUES ("
            + "#{id},#{account},#{username},#{user_password},#{mobile},#{email},#{sex},#{pk_org},#{tel},#{memo},"
            + "#{usertype},#{onlyadmin:BOOLEAN},#{isreset:BOOLEAN},#{update_pwd_time:BIGINT},#{islocked:BOOLEAN},"
            + "#{isdisabled:BOOLEAN},#{abletime:BIGINT},#{disabletime:BIGINT},"
            + "#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version},#{origin}"
            + ")")
    void insert(UserVO user);

    @Update("update rms_user set "
            + "account=#{account},username=#{username},usertype=#{usertype},"
            + "onlyadmin=#{onlyadmin:BOOLEAN},mobile=#{mobile},email=#{email},"
            + "sex=#{sex},pk_org=#{pk_org},isreset=#{isreset:BOOLEAN},islocked=#{islocked:BOOLEAN},"
            + "abletime=#{abletime:BIGINT},disabletime=#{disabletime:BIGINT},memo=#{memo},"
            + "tel=#{tel},lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy},"
            + "version=#{version},origin=#{origin},isdisabled= #{isdisabled:BOOLEAN}"
            + "where id=#{id}")
    void update(UserVO user);
    /**
     * 锁定用户(区别于停用)
     * @param id
     */
    @Update("<script>update rms_user set islocked = 'Y' where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void lockUser(String[] id);
    /**
     * 解锁用户(区别于启用)
     * @param id
     */
    @Update("<script>update rms_user set islocked = 'N' where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void unlockUser(String[] id);

    @Update("update rms_user set pk_org = #{pk_org} where id=#{id}")
    void transOrg(UserVO user);

    @Update("update rms_user set isreset = #{isreset:BOOLEAN},user_password = #{user_password} where id=#{id}")
    void resetUserPasssword(UserVO user);
    
    @Update("update rms_user set isreset = #{isreset:BOOLEAN},user_password = #{user_password},update_pwd_time = #{update_pwd_time:BIGINT} where id=#{id}")
    void updateUserPasssword(UserVO user);

    @Delete("delete from rms_user  where id=#{id}")
    void delete(String id);
        
    void resetPersonalInf(UserVO user);
    
    @Select("select u.* from rms_user u  where u.id=#{id}")
    UserVO load(String id);

    /** 根据用户名查询用户所有信息 */
    @Select("select * from rms_user where account=#{account}")
    UserVO queryUserByAccount(String account);
 
    /** 根据用户名查询用户的验证信息, 此查询对用户私人及敏感信息作相应限制，仅用来验证用户的某些状态 */
    UserVO queryUserAuthInfoByAccount(String account);
    
    @Select("<script>select count(1) from rms_user where account = #{account} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameUser(@Param("account") String account, @Param("id") String id);
    
    List<UserVO> queryUserList(@Param("condition") Condition condition);

    /**
      * 
      * @Title: queryUserByOrgId 
      * @Description: 根据机构ID查询用户集合（查询结果不包含当前登录用户，不包含管理员用户）
      * @param userVO	查询条件
      * @param loginUserId	当前登录用户ID
      * @param userType	用户类型（0超级管理员1普通用户）
      * @param containLower	是否包含下级机构用户
      * @return List<UserVO>    返回用户结婚
      */
    List<UserVO> queryUserByOrgId(@Param("userVO") UserVO userVO, @Param("orgIdList") List orgIdList,@Param("loginUserId") String loginUserId );
    
    /**
     * 
     * @Title: queryUserByOrgId 
     * @Description: 根据机构ID查询用户集合（查询结果不包含当前登录用户，不包含管理员用户）
     * @param userVO	查询条件
     * @param loginUserId	当前登录用户ID
     * @param userType	用户类型（0超级管理员1普通用户）
     * @param containLower	是否包含下级机构用户
     * @return List<UserVO>    返回用户结婚
     */
   List<UserVO> queryUserByOrgIdCondition(@Param("userVO") UserVO userVO, @Param("orgIdList") List orgIdList,@Param("loginUserId") String loginUserId,@Param("condition") Condition condition );

    //成果共享模块使用
    void queryUserByUserIds(MapListResultHandler handler, @Param("array") String[] array);

   //辖区管理模块使用
    List<UserVO> querUsersByOrgIds(List list);

    List<UserVO> querUserByUserIds(List list);

    List<UserVO> findAllUsersList();
    
    List<UserVO> queryNoAuthUserList(@Param("condition") Condition condition,@Param("username") String username);
    
    List<UserVO> queryNoAuthUserListByCondition(@Param("condition") Condition condition);
    
    //@Select("<script>select u.*,o.orgname from rms_user u left join rms_orgs o  on u.pk_org = o.id <if test=\"addCond != null and addCond!=''\">where (${addCond}) </if></script> ")
    List<UserVO> queryUserListByAddCond(@Param("addCond") String addCond);

    //两个queryUserPagePreByIds方法  一个是分页  一个是返回List(工作流模块使用，暂时没有修改)
    Page<UserVO> queryUserPreByIds(@Param("userIds") String[] userIds, @Param("userGroupIds") String[] userGroupIds, @Param("roleIds") String[] roleIds, @Param("orgIds") String[] orgIds,Pageable pageable);

    List<UserVO> queryUserPreByIds(@Param("userIds") String[] userIds, @Param("userGroupIds") String[] userGroupIds, @Param("roleIds") String[] roleIds, @Param("orgIds") String[] orgIds);
    /**
     * 停用
     * @param id
     */
    void disable(String[] id);
    /**
     * 启用
     * @param id
     */
    void enable(String[] id);
    /*
     *  @Select("<script>select u.*,o.orgname from rms_user u left join rms_orgs o  on u.pk_org = o.id <if test=\"addCond != null and addCond!=''\">where (${addCond}) </if>"
     *          + "<trim prefix=\"and\" prefixOverrides=\"and |or \"> <if test=\"condition!= null\">${condition._CONDITION_}</if> </trim> order by o.orgname</script> ")
     *  Page<UserVO> queryUserPageByAddCond(@Param("condition") Condition condition, @Param("addCond") String addCond, Pageable page);
     *  
     *  List<UserVO> queryUserByCondition(@Param("condition") Condition condition);
     *  
     *  Page<UserVO> queryUserByIds(@Param("userIds") String[] userIds, Pageable page);
     *  
     *  
     */
    
    /**
     * 根据账号集合查询用户集合
     * @param accountList
     * @return
     */
	List<UserVO> findUserByAccountList(@Param("accountList")List<String> accountList);
    
}

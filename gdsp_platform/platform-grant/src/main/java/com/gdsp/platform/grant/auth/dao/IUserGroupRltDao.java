package com.gdsp.platform.grant.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.auth.model.UserGroupRltVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * @version 2015/6/25.
 */
@MBDao
public interface IUserGroupRltDao {

    @Insert("insert into rms_usergroup_rlt (id,pk_user,pk_usergroup,createby,createtime ) "
            + "values (#{id},#{pk_user},#{pk_usergroup},#{createBy},#{createTime:BIGINT})")
    void insert(UserGroupRltVO vo);

    @Delete("<script>delete from rms_usergroup_rlt  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    @Delete("<script>delete from rms_usergroup_rlt  where pk_user in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean deleteRltForUser(String[] userIDs);

    @Delete("<script>delete from rms_usergroup_rlt  where pk_usergroup in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean deleteRltForGroup(String[] groupIDs);

    Page<UserGroupRltVO> queryUserGroupRltByCond(@Param("groupId") String groupId, Pageable page);
/*权限拆分------------------------------------2016.12.29
 * 未被调用
    @Select("select u.* from rms_usergroup_rlt ug inner join rms_user u on ug.pk_user = u.id where ug.pk_usergroup=#{groupID} ")
    Page<UserVO> queryUserByGroupId(@Param("groupID") String groupID, Pageable page);
    
    @Select("select u.* from rms_usergroup_rlt ug inner join rms_user u on ug.pk_user = u.id where ug.pk_usergroup=#{groupID} ")
    List<UserVO> queryUserListByGroupId(@Param("groupID") String groupID, Sorter sort);
    
    @Select("select u.* from rms_usergroup_rlt ug inner join rms_user u on ug.pk_user = u.id where ug.pk_user=#{userID} ")
    List<UserVO> queryGroupByUser(@Param("userID") String userID);
*/
    /**----lyf 2016.12.28修改 原因：权限拆分，rms_orgs不能联表查询----**/
    @Select("<script>select u.* from rms_user u <if test=\"addCond != null and addCond!=''\">where (${addCond}) </if>"
            + "<trim prefix=\"and\" prefixOverrides=\"and |or \"> <if test=\"condition!= null\">${condition._CONDITION_}</if> </trim></script> ")
    Page<UserVO> queryUserForUserGroupByAddCond(@Param("condition") Condition condition, @Param("addCond") String addCond, Pageable page);
    
    Page<UserGroupRltVO> queryUserGroupRltByCondition(@Param("condition") Condition condition,Pageable page);
    
    List<UserGroupRltVO> queryUerIdsByGroupId(@Param("groupID") String groupID);
    
    List<UserGroupRltVO> queryUerIdsByPkUser(@Param("userID") String userID);

}

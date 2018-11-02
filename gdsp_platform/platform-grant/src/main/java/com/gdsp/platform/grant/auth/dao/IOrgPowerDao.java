package com.gdsp.platform.grant.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * @version 2015/6/25.
 */
@MBDao
public interface IOrgPowerDao {

    @Insert("insert into rms_power_org (id,pk_role,resource_id,createby,createtime ) "
            + "values (#{id},#{pk_role},#{resource_id},#{createBy},#{createTime:BIGINT})")
    void insert(OrgPowerVO vo);
    
    void insertBatch(List<OrgPowerVO> orgPowerVOList);

    @Delete("<script>delete from rms_power_org  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    @Delete("<script>delete from rms_power_org  where pk_role in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    boolean deleteRoleOrgPower(String[] roleIDs);
    
    /**
     * 根据角色id和机构id数组删除关联关系
     * @param roleId 角色id
     * @param orgIds 机构id数组
     * @return
     */
    boolean deleteByRoleIdAndOrgIds(@Param("roleId")String roleId,@Param("orgIds")String[] orgIds);

    @Select("select op.* from rms_power_org op  where op.pk_role=#{roleID} ")
    List<OrgPowerVO> queryOrgPowerListByRole(String roleID);

    @Select("select distinct op.resource_id from rms_power_org op where op.pk_role =#{userID} or op.pk_role in (select pk_role from rms_user_role where pk_user = #{userID})")
    List<String> queryOrgListByUser(String userID);

    @Select("<script>select * from rms_power_org <trim prefix=\"where\" prefixOverrides=\"and |or \">"
            + "<if test=\"condition!= null\">${condition._CONDITION_}</if>"
            + "</trim>		</script>")
    List<OrgPowerVO> queryOrgPowerByCondition(@Param("condition") Condition condition);

    /** TODO 无调用  待删除 */
//    Page<OrgPowerVO> queryOrgRoleByRoleId(@Param("roleId") String roleID, @Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);
    
    /**
     * 根据主键查找机构和角色关系
     * @param id
     * @return
     */
    OrgPowerVO load(@Param("id") String id);
}

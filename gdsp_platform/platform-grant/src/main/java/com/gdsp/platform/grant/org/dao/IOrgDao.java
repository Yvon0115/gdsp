package com.gdsp.platform.grant.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.org.model.OrgVO;

/**
 * @version 2015/7/1
 */
@MBDao
public interface IOrgDao {

    @Insert("insert into rms_orgs (id,orgcode,orgname,shortname,pk_fatherorg,innercode,memo,createtime,createby,lastmodifytime,lastmodifyby,version) "
            + "values (#{id},#{orgcode},#{orgname},#{shortname},#{pk_fatherorg},#{innercode},#{memo},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insert(OrgVO org);

    @Update("update rms_orgs set orgcode=#{orgcode},orgname=#{orgname},shortname=#{shortname},memo=#{memo},"
            + "lastModifyTime=#{lastModifyTime:BIGINT},lastModifyBy=#{lastModifyBy}, version=#{version} where id=#{id}")
    void update(OrgVO org);

    @Delete("<script>delete from rms_orgs  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    /**lyf 2016.12.27修改 原因：权限拆分，不能联表查询**/
    @Select("select a.id,a.orgcode,a.orgname,a.shortname,a.pk_fatherorg,a.innercode,a.memo,a.createtime,"
            + "a.createby,a.lastmodifytime,a.lastmodifyby,a.version, b.orgname as pk_fatherName"
            + " from rms_orgs a left join rms_orgs b on a.pk_fatherorg = b.id "
            + " where a.id= #{id}")
    OrgVO load(String id);

    @Select("select id,orgcode,orgname,shortname,pk_fatherorg,innercode,memo,createtime,"
            + "createby,lastmodifytime,lastmodifyby,version,'N'leaf from rms_orgs order by innercode")
    List<OrgVO> queryAllOrgList();

    List<OrgVO> queryOrgListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    @Select("<script>select count(1) from rms_orgs where orgcode = #{orgcode} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameOrg(OrgVO org);

    @Select("<script>select count(1) from rms_orgs where orgname =#{orgname} and pk_fatherorg = #{pk_fatherorg} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameNameOrg(OrgVO org);

    public List<OrgVO> queryOrgListByIDs(List list);

    /**
     * 根据机构innercode查询本级及下级机构列表
     * @param innercode
     * @return
     */
    List<OrgVO> querySelfAndChildOrgListByInnercode(@Param("innercode") String innercode);
    
    /**
     * 根据机构innercode查询下级机构列表
     * @param innercode
     * @return
     */
    List<OrgVO> queryChildOrgListByOrgInnercode(@Param("innercode") String innercode);
    
    List<OrgVO> findAllOrgList();
}

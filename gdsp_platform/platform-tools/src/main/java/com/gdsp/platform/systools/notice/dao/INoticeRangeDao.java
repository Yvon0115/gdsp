package com.gdsp.platform.systools.notice.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;
import com.gdsp.platform.systools.notice.model.NoticeRangeVO;

@MBDao
public interface INoticeRangeDao {
/*权限拆分----------------------------------------2016.12.28
 * 未被调用
    Page<UserVO> queryUsersByAddCond(@Param("addCond") String addCond, @Param("page") Pageable page);
*/
    Page<OrgVO> queryOrgByConditions(@Param("addCond") String addCond, Pageable page, @Param("sort") Sorter sort);

    Page<NoticeRangeVO> queryNoticeRangeByCondition(@Param("addCond") String addCond,Pageable page, @Param("sort") Sorter sort);

    Page<UserGroupVO> queryUserGroupByConditions(@Param("addCond") String addCond, Pageable page, @Param("sort") Sorter sort);

    @Insert("insert into rms_notice_range (id,range_id,notice_id,type,createby,createtime ) "
            + "values (#{id},#{range_id},#{notice_id},#{type},#{createBy},#{createTime:BIGINT})")
    void insertNoticeRange(NoticeRangeVO vo);

    @Delete("<script>delete from rms_notice_range  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deleteNoticeRange(String[] ids);
}

package com.gdsp.platform.widgets.help.dao;

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
import com.gdsp.platform.widgets.help.model.HelpVO;

@MBDao
public interface IHelpDao {

    @Select("<script> select * from rms_help <trim prefix=\"where\" prefixOverrides=\"and |or\"><if test=\"condition!=null and condition._CONDITION_ != null\">${condition._CONDITION_}</if></trim><if test=\"sort != null\"> order by	<foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if></script>")
    public Page<HelpVO> queryHelpVosPageByCondition(@Param("condition") Condition condition,
            Pageable pageable, @Param("sort") Sorter sort);

    @Select("select * from rms_help where id=#{id}")
    public HelpVO load(String id);

    @Insert("insert into rms_help ("
            + "id,title,attach_name,"
            + "attach_path,memo,createtime,"
            + "createby,lastmodifytime,lastmodifyby,version) "
            + "values ("
            + "#{id},#{title},#{attach_name},"
            + "#{attach_path},#{memo},#{createTime:BIGINT},"
            + "#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    public void insert(HelpVO helpVO);

    @Update("update rms_help set "
            + "title=#{title},attach_name=#{attach_name},attach_path=#{attach_path},"
            + "memo=#{memo},lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy},"
            + "version=#{version}+1"
            + "where id=#{id}")
    public void update(HelpVO helpVO);

    @Delete("<script>delete from rms_help  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    public void delete(String[] id);
}

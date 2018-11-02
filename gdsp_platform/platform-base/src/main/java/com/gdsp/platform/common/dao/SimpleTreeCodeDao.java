package com.gdsp.platform.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gdsp.dev.persist.ext.MBDao;

/**
 * 树编码使用的dao
 * @author yaboocn
 * @version 1.0 2011-5-17
 * @since 1.6
 */
@MBDao
public interface SimpleTreeCodeDao {

    /**
     * 取得指定表指定树层次的最大编码
     * @param tableName 表名
     * @param parentCode 父节点编码
     * @return 最大编码
     */
    @Select("<script>select max(innercode) as code from ${tableName} where innercode like '${parentCode}____'</script>")
    public String getMaxCode(@Param("tableName") String tableName, @Param("parentCode") String parentCode);

    /**
     * 更新指定表的指定上级的实体的树编码
     * @param tableName 表名
     * @param parentCode 新的上级编码
     * @param oldParentCode 旧的上级编码
     * @return 更新记录的数量
     */
    @Update("<script>update ${tableName} set innercode = (concat('${parentCode}',substr(innercode,${oldParentCode.length()},length(innercode)))) where innercode like '${oldParentCode}%'</script>")
    public int updateSubTreeCode(@Param("tableName") String tableName, @Param("parentCode") String parentCode, @Param("oldParentCode") String oldParentCode);
}

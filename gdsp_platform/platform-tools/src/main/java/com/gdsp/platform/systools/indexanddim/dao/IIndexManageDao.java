package com.gdsp.platform.systools.indexanddim.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;

/**
 * 指标管理数据访问层接口
 * @author Administrator
 *
 */
@MBDao
public interface IIndexManageDao {

    /**
     * 根据条件查询指标信息
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    Page<IndexInfoVO> queryIndexInfoByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    /**
     * 修改指标信息
     * @param indexInfoVO
     */
    void updateIndexInfo(IndexInfoVO indexInfoVO);

    /**
     * 新增指标信息
     * @param indexInfoVO
     */
    void insertIndexInfo(IndexInfoVO indexInfoVO);

    /**
     * 根据指标ID查询指标信息
     * @param id
     * @return
     */
    IndexInfoVO findIdxInfoById(String id);

    /**
     * 删除指标信息
     * @param ids
     */
    void deleteIdxInfo(String[] ids);

    /**
     * 唯一性检查
     * @param indexInfoVO
     * @return
     */
    int existSameIdxInfo(IndexInfoVO indexInfoVO);

    /**
     * 查询指标表，归属部门数据
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    Page<IndexInfoVO> queryIdxDepartment(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    /**
     * 根据部门ID获取部门名称
     * @param comedepart
     * @return
     */
    /**-----lyf 2016.12.28修改 原因：有关rms_orgs表的权限拆分-------**/
//    OrgVO queryOrgNameByID(String comedepart);
    /**--此次修改结束--**/

    /**
     * 查询指标是否存在
     * @param id
     * @return
     */
    int findExportIdxInfoById(String id);

    /**
    * 
    * 通过指标编码、指标名称查询 <br/>
    * 
    * @param indexCode
    * @param indexName
    * @return
    */
    String queryIdexId(@Param("indexCode") String indexCode, @Param("indexName") String indexName);

    /**
     * 批量新增指标
     * @param indexInfoVO
     */
    void insertIndex(List<IndexInfoVO> list);
    /**
     * 通过指标ID获取指标信息
     * @param ids
     * @return
     */
    List<IndexInfoVO> queryIndexByIds(@Param("ids")List<String> ids);
}

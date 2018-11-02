package com.gdsp.platform.systools.indexanddim.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.IndTreeVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;

/**
 * 指标分组管理底层接口类 <br/>
 * 
 * @author hy
 */
@MBDao
public interface IIndGroupDao {

    /**
     * 
     * 查询所有指标组信息 <br/>
     * 
     * @param sort
     * @return
     */
    public List<IndTreeVO> queryAllIndGroup();

    /**
     * 
     * 查询编码相同的指标组数量<br/>
     * 
     * @param indTreeVO
     * @return
     */
    public int existSameCode(IndTreeVO indTreeVO);

    /**
     * 
     * 根据innercode查询指标组 <br/>
     * 
     * @param innercode
     * @return
     */
    public IndTreeVO queryIndGroupByInn(@Param("parentId") String parentId);

    /**
     * 
     * 根据id查询指标组 <br/>
     * 
     * @param id
     * @return
     */
    public IndTreeVO queryIndGroupById(@Param("id") String id);

    /**
     * 
     * 添加指标组<br/>
     * 
     * @param indTreeVO
     */
    public void insert(IndTreeVO indTreeVO);

    /**
     * 
     * 修改指标组信息 <br/>
     * 
     * @param indTreeVO
     */
    public void update(IndTreeVO indTreeVO);

    /**
     * 
     * 根据条件查询其所有子指标组 <br/>
     * 
     * @param condition
     * @param sort
     * @return
     */
    public List<IndTreeVO> queryIndGroupListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    /**
     * 
     * 根据id删除指标组 <br/>
     * 
     * @param id
     */
    public void deleteIndGroup(@Param("id") String id);

    /**
     * 
     * 查询指标组下所关联的指标 <br/>
     * 
     * @param condition
     * @param page
     * @return
     */
    public Page<IndGroupRltVO> queryChildInd(@Param("condition") Condition condition, Pageable page);

    /**
     * 
     * 查询不在该指标组下的指标 <br/>
     * 
     * @param addCond
     * @param page
     * @return
     */
    public Page<IndexInfoVO> queryRemainInd(Pageable page, @Param("sort") Sorter sort, @Param("condition") Condition condition);

    /**
     * 
     * 添加指标组与指标的关联关系 <br/>
     * 
     * @param indGroupRltVO
     */
    public void insertIndGroupRlt(List<IndGroupRltVO> list);

    /**
     * 
     * 根据id查询该指标组的关联关系 <br/>
     * 
     * @param id
     * @return
     */
    public List<IndGroupRltVO> queryRltByGroupId(@Param("id") String id);

    /**
     * 
     * 删除关联关系 <br/>
     * 
     * @param ids
     */
    public void deleteRelation(String[] ids);
    /**
     * 查询所有指标组信息
     * @param handler
     */
    public void queryKpiTreeMap(ResultHandler handler);
}

package com.gdsp.platform.systools.indexanddim.service;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.IndTreeVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;

/**
 * 指标组服务
 * @author hy
 */
public interface IIndGroupService {

    /**
     * 查询所有指标组 <br/>
     * @param sort
     * @return
     */
    public List<IndTreeVO> queryAllIndGroup();

    /**
     * 查询指标组唯一性 <br/>
     * @param indTreeVO
     * @return
     */
    public boolean uniqueCheck(IndTreeVO indTreeVO);

    /**
     * 根据父级ID查询内部码 <br/>
     * @param innercode
     * @return
     */
    public IndTreeVO queryIndGroupByInn(String parentId);

    /**
     * 根据id查询指标组 <br/>
     * @param id
     * @return
     */
    public IndTreeVO queryIndGroupById(String id);

    /**
     * 添加指标组 <br/>
     * @param indTreeVO
     */
    public void insert(IndTreeVO indTreeVO);

    /**
     * 修改指标组信息 <br/>
     * @param indTreeVO
     */
    public void update(IndTreeVO indTreeVO);

    /**
     * 根据指标组id查询子指标组 <br/>
     * @param indGroupId
     * @param condition
     * @param sort
     * @param containItself
     * @return
     */
    public List<IndTreeVO> queryChildIndGroupById(String indGroupId, Condition condition, Sorter sort, boolean containItself);

    /**
     * 删除指标组 <br/>
     * @param id
     * @return
     */
    public boolean deleteIndGroup(String id);

    /**
     * 更新版本信息 <br/>
     * @param indTreeVO
     */
    public void changeVersion(IndTreeVO indTreeVO);

    /**
     * 查询指标组下所关联的指标 <br/>
     * @param innercode
     * @param cond
     * @param page
     * @return
     */
    public Page<IndGroupRltVO> queryChildInd(String innercode, Condition cond, Pageable page);

    /**
     * 查询不在该指标组下的指标 <br/>
     * @param id
     * @param page
     * @return
     */
    public Page<IndexInfoVO> queryRemainInd(String id, Pageable page, Sorter sort, Condition cond);

    /**
     * 添加指标组与指标的关联关系 <br/>
     * @param groupId
     * @param id
     */
    public void addRelation(String groupId, String[] id);

    /**
     * 根据指标组id查询关联关系 <br/>
     * @param id
     * @return
     */
    public List<IndGroupRltVO> queryRltByGroupId(String id);

    /**
     * 删除指标组与指标的关联关系 <br/>
     * @param ids
     * @return
     */
    public boolean deleteRelation(String[] ids);
    /**
     * 指标组树型结构
     * @param handler
     */
    public void queryKpiTreeMap(ResultHandler handler);
    /**
     * 根据指标指标分组关系过去指标信息
     * @param condition
     * @param page
     * @return
     */
    public Page<IndGroupRltVO> queryKpiPageByCondition(Condition condition, Pageable page);
    
}

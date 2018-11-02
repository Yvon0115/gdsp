package com.gdsp.platform.systools.datalicense.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;

/**
 * 启用的数据源关联数据字典dao接口
 * @author yucl
 *
 */
@MBDao
public interface IDataDicPowerDao extends ICrudDao<DataDicPowerVO>{

    /**
     * 通过条件查询数据字典
     * @param dataSourceId
     * @param page
     * @return 分页方式显示数据字典
     */
    public Page<DataDicPowerVO> findDataDicByDataSourceId(@Param("dataSourceId") String dataSourceId, @Param("condition") Condition condition, Pageable page);

    /**
     * 删除数据源和数据字典的关系
     * @param ids
     * @return 
     */
    public int delete(@Param("ids") String[] ids);

    /**
     * 查询所有数据字典数据id集合
     * @return
     */
    public List<String> findAllDataDicIds();

    /**
     * 通过选中数据源id查询关联的数据字典集合
     * @param dataSourceID
     * @return 以list集合形式返回数据字典VO
     */
    public List<DataDicPowerVO> findDataDicIds(@Param("pk_dataSource") String pk_dataSource);

    /**
     * 保存数据源数据字典关联关系
     * @param dataSourceId
     * @param ids
     * @return 
     */
    public int insert(List<DataDicPowerVO> list);

    /**
     * 查询已被关联的维度id集合
     * @return
     */
    public List<String> findPk_dataDicIds(String[] ids);

    /**
     * 查询维度数据是否被授权
     * @return 维度id集合
     */
    public List<String> findPk_dicList(List<String> ids);
    
    /**
     * 根据主键查找角色和菜单关系
     * @param id
     * @return
     */
    DataDicPowerVO load(@Param("id") String id);
    
    List<DataDicPowerVO> queryPowerDataDic(String[] id);
}

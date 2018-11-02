package com.gdsp.platform.systools.datalicense.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;

/**
 * 权限控制维度Service
 * @author yucl
 * 
 */
@Deprecated
public interface IDataDicPowerService {

    /**
     * 通过数据源id查询数据字典信息
     * @param dataSourceId
     * @param page
     * @return 分页方式显示数据字典
     */
    public Page<DataDicPowerVO> queryDataDicByDataSourceId(String dataSourceId, Condition condition, Pageable page);

    /**
     * 删除数据源和数据字典的关系
     * 若有授权则不允许删除
     * @param ids
     */
    public boolean deleteDataSourceDicRel(String[] ids);

    /**
     * 保存数据源和数据字典关系
     * @param dataSourceId
     * @param ids
     */
    public void saveDataSourceDataDicRel(String dataSourceId, String[] ids);

    /**
     * 查询所有数据字典id集合
     * @return 以list形式返回数据字典id集合
     */
    public List<String> queryAllDataDic();

    /**
     * 通过选中数据源id查询关联的数据字典id
     * @param dataSourceID
     * @return 以list集合形式返回数据字典VO
     */
    public List<DataDicPowerVO> queryDataDicIds(String dataSourceId);
    /**
     * 通过维度id 查询信息
     * @param ids
     * @return
     * 2017年1月13日
     * win
     */
    public List<DataDicPowerVO> queryPowerDataDic(String [] ids);
}

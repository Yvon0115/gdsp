package com.gdsp.ptbase.appcfg.service;

import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.model.CommonDirVO;
import com.gdsp.ptbase.appcfg.model.CommonDirsVO;

public interface ICommonDirService {

    CommonDirVO insert(CommonDirVO dir);

    CommonDirVO update(CommonDirVO dir);

    void updateName(CommonDirVO dir);

    void delete(String... id);

    /**
     * 删除资源目录，（为空时删除）
     * @param id
     */
    String deleteById(String id);

    CommonDirVO load(String id);

    /**
     * 根据分类下指定父节点下的第一级子节点
     * @param category 分类
     * @param parentId 上级节点id
     * @return 指定目录的下级目录
     */
    List<CommonDirVO> findFirstLevelDirsByCategory(String category, String parentId);

    /**
     * 根据分类下查询所有下级目录的树状maplist
     * @param category 分类
     * @return 以parentId为键值的所有下级目录的树状maplist
     */
    Map<String, List<Map>> findDirTreeByCategory(String category);

    /**
     * 查询所有分类下的所有目录
     * @return 以parentId为键值的所有下级目录的树状maplist
     */
    Map<String, List<Map>> findAllDirTree();

    List<CommonDirVO> findListByCondition(Condition cond, Sorter sort);

    Map<String, CommonDirVO> findMapByCondition(Condition cond, Sorter sort);

    int findCountByCondition(Condition cond);

    int findMaxSortNum();

    /**
     * 查询资源目录
     * @param con
     * @param sort
     * @return Map 返回树用的Map
     */
    Map queryCommonDirByCondReturnMap(Condition con, Sorter sort);

    public boolean synchroCheckDirName(CommonDirVO commonDirVO);
    
    /**
     * 查询出所有的目录
     * @return
     */
    public Map<String, List<CommonDirsVO>> queryDirs();
}

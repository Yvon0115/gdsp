package com.gdsp.platform.config.global.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.impl.QueryService;
import com.gdsp.platform.config.global.dao.IDefDocListDao;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;

/**
 * 
* @ClassName: DefDocListServiceImpl
* (接口实现类)
* @author songxiang
* @date 2015年10月28日 下午2:02:22
*
 */
@Service
@Transactional(readOnly = true)
public class DefDocListServiceImpl extends QueryService<DefDocListVO> implements IDefDocListService {

    @Resource
    private IDefDocListDao defDocListDao;

    @Override
    protected QueryDao<DefDocListVO> getDao() {
        return defDocListDao;
    }

    // 根据主键查询
    @Override
    public DefDocListVO findOne(String id) {
        return defDocListDao.findOne(id);
    }

    //保存更新
    @Transactional
    @Override
    @CacheEvict(value = "defDocListCache", allEntries = true)
    public void saveDefDocList(DefDocListVO defDocListVO) {
        if (StringUtils.isNotEmpty(defDocListVO.getId())) {
            defDocListDao.update(defDocListVO);
        } else {
            defDocListVO.setSortnum(defDocListDao.findSortnum() + 1);
            defDocListDao.insert(defDocListVO);
        }
    }

    /*
    //删除
    @Transactional
    @Override
    @CacheEvict(value = "defDocListCache", allEntries = true)
    public void delete(String[] ids) {
        defDocListDao.delete(ids);
    }*/

    //根据单条ID查询
    @Override
    public DefDocListVO load(String id) {
        
        return defDocListDao.load(id);
    }

    //查询所有
    @Override
    public Page<DefDocListVO> queryDefDocListVOByCondition(Condition condition,
            Pageable page, Sorter sort) {
        
        return defDocListDao.queryDefDocListVOByCondition(condition, page, sort);
    }

    

    @Override
    @Cacheable(value = "defDocListCache", key = "#typeid")
    public List<DefDocVO> getDefDocsByTypeID(String typeid) {
        //实现取的是code
        List<DefDocListVO> list = defDocListDao.findDocListWithDocsByTypeCode(typeid);
        if (list != null && list.size() > 0) {
            return list.get(0).getDefdocs();
        }
        return null;
    }

    /* (非 Javadoc)
    * <p>Title: isPreset</p>
    * <p>Description: </p>
    * @param id
    * @return
    * @see com.gdsp.platform.systools.datadic.service.IDefDocListService#isPreset(java.lang.String[])
    */
    @Override
    public List<String> isPreset(String[] id) {
        return defDocListDao.isPreset(id);
    }

    @Override
    public boolean synchroCheck(DefDocListVO defDocListVO) {
    	 if(defDocListDao.existSameTypeCode(defDocListVO)>0){
             return false;
         }else{
             return true;
         }
    }

    @Override
    public String findIdByTypeCode(String typeCode) {
        return defDocListDao.findIdByTypeCode(typeCode);
    }
    
    @Override
    public List<DefDocListVO> queryByTypeCode(String typeCode) {
        Condition condition = new Condition();
        condition.addExpression("type_code", typeCode);
        return defDocListDao.findListByCondition(condition);
    }
}

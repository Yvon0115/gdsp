package com.gdsp.ptbase.appcfg.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.ptbase.appcfg.dao.ICommonDirDao;
import com.gdsp.ptbase.appcfg.model.CommonDirVO;
import com.gdsp.ptbase.appcfg.model.CommonDirsVO;
import com.gdsp.ptbase.appcfg.service.ICommonDirService;

@Service
@Transactional
public class CommonDirServiceImpl implements ICommonDirService {

    @Resource
    private ICommonDirDao dao;

    @Override
    public CommonDirVO insert(CommonDirVO dir) {
        dao.insert(dir);
        return dir;
    }

    @Override
    public CommonDirVO update(CommonDirVO dir) {
        dao.update(dir);
        return dir;
    }

    @Override
    public void delete(String... ids) {
        dao.delete(ids);
    }

    public String deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            return "N";
        }
        //判断该目录下是否有有目录或者资源
        String isExist = dao.isExistChildNode(id);
        if (StringUtils.isBlank(isExist)) {
            return "N";
        }
        if ("Y".equals(isExist)) {
            dao.deleteById(id);
            return isExist;
        }
        return "N";
    }

    @Override
    public CommonDirVO load(String id) {
        return dao.load(id);
    }

    @Override
    public List<CommonDirVO> findFirstLevelDirsByCategory(String category, String parentId) {
        return dao.findFirstLevelDirsByCategory(category, parentId);
    }

    @Override
    public Map<String, List<Map>> findDirTreeByCategory(String category) {
        MapListResultHandler<Map> handler = new MapListResultHandler<>("parent_id");
        dao.findDirTreeByCategory(category, handler);
        return handler.getResult();
    }

    public Map findAllDirTree() {
        MapListResultHandler<Map> handler = new MapListResultHandler<>("parent_id");
        dao.findAllDirTree(handler);
        return handler.getResult();
    }

    @Override
    public List<CommonDirVO> findListByCondition(Condition cond, Sorter sort) {
        return dao.findListByCondition(cond, sort);
    }

    @Override
    public int findCountByCondition(Condition cond) {
        return dao.findCountByCondition(cond);
    }

    @Override
    public int findMaxSortNum() {
        Integer i = dao.findMaxSortNum();
        return i == null ? 0 : i.intValue();
    }

    @Override
    public Map queryCommonDirByCondReturnMap(Condition con, Sorter sort) {
        MapListResultHandler handler = new MapListResultHandler("parent_id");
        dao.queryCommonDirByCondReturnMap(con, sort, handler);
        return handler.getResult();
    }

    @Override
    public Map<String, CommonDirVO> findMapByCondition(Condition cond, Sorter sort) {
        
        List<CommonDirVO> lisvo = dao.findListByCondition(cond, sort);
        Map<String, CommonDirVO> map = new HashMap<String, CommonDirVO>();
        if (lisvo == null || lisvo.size() == 0) {
            return map;
        }
        Iterator<CommonDirVO> it = lisvo.iterator();
        while (it.hasNext()) {
            CommonDirVO vo = it.next();
            map.put(vo.getId(), vo);
        }
        return map;
    }

    @Override
    public void updateName(CommonDirVO dir) {
        
        dao.updateName(dir);
    }

    @Override
    public boolean synchroCheckDirName(CommonDirVO commonDirVO) {
        return dao.existSameDirName(commonDirVO) == 0;
    }

	@Override
	public Map<String, List<CommonDirsVO>> queryDirs() {
		MapListResultHandler<CommonDirsVO> handler = new MapListResultHandler<CommonDirsVO>("parent_id");
		dao.queryDirs(handler);
		return handler.getResult();
	}
}

package com.gdsp.integration.framework.param.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.param.dao.IAcParamDao;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.model.TemplateVO;
import com.gdsp.integration.framework.param.service.IParamService;

@Service
@Transactional(readOnly = true)
public class ParamServiceImpl implements IParamService {

    @Autowired
    private IAcParamDao dao;

    @Transactional(readOnly = false)
    @Override
    public void addParam(AcParam vo) {
        dao.insert(vo);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateParam(AcParam vo) {
        dao.updateByPrimaryKeyWithBLOBs(vo);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteParam(String id) {
        dao.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteParamByIds(String[] ids) {
        dao.deleteParamByIds(ids);
    }

    @Override
    public AcParam load(String id) {
        return dao.load(id);
    }

    @Override
    public List<AcParam> queryAllParam() {
        return dao.queryAllParam();
    }

    @Override
    public Page<AcParam> queryByCondition(Condition condition,
            Pageable pageable, Sorter sort) {
        return dao.queryByCondition(condition, pageable, sort);
    }

    @Override
    public void loadQueryTemplate(String rootPath, String path, Map<String, List<TemplateVO>> map) {
        File allFiles = new File(path);
        File[] subFiles = allFiles.listFiles();
        if (subFiles != null) {
            for (File file : subFiles) {
                TemplateVO node = new TemplateVO();
                node.setFileName(file.getName());
                node.setFilePath(path + "/" + file.getName());
                node.setParentPath(path);//父路径
                if (rootPath.equals(path)) {
                    node.setParentPath("__null_key__");//父路径
                }
                List<TemplateVO> list = map.get(node.getParentPath());
                if (list == null) {
                    list = new ArrayList<TemplateVO>();
                    map.put(node.getParentPath(), list);
                }
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".ftl") || fileName.endsWith(".jsp")) {
                        list.add(node);
                    }
                } else {
                    String newPath = node.getFilePath();
                    list.add(node);
                    loadQueryTemplate(rootPath, newPath, map);//递归调用
                }
            }
        }
    }

    //	private void getFileRecur(File file,List<TemplateVO> list,String dirPath){
    //		if(!file.exists()){
    //			return;
    //		}
    //		if(file.isDirectory()){
    //			File[] listFiles = file.listFiles();
    //			if(listFiles != null && listFiles.length > 0){
    //				for (File file2 : listFiles) {
    //					getFileRecur(file2,list,dirPath);
    //				}
    //			}
    //		}else{
    //			TemplateVO vo = new TemplateVO();
    //			vo.setFileName(file.getName());
    //			String path = file.getPath();
    //			vo.setFilePath(path.substring(new File(dirPath).getPath().length(),path.length()));
    //			list.add(vo);
    //		}
    //	}
    @Override
    public List<AcParam> queryByIds(List<String> ids) {
        if (ids == null) {
            return null;
        }
        return dao.queryByIds(ids);
    }
}

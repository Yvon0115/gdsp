package com.gdsp.platform.systools.datasource.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.datasource.dao.IDataSourceDao;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.model.DsLibraryVO;
import com.gdsp.platform.systools.datasource.model.DsRegisterVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;

/**
 * 
* @ClassName: DataSourceServiceImpl
* ( 接口的实现类)
* @author songxiang
* @date 2015年10月28日 下午12:51:35
*
 */
@Service
@Transactional(readOnly = true)
public class DataSourceServiceImpl implements IDataSourceService {

    @Autowired
    private IDataSourceDao           dataSourceDao;
    public Map<String, DataSourceVO> map = null;
    //从application.properties中读取秘钥，报表秘钥为定值
    private String reportKey = FileUtils.getFileIO("reportKey",true);

    /**
     * 更新数据
     */
    @Transactional
    @Override
    public void saveDataSource(DataSourceVO vo) {
    	
    	DataSourceVO existVO = dataSourceDao.load(vo.getId());
    	boolean curPswExists = StringUtils.isNotEmpty(vo.getPassword().trim());    // 现密码不为空
//    	boolean orginPswExists = StringUtils.isNotEmpty(existVO.getPassword().trim());    // 原密码不为空
    	boolean curEqualsOrgin = false;
    	if (existVO!=null) {
    		curEqualsOrgin = StringUtils.equals(existVO.getPassword().trim(), vo.getPassword().trim());    // 原密码与现密码相同
		}
    	
    	if (StringUtils.isNotEmpty(vo.getId())) {    // 修改
    		if (curPswExists && !curEqualsOrgin) {    // 加密的前提是现密码不为空，且和原密码不相等
				vo.setPassword(EncryptAndDecodeUtils.getEncryptString(vo.getPassword(),reportKey));     // 改动后对密码重新加密然后更新
			}
            dataSourceDao.update(vo);
        } else {                                     // 新增
        	if(curPswExists){
        		vo.setPassword(EncryptAndDecodeUtils.getEncryptString(vo.getPassword(),reportKey));
        	}
            dataSourceDao.insert(vo);
         }
    }
    
    
    

    /**
     * 删除数据
     */
    @Transactional
    @Override
    public void deleteDataSource(String... ids) {
        
        dataSourceDao.delete(ids);
    }

    /**
     * 查询数据
     */
    @Override
    public Page<DataSourceVO> queryDataSourceByCondition(Condition condition, Pageable page, Sorter sort) {
        return dataSourceDao.queryDataSourceByCondition(condition, page, sort);
    }

    @Override
    public List<DataSourceVO> queryAllDataSourceByType() {
        
        return dataSourceDao.queryAllDataSourceByType();
    }

    @Override
    public Map<String, DataSourceVO> queryAllDataSourceByTypeMap() {
        
        if (map != null) {
            return map;
        }
        List<DataSourceVO> lis = dataSourceDao.queryAllDataSourceByType();
        map = new HashMap<String, DataSourceVO>();
        if (lis == null || lis.isEmpty()) {
            return map;
        }
        Iterator<DataSourceVO> it = lis.iterator();
        while (it.hasNext()) {
            DataSourceVO vo = it.next();
            map.put(vo.getId(), vo);
        }
        return map;
    }

    @Override
    public boolean synchroCheck(DataSourceVO dateSourceVO) {
        if(dataSourceDao.existSameCode(dateSourceVO)>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public DataSourceVO queryDataSourceByCode(String code) {
        return dataSourceDao.queryDataSourceByCode(code);
    }

    @Override
    public boolean checkDatasourceTypeUnique(DataSourceVO dateSourceVO) {
        return dataSourceDao.existDatasourceType(dateSourceVO) == 0;
    }

    @Override
    public List<DataSourceVO> queryEnableDataSource(String[] reportType) {
        return dataSourceDao.queryEnableDataSource(reportType);
    }

    @Override
    public Map<String, List<Map>> findDirTreeByCategory(String []type) {
        MapListResultHandler<Map> handler = new MapListResultHandler<>("parent_id");
        dataSourceDao.findDirTreeByCategory(type, handler);
        return handler.getResult();
    }

    @Override
    public List<DsRegisterVO> queryDatasourceRef(String... Ds_id) {
        List<DsRegisterVO> dsRegisterVOs = dataSourceDao.findAllDsRef();
        List<DsRegisterVO> dsRegVO = new ArrayList<>();
        if(Ds_id!=null && Ds_id.length>0 && dsRegisterVOs!=null && dsRegisterVOs.size()>0){
            for(String id:Ds_id){
                for(DsRegisterVO VO:dsRegisterVOs){
                    if(null != VO.getPk_datasource() && id.equals(VO.getPk_datasource())){
                        dsRegVO.add(VO);
                    }
                }
            }
            return dsRegVO;
        }else{
            return null;
        }
    }

    @Override
    public List<DsLibraryVO> queryDSProListByTypeAndVersion(String ds_type, String ds_version) {
        return dataSourceDao.findDSProListByTypeAndVersion(ds_type,ds_version);
    }



	@Override
	public boolean passwordCheck(DataSourceVO dateSourceVO) {
		boolean flag = false;
		if (StringUtils.isEmpty(dateSourceVO.getId())) {
			if (dateSourceVO.getPassword().length() > 32) {
				flag = false;
			}else {
				flag = true;
			}
		}else {
			DataSourceVO load = dataSourceDao.load(dateSourceVO.getId());
			if (dateSourceVO.getPassword().equals(load.getPassword())) {
				flag = true;
			}else {
				if (dateSourceVO.getPassword().length() > 32) {
					flag = false;
				}else {
					flag = true;
				}
			}
		}
		return flag;
	}
}

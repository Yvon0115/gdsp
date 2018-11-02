package com.gdsp.platform.systools.datalicense.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.log.service.OpLog;
import com.gdsp.platform.systools.datalicense.dao.IDataDicPowerDao;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
import com.gdsp.platform.systools.datalicense.service.IDataDicPowerService;
import com.gdsp.platform.systools.datalicense.service.IDataLicenseService;

/**
 * 权限控制维度数据源关联数据字典实现类
 * @author yucl
 *
 */
@Service
@Transactional(readOnly = true)
public class DataDicPowerServiceImpl implements IDataDicPowerService {

    @Autowired
    private IDataDicPowerDao dataDicPowerDao;
    @Autowired
    private IDataLicenseService dataLicenseService;
    @Override
    public Page<DataDicPowerVO> queryDataDicByDataSourceId(String dataSourceId, Condition condition, Pageable page) {
        Page<DataDicPowerVO> dataDicPowerVO = dataDicPowerDao.findDataDicByDataSourceId(dataSourceId, condition, page);
        return dataDicPowerVO;
    }

    @Override
    @Transactional
    @OpLog
    public boolean deleteDataSourceDicRel(String[] ids) {
        //根据选中id数组查询关联维度id集合
        List<String> pk_dataDicId = dataDicPowerDao.findPk_dataDicIds(ids);
        //根据维度id集合查询被授权维度id集合
        String strings[]=new String[pk_dataDicId.size()];
        for(int i=0,j=pk_dataDicId.size();i<j;i++){
        strings[i]=pk_dataDicId.get(i);
        }
       Object[] array = pk_dataDicId.toArray();
        List<DataLicenseVO> queryRole = dataLicenseService.queryRole(strings);
      //  List<String> pk_dicIds = dataDicPowerDao.findPk_dicList(pk_dataDicId);
        if (queryRole.size() > 0) {
            return false;
        } else {
            dataDicPowerDao.delete(ids);
            return true;
        }
    }

    @Override
    @Transactional
    @OpLog
    public void saveDataSourceDataDicRel(String dataSourceId, String[] ids) {
        List<DataDicPowerVO> list = new ArrayList<DataDicPowerVO>();
        //将一对多关系封装到对象集合保存
        for (String id : ids) {
            DataDicPowerVO vo = new DataDicPowerVO();
            vo.setPk_dataSource(dataSourceId);
            vo.setPk_dataDicId(id);
            list.add(vo);
        }
        dataDicPowerDao.insert(list);
    }

    @Override
    public List<String> queryAllDataDic() {
        return dataDicPowerDao.findAllDataDicIds();
    }

    @Override
    public List<DataDicPowerVO> queryDataDicIds(String dataSourceId) {
        return dataDicPowerDao.findDataDicIds(dataSourceId);
    }

	@Override
	public List<DataDicPowerVO> queryPowerDataDic(String[] ids) {
		
		return dataDicPowerDao.queryPowerDataDic(ids);
	}
}

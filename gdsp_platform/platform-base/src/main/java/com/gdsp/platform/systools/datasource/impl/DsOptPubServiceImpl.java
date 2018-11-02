/**  
* @Title: DsOptPubServiceImpl.java
* @Package com.gdsp.platform.systools.datasource.impl
* (用一句话描述该文件做什么)
* @author yuchenglong
* @date 2017年7月7日 下午4:32:38
* @version V1.0  
*/ 
package com.gdsp.platform.systools.datasource.impl;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.systools.datasource.dao.IDataSourceDao;
import com.gdsp.platform.systools.datasource.model.DsRegisterVO;
import com.gdsp.platform.systools.datasource.model.PtRegMsgVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceOptPubService;


/**
* @ClassName: DsOptPubServiceImpl
* (数据源相关公共操作接口实现类)
* @author yuchenglong
* @date 2017年7月7日 下午4:32:38
*
*/
@Service
public class DsOptPubServiceImpl implements IDataSourceOptPubService {
    
    @Autowired
    private IDataSourceDao dataSourceDao;
    
    @Override
    public PtRegMsgVO register2Datasource(DsRegisterVO dsRegisterVO) {
        if(dsRegisterVO==null){
            return null;
        }
        String pk_datasource = dsRegisterVO.getPk_datasource();
        String res_id = dsRegisterVO.getRes_id();
        if(!(StringUtils.isNotEmpty(pk_datasource) && StringUtils.isNotEmpty(res_id))){
            return new PtRegMsgVO(false,"失败","417","注册信息不能为空！");
        }
        try{
            if(0==dataSourceDao.jdugeRegister(pk_datasource,res_id)){
                dataSourceDao.insertPtRegMsgVO(dsRegisterVO);
                return new PtRegMsgVO(true,"成功","200","注册成功！");
            }else{
                dataSourceDao.updatePtRegMsgVO(dsRegisterVO);
                return new PtRegMsgVO(true,"成功","200","您已经注册过该服务！");
            }
        }catch(Exception e){
            return new PtRegMsgVO(false,"失败","500","服务器异常！请联系管理员！");
        }
    }
    
    @Override
    public Boolean removeDatasourceRef(String ds_id, String res_id) {
        dataSourceDao.deletePtRegMsgVO(ds_id,res_id);
        return true;
    }
    
}

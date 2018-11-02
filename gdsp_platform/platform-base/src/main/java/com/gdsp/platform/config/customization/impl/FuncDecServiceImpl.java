package com.gdsp.platform.config.customization.impl;


import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.platform.config.customization.dao.IFuncDecDao;
import com.gdsp.platform.config.customization.model.FuncDecDataIOVO;
import com.gdsp.platform.config.customization.model.FuncDecVO;
import com.gdsp.platform.config.customization.service.IFuncDecService;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;


/**
 * 
* @ClassName: FuncDecServiceImpl
* (接口的实现类)
* @author songxiang
* @date 2015年10月28日 下午12:53:21
*
 */
@Transactional(readOnly=false)
@Service
public class FuncDecServiceImpl implements IFuncDecService {

	private static final Logger logger = LoggerFactory.getLogger(FuncDecServiceImpl.class);
	@Autowired
	private IFuncDecDao dao;
	@Autowired
	IMenuRegisterService munuRegisterService ;
	@Override
	public void save(FuncDecVO funcDecVO) {
		
	    String id = funcDecVO.getId();
	    if(StringUtils.isNoneEmpty(id)){
	        dao.update(funcDecVO);
	    }else{
	        dao.insert(funcDecVO);
	    }
		
	}

	@Override
	public List<FuncDecVO> queryFuncDecListByCondition(String menuid
			) {
		
		return dao.queryFuncDecListByCondition(menuid);
	}


	@Override
	public void delete(String id) {
		
		dao.delete(id);
	}

	@Override
	public FuncDecVO loadFuncDecVOById(String id) {
		
		return dao.load(id);
	}

	@Override
	public List<FuncDecVO> findListByMenuId(String menuid, Condition condition) {
		
		return dao.queryFuncDecListByCondition(menuid);
	}

	@Override
	public void batchUpdate(List<FuncDecVO> funcDecVO) {
		if (funcDecVO != null && funcDecVO.size() > 0)
		{
			Iterator<FuncDecVO> it = funcDecVO.iterator();
			while (it.hasNext())
			{
				FuncDecVO vo = it.next();
				dao.update(vo);
			}
		}
		
	}

	@Override
	public void importFuncDec(List<FuncDecDataIOVO> vos) {
		if(vos == null ){
			throw new BusinessException("导入数据为空");
		}
		for (FuncDecDataIOVO funcDecDataIOVO : vos) {
			if(StringUtils.isNotBlank(funcDecDataIOVO.getId())){
				dao.deleteByMenuId(funcDecDataIOVO.getId());
			}
		}
		//解析list并保存
		for (int i = 0; i < vos.size(); i++) {
			FuncDecDataIOVO funcDecDataIOVO = vos.get(i);
			if(funcDecDataIOVO != null){
				MenuRegisterVO menuRegVo = new MenuRegisterVO();
				menuRegVo.setFunname(funcDecDataIOVO.getFunname());
				menuRegVo.setMemo(funcDecDataIOVO.getMemo());
				menuRegVo.setUrl(funcDecDataIOVO.getUrl());
				menuRegVo.setFuntype(funcDecDataIOVO.getFuntype());
				menuRegVo.setFuncode(funcDecDataIOVO.getFuncode());
				menuRegVo.setParentid(funcDecDataIOVO.getParentid());
				menuRegVo.setCreateTime(new DDateTime());
				menuRegVo.setCreateBy(AppContext.getContext().getContextUserId());
				munuRegisterService.insertMenuRegister(menuRegVo);;
			}
		}
	}
	/**
	 * @return 如果为true,超过数据库字段长度
	 * @return 获取数据库编码集
	 */
	@Override
	public boolean findDataSourceNlslang(String content) {
		
		try {
			//获取datasource.properties下dataSourceType的值
			String datasourceType = FileUtils.getFileIO("dataSourceType",false);
			//如果为oracle
			if(datasourceType.toLowerCase().indexOf("oracle") > 0){
			//查询数据库编码集
				String Nlslang =dao.findDataSourceNlslang();
				//不同编码集下的处理
				if (Nlslang.toLowerCase().indexOf("gbk") > 0) {
					//如果查询获得数据库编码集为gbk,将content转为gbk编码(按1个中文2个字节计算)并判断长度
					if (content.getBytes("gbk").length > 500) {
						return true;
					}
				} else {
					//如果查询获得数据库编码集不为gbk,将content转为utf-8编码(按1个中文3个字节计算)并判断长度
					if (content.getBytes("utf-8").length > 500) {
						return true;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			
			logger.error(e.getMessage(),e);
			return true;
		}
		return false;
	}

	

}

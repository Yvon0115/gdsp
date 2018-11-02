package com.gdsp.platform.config.customization.impl;

import com.gdsp.platform.config.customization.dao.ISystemConfExtDao;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.customization.model.GrantAgingVO;
import com.gdsp.platform.config.customization.model.MailServiceConfVO;
import com.gdsp.platform.config.customization.model.PasswordConfVO;
import com.gdsp.platform.config.customization.model.SystemConfExtVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 
* 
* @Description:系统配置扩展服务实现类
* @author guoyang
* @date 2016年12月2日
 */
@Service
@Transactional
public class SystemConfExtImpl implements ISystemConfExtService {
	
	@Autowired
	private ISystemConfExtDao systemConfExtDao;

	@Override
	public GrantAgingVO queryGrantAgingConfigs() {
		GrantAgingVO agingVO = new GrantAgingVO();
		List<SystemConfExtVO> grantAgingConfigs = systemConfExtDao.querySystemConfExtVoListByCatgCode(SystemConfigConst.GRANT_AGING_CATG_CODE);
		for (SystemConfExtVO vo : grantAgingConfigs) {
			if (SystemConfigConst.GRANT_AGING_STATUS.equals(vo.getConfCode())) {
				agingVO.setStatus(vo.getConfValue());
			}else if(SystemConfigConst.GRANT_AGING_LEADTIME.equals(vo.getConfCode())){
				agingVO.setLeadTime(Integer.valueOf(vo.getConfValue()));
			}else if(SystemConfigConst.GRANT_AGING_DEFAULTAGINGTIME.equals(vo.getConfCode())){
                agingVO.setDefaultAgingTime(Integer.valueOf(vo.getConfValue()));
            }
		}
		return agingVO;
	}

	@Override
	public PasswordConfVO queryPasswordConf() {
		PasswordConfVO pwdConfVo = new PasswordConfVO();
		List<SystemConfExtVO> systemConfExtVOs = systemConfExtDao.querySystemConfExtVoListByCatgCode(SystemConfigConst.PSP_CATG_CODE);
		for(SystemConfExtVO conf : systemConfExtVOs){
			if(SystemConfigConst.PSP_TIME_LIMIT.equals(conf.getConfCode())){
				pwdConfVo.setTimeLimit(Integer.valueOf(conf.getConfValue()));
			}else if(SystemConfigConst.PSP_LENGTH.equals(conf.getConfCode())){
				pwdConfVo.setPwdLength(Integer.valueOf(conf.getConfValue()));
			}else if(SystemConfigConst.PSP_NUMBER_STATE.equals(conf.getConfCode())){
				pwdConfVo.setPwdNumberState(conf.getConfValue());
			}else if(SystemConfigConst.PSP_CARACTER_STATE.equals(conf.getConfCode())){
				pwdConfVo.setPwdCharacterState(conf.getConfValue());
			}else if(SystemConfigConst.PSP_CASE_STATE.equals(conf.getConfCode())){
				pwdConfVo.setPwdCaseState(conf.getConfValue());
			}else if(SystemConfigConst.PSP_ENGLISH_STATE.equals(conf.getConfCode())){
				pwdConfVo.setPwdEnglishState(conf.getConfValue());
			}
		}
		return pwdConfVo;
	}

	@Override
	public void updateBatch(List<SystemConfExtVO> systemConfExtVOs) {
		systemConfExtDao.updateBatch(systemConfExtVOs);
	}

	@Override
	public MailServiceConfVO queryMailServiceConfs() {
		MailServiceConfVO mailServiceConfVO = new MailServiceConfVO();
		List<SystemConfExtVO> mailServiceConfigs = systemConfExtDao.querySystemConfExtVoListByCatgCode(SystemConfigConst.SYS_MAILSERVICE_CATG_CODE);
		for (SystemConfExtVO vo : mailServiceConfigs) {
			if (SystemConfigConst.SYS_MAILSERVICE_STATUS.equals(vo.getConfCode())) {
				mailServiceConfVO.setStatus(vo.getConfValue());
			} else if (SystemConfigConst.SYS_MAILCONF_LOCATION.equals(vo.getConfCode())) {
				mailServiceConfVO.setConfLocation(vo.getConfValue());
			} else {
				continue;
			}
		}
		return mailServiceConfVO;
	}


}

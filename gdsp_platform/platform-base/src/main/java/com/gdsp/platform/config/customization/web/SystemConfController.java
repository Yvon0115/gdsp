package com.gdsp.platform.config.customization.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.platform.config.customization.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.config.customization.service.ISystemConfService;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;

/**
 * 客户化 - 系统配置
 * @author lijiayi 
 * @author guoyang 
 * @author wangqinghua
 * 2016年12月12日 上午11:09:41
 */
@Controller
@RequestMapping("func/systemconf")
public class SystemConfController {

    @Autowired
    private ISystemConfService 		systemConfservice;
    private SystemConfVO       		systemConf;
    private List<DefDocVO>     		list;
    
    @Autowired
    private ISystemConfExtService   systemConfExtService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
    @Autowired
    private IUserRoleOptPubService userRoleOptPubService;
    /**
     * 系统配置主页面<br>
     * 功能：包含系统首页设置、报表集成设置功能<br>
     * @author lijy
     * @return page
     */
    @RequestMapping("/list.d")
    public String list(Model model) {
        // 查询报表类型信息
        list = systemConfservice.queryReportType();
        // 查询最新系统配置信息
        systemConf = systemConfservice.querySystemConf();
        if (null != systemConf) {
            String reportInts = systemConf.getReportInts();
            // 根据reportInts设置报表集成多选按钮勾选情况
            for (int i = 0; i < list.size(); i++) {
                String reportType = list.get(i).getDoc_code();
                if (reportInts.indexOf(reportType) != -1) {
                    list.get(i).setChecked("checked");
                }
            }
        }
        model.addAttribute("reportTypeList", list);
        model.addAttribute("systemConf", systemConf);
        return "conf/systemconf/list";
    }

    /**
     * 系统编辑页面
     * @param model
     * @param id
     * @return 系统配置编辑页面
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        // 重置ID，用于每次点击保存都重新生成一条配置记录
//        if (null != systemConf) {
//            systemConf.setId(null);
//        }
        model.addAttribute("reportTypeList", list);
        model.addAttribute("systemConf", systemConf);
        return "conf/systemconf/editForm";
    }

    /**
     * 保存并应用系统配置信息<br>
     * 功能：1、保存系统首页配置信息和报表集成信息到数据库<br>
     * 2、应用系统首页配置信息<br>
     * @param systemConf
     * @return
     */
    @RequestMapping("/saveSystemConf.d")
    @ResponseBody
    public Object saveSystemConf(SystemConfVO systemConf) {
        systemConfservice.saveSystemConf(systemConf);
        // 更新AppConfig中的系统配置信息
        Properties properties = AppConfig.getInstance().getConfig();
        properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_STATE, systemConf.getSysHomeState());
        if (!StringUtils.isEmpty(systemConf.getSysHomeUrl())) {
            properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_URL, systemConf.getSysHomeUrl());
        } else {
            properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_URL, "");
        }
        if (!StringUtils.isEmpty(systemConf.getReportInts())) {
            properties.setProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO, systemConf.getReportInts());
        } else {
            properties.setProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO, "");
        }
        return AjaxResult.SUCCESS;
    }
    
    /**
     * 保存密码安全策略
     * @param systemConfExtVOs
     * @return
     */
    @RequestMapping("/savePasswordSecurityPolicy.d")
    @ResponseBody
    public Object savePasswordSecurityPolicy(PasswordConfVO pwdConfVo){
    	SystemConfExtVO timeLimitVo = new  SystemConfExtVO();
    	SystemConfExtVO pwdLengthVo = new  SystemConfExtVO();
    	SystemConfExtVO pwdNumberStateVo = new  SystemConfExtVO();
    	SystemConfExtVO pwdCharacterStateVo = new  SystemConfExtVO();
    	SystemConfExtVO pwdCaseStateVo = new  SystemConfExtVO();
    	SystemConfExtVO pwdEnglishStateVo = new  SystemConfExtVO();
    	
    	timeLimitVo.setConfCode(SystemConfigConst.PSP_TIME_LIMIT);
    	if(pwdConfVo.getTimeLimit() == null){
        	timeLimitVo.setConfValue("0");
    	}else{
        	timeLimitVo.setConfValue(pwdConfVo.getTimeLimit().toString());
    	}
    	
    	pwdLengthVo.setConfCode(SystemConfigConst.PSP_LENGTH);
    	if(pwdConfVo.getPwdLength() == null){
    		pwdLengthVo.setConfValue("0");
    	}else{
    		pwdLengthVo.setConfValue(pwdConfVo.getPwdLength().toString());
    	}
    	
    	pwdNumberStateVo.setConfCode(SystemConfigConst.PSP_NUMBER_STATE);
    	if(pwdConfVo.getPwdNumberState() == null || "".equals(pwdConfVo.getPwdNumberState().trim())){
    		pwdNumberStateVo.setConfValue("N");
    	}else{
    		pwdNumberStateVo.setConfValue(pwdConfVo.getPwdNumberState());
    	}
    	
    	pwdCharacterStateVo.setConfCode(SystemConfigConst.PSP_CARACTER_STATE);
    	if(pwdConfVo.getPwdCharacterState() == null || "".equals(pwdConfVo.getPwdCharacterState().trim())){
    		pwdCharacterStateVo.setConfValue("N");
    	}else{
    		pwdCharacterStateVo.setConfValue(pwdConfVo.getPwdCharacterState());
    	}
    	
    	pwdCaseStateVo.setConfCode(SystemConfigConst.PSP_CASE_STATE);
    	if(pwdConfVo.getPwdCaseState() == null || "".equals(pwdConfVo.getPwdCaseState().trim())){
    		pwdCaseStateVo.setConfValue("N");
    	}else{
    		pwdCaseStateVo.setConfValue(pwdConfVo.getPwdCaseState());
    	}
    	
    	pwdEnglishStateVo.setConfCode(SystemConfigConst.PSP_ENGLISH_STATE);
    	if(pwdConfVo.getPwdEnglishState() == null || "".equals(pwdConfVo.getPwdEnglishState().trim())){
    		pwdEnglishStateVo.setConfValue("N");
    	}else{
    		pwdEnglishStateVo.setConfValue(pwdConfVo.getPwdEnglishState());
    	}
    	
    	List<SystemConfExtVO> systemConfExtVOs = new ArrayList<SystemConfExtVO>();
    	systemConfExtVOs.add(timeLimitVo);
    	systemConfExtVOs.add(pwdLengthVo);
    	systemConfExtVOs.add(pwdNumberStateVo);
    	systemConfExtVOs.add(pwdCharacterStateVo);
    	systemConfExtVOs.add(pwdCaseStateVo);
    	systemConfExtVOs.add(pwdEnglishStateVo);
    	systemConfExtService.updateBatch(systemConfExtVOs);
    	return AjaxResult.SUCCESS;
    }
    
    @RequestMapping("/loadPasswordSecurityPolicy.d")
    @ViewWrapper(wrapped = false)
    public String loadPasswordSecurityPolicy(Model model){
    	PasswordConfVO policy = systemConfExtService.queryPasswordConf();
    	model.addAttribute("policy", policy);
    	return "conf/systemconf/policyForm";
    }
    
    @RequestMapping("/editPasswordSecurityPolicy.d")
    @ViewWrapper(wrapped = false)
    public String editPasswordSecurityPolicy(Model model){
    	PasswordConfVO policy = systemConfExtService.queryPasswordConf();
    	model.addAttribute("policy", policy);
    	return "conf/systemconf/policyEditForm";
    }
    
    /**
     * 保存权限时效配置
     * @author wangqinghua
     * @param agingVO
     * @return
     */
    @RequestMapping("saveGrantAgingConfigs.d")
    @ResponseBody
    public Object saveGrantAgingConfigs(GrantAgingVO agingVO) {
		
    	List<SystemConfExtVO> grantAgingConfigs = new ArrayList<SystemConfExtVO>();
    	// 时效开关
    	SystemConfExtVO agingStatus  = new SystemConfExtVO();
    	agingStatus.setConfCode(SystemConfigConst.GRANT_AGING_STATUS);	
    	if (agingVO.getStatus() == null) {
    		agingStatus.setConfValue("N");
		}else {
			agingStatus.setConfValue(agingVO.getStatus());
		}
    	grantAgingConfigs.add(agingStatus);
    	// 提醒前置时间
    	SystemConfExtVO leadtime  = new SystemConfExtVO();
    	leadtime.setConfCode(SystemConfigConst.GRANT_AGING_LEADTIME);
    	if (agingVO.getLeadTime() == null) {
			leadtime.setConfValue("0");
		}else {
			leadtime.setConfValue(String.valueOf(agingVO.getLeadTime()));
		}
    	grantAgingConfigs.add(leadtime);
    	//权限时效默认值
    	SystemConfExtVO defaultAgingTime  = new SystemConfExtVO();
    	defaultAgingTime.setConfCode(SystemConfigConst.GRANT_AGING_DEFAULTAGINGTIME);
    	if (agingVO.getDefaultAgingTime() == null) {
    	    defaultAgingTime.setConfValue("0");
        }else {
            defaultAgingTime.setConfValue(String.valueOf(agingVO.getDefaultAgingTime()));
        }
    	grantAgingConfigs.add(defaultAgingTime);
    	systemConfExtService.updateBatch(grantAgingConfigs);
    	AjaxResult result = new AjaxResult(AjaxResult.STATUS_SUCCESS, "保存成功");
    	return result;
	}
    
    /**
     * 加载权限时效配置
     * @author wangqinghua
     * @param model
     * @return
     */
    @RequestMapping("/loadGrantAgingConfigs.d")
    @ViewWrapper(wrapped = false)
    public String loadGrantAgingConfigs(Model model,String t){
        GrantAgingVO agingVO = systemConfExtService.queryGrantAgingConfigs();
    	model.addAttribute("agingVO", agingVO);
    	return "conf/systemconf/grant-aging-form";
    }
    @RequestMapping("/editGrantAgingConfigs.d")
    @ViewWrapper(wrapped = false)
    public String load(Model model){
        GrantAgingVO agingVOs = systemConfExtService.queryGrantAgingConfigs();
        model.addAttribute("agingVO", agingVOs);
        return "conf/systemconf/grant-aging-editForm";
    }
    
    /**
     * 由关闭状态开启权限时效的时候立即执行一次过期删除
     * @return
     */
    @RequestMapping("enableAgingAciton.d")
    @ResponseBody
    public Object enableAgingAciton(){
    	userRoleOptPubService.deleteOverdueRelations();
    	return AjaxResult.SUCCESS;
    }

    @RequestMapping("/loadMailServiceConfs.d")
    @ViewWrapper(wrapped = false)
    public String loadMailServiceConfs(Model model) {
        MailServiceConfVO mailServiceConfVO = systemConfExtService.queryMailServiceConfs();
        model.addAttribute("mailServiceConfVO",mailServiceConfVO);
        return "conf/systemconf/mailService-list";
    }

    @RequestMapping("/editMailServiceConfigs.d")
    @ViewWrapper(wrapped = false)
    public String editMailServiceConfigs(Model model) {
        MailServiceConfVO mailServiceConfVO = systemConfExtService.queryMailServiceConfs();
        model.addAttribute("mailServiceConfVO",mailServiceConfVO);
        return "conf/systemconf/mailService-editForm";
    }

    @RequestMapping("/saveMailServiceConfigs.d")
    @ResponseBody
    public Object saveMailServiceConfigs( MailServiceConfVO mailServiceConfVO ) {
        List<SystemConfExtVO> mailServiceConfigs = new ArrayList<>();
        // 邮件开关
        SystemConfExtVO mailServiceStatus  = new SystemConfExtVO();
        mailServiceStatus.setConfCode(SystemConfigConst.SYS_MAILSERVICE_STATUS);
        if (StringUtils.isEmpty(mailServiceConfVO.getStatus())) {
            mailServiceStatus.setConfValue(DBoolean.FALSE.toString());
        } else {
            mailServiceStatus.setConfValue(mailServiceConfVO.getStatus());
        }
        if (null != mailServiceConfVO.getVersion()) {
            mailServiceStatus.setVersion(mailServiceConfVO.getVersion() + 1);
        }
        mailServiceConfigs.add(mailServiceStatus);

        // 邮件服务器配置读取位置
        SystemConfExtVO mailServerConf  = new SystemConfExtVO();
        mailServerConf.setConfCode(SystemConfigConst.SYS_MAILCONF_LOCATION);
        mailServerConf.setConfValue(mailServiceConfVO.getConfLocation());
        if (null != mailServiceConfVO.getVersion()) {
            mailServerConf.setVersion(mailServiceConfVO.getVersion() + 1);
        }
        mailServiceConfigs.add(mailServerConf);

        //其他扩展配置

        systemConfExtService.updateBatch(mailServiceConfigs);
        AjaxResult result = new AjaxResult(AjaxResult.STATUS_SUCCESS, "保存成功");
        return result;
    }

    	
}

package com.gdsp.platform.log.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.model.param.PageSerImpl;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantUtils;
import com.gdsp.platform.log.dao.ILoginLogDao;
import com.gdsp.platform.log.helper.PortalQueryConst;
import com.gdsp.platform.log.model.LoginLogVO;
import com.gdsp.platform.log.service.ILoginLogService;

@Service
public class LoginLogServiceImpl implements ILoginLogService {

    @Autowired
    private ILoginLogDao         loginLogDao;
    @Autowired
    private IUserQueryPubService userQueryPubService;

    @Override
    public Page<LoginLogVO> queryPageByCondition(Pageable page, String queryParam, String type, String p_start_date, String p_end_date) {
        //查询登陆日志条件
        Condition condition = new Condition();
        List<LoginLogVO> loginLogVOs = this.getLoginLogList(condition, queryParam, type, p_start_date, p_end_date);
        //利用工具类进行假分页 lyf 2016.12.28
        Page<LoginLogVO> loginLogPage = new PageSerImpl<LoginLogVO>(null);
        loginLogPage = GrantUtils.convertListToPage(loginLogVOs, page);
        return loginLogPage;
    }
    
    @Override
    public List<LoginLogVO> findListByCondition(String queryParam,String type, String p_start_date, String p_end_date){
    	//查询登陆日志条件
        Condition condition = new Condition();
        List<LoginLogVO> loginLogList = this.getLoginLogList(condition, queryParam, type, p_start_date, p_end_date);
        return loginLogList;
    }
    
    public List<LoginLogVO> getLoginLogList(Condition condition,String queryParam, String type, String p_start_date, String p_end_date){
    	 //查询所有用户
        List<UserVO> userVOs = userQueryPubService.findAllUsersList();
        //匹配用户名的登录日志结果集volist
        List<LoginLogVO> voList = new ArrayList<LoginLogVO>();
        //条件过滤后得到的终结果
        List<LoginLogVO> loginLogVOs = new ArrayList<LoginLogVO>();
        //判断添加条件
        //自由
        /*if (PortalQueryConst.freeString.equals(type) || StringUtils.isEmpty(type)) {
            if (StringUtils.isNotEmpty(p_start_date)) {
                condition.addExpression("c.login_time", new DDate(p_start_date).getMillis() / 1000, ">=");
            }
            if (StringUtils.isNotEmpty(p_end_date)) {
                condition.addExpression("c.login_time", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
            }
        }
        //今天
        else if (PortalQueryConst.todayString.equals(type)) {
            condition.addExpression("c.login_time", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, ">=");
        }
        //昨天
        else if (PortalQueryConst.yestodayString.equals(type)) {
            condition.addExpression("c.login_time", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -1)).getMillis() / 1000, ">=");
            condition.addExpression("c.login_time", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, "<");
        }
        //近7天
        else if (PortalQueryConst.weekString.equals(type)) {
            condition.addExpression("c.login_time", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -6)).getMillis() / 1000, ">=");
        }
        //近30天
        else if (PortalQueryConst.monthString.equals(type)) {
            condition.addExpression("c.login_time", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -29)).getMillis() / 1000, ">=");
        }*/
        //修改人：wangxiaolong
        //修改时间：2017-4-6
        //修改原因：实现联动功能，首次查询近一个月的记录，以后以查询时间范围为准，改变近一个月显示
        
        
       
        if (PortalQueryConst.monthString.equals(type)) {
//            condition.addExpression("c.login_time", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -29)).getMillis() / 1000, ">=");
              condition.addExpression("c.login_time", new DDate(DateUtils.addMonth(new Date(), -1)).getMillis()/1000, ">=");
        }else {
            if (StringUtils.isNotEmpty(p_start_date)) {
                condition.addExpression("c.login_time", new DDate(p_start_date).getMillis() / 1000, ">=");
            }
            if (StringUtils.isNotEmpty(p_end_date)) {
                condition.addExpression("c.login_time", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
            }
        }
        //条件查询登录用户集合loginLogByCon
        List<LoginLogVO> loginLogByCon = loginLogDao.findListByCondition(condition);
        if (loginLogByCon != null && loginLogByCon.size() > 0 && userVOs != null && userVOs.size() > 0) {
            for (LoginLogVO loginVO : loginLogByCon) {
                for (UserVO VO : userVOs) {
                    if (loginVO.getLogin_account().equals(VO.getAccount())) {
                        loginVO.setUsername(VO.getUsername());
                        break;
                    }
                }
                voList.add(loginVO);
            }
        } else {
            voList = null;
        }
        //账号或用户名条件过滤
        if (voList != null && voList.size() > 0 && StringUtils.isNotEmpty(queryParam)) {
            for (LoginLogVO vo : voList) {
                if (StringUtils.isNotEmpty(vo.getUsername()) && vo.getUsername().contains(queryParam) || vo.getLogin_account().contains(queryParam)) {
                    loginLogVOs.add(vo);
                }
            }
        } else {
            loginLogVOs = voList;
        }
        return loginLogVOs;
    }
    
    @Override
    public List<String> getContentList(List<LoginLogVO>  loginList){
    	List<String> contentList = new ArrayList<String>();
    	if(loginList != null && loginList.size() > 0){
    		for (LoginLogVO loginLogVO : loginList) {
            	StringBuilder build = new StringBuilder();
            	if(loginLogVO.getLogin_account() != null){
            		build.append(loginLogVO.getLogin_account());
            	}
            	if(loginLogVO.getLogin_account() == null){
            		build.append("");
            	}
            	if(loginLogVO.getUsername() != null){
            		build.append(","+loginLogVO.getUsername());
            	}
            	if(loginLogVO.getUsername() == null){
            		build.append(","+"");
            	}
            	if(loginLogVO.getLogin_time() != null){
            		build.append(","+loginLogVO.getLogin_time());
            	}
            	if(loginLogVO.getLogin_time() == null){
            		build.append(","+"");
            	}
            	if(loginLogVO.getIp_addr() != null){
            		build.append(","+loginLogVO.getIp_addr());
            	}
            	if(loginLogVO.getIp_addr() == null){
            		build.append(","+"");
            	}
//            	if(loginLogVO.getMac_addr() != null){
//            		build.append(","+loginLogVO.getMac_addr());
//            	}
//            	if(loginLogVO.getMac_addr() == null){
//            		build.append(","+"");
//            	}
            	if(loginLogVO.getLogin_status() != null && "Y".equals(loginLogVO.getLogin_status())){
            		build.append(","+"成功");
            	}
            	if(loginLogVO.getLogin_status() != null && "N".equals(loginLogVO.getLogin_status())){
            		build.append(","+"失败");
            	}
            	if(loginLogVO.getLogin_status() == null){
            		build.append(","+"");
            	}
            	String lineData = build.toString();
            	contentList.add(lineData);
    		}
    	}
    	return contentList;
    }
}

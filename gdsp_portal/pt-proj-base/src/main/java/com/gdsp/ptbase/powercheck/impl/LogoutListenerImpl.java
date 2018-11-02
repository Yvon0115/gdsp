/**
 * 
 */
package com.gdsp.ptbase.powercheck.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.utils.web.HttpClientUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.login.LogoutListener;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;

/**
 * 登出后，发消息给birt服务器清除session
 * @author wangliyuan
 *
 */
@Service
public class LogoutListenerImpl implements LogoutListener{

    private static final Logger logger=LoggerFactory.getLogger(LogoutListenerImpl.class);
    
    @Override
    public void onLogout(AuthInfo account) {
        
        String url = AppConfig.getInstance().getString("birt.server.receive.mes.addr");
        String reportIntsInfo = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO);
        if(StringUtils.isNotEmpty(reportIntsInfo) && reportIntsInfo.indexOf("birt") != -1){
        	if(StringUtils.isNotEmpty(url)){       
                String userAccount= account.getAccount();
                LinkedMap<String,Object> link= new LinkedMap<String,Object>();
                link.put("account", userAccount);
                try {
                    HttpClientUtils.httpGetText(url, link);
                } catch (HttpException e) {
                    
                    logger.error("http异常",e);
                } catch (IOException e) {
                    
                    logger.error("I/O异常",e);
                }	
            }else{
            	logger.debug("birt服务未开启");
            }
        }
    }
    
    
}

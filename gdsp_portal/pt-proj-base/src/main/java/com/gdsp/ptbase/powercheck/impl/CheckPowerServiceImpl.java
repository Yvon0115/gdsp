/**
 * 
 */
package com.gdsp.ptbase.powercheck.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.ssl.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.data.json.Json2ObjectMapper;
import com.gdsp.dev.web.security.shiro.EncodePasswordService;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.ptbase.powercheck.model.ReturnMessage;
import com.gdsp.ptbase.powercheck.service.ICheckPowerService;
import com.gdsp.ptbase.powercheck.service.ITokenService;

/**
 * @author wangliyuan
 *
 */
@Service
public class CheckPowerServiceImpl implements ICheckPowerService {

    @Autowired
    private ITokenService         tokenService;
    @Resource
    private EncodePasswordService passwordService = null;
    @Autowired
    IUserQueryPubService          userPubService;
    @Autowired
    IPowerMgtQueryPubService      powerMgtPubService;
    private static Logger         logger          = LoggerFactory.getLogger(CheckPowerServiceImpl.class);

    @Override
    public String checkPower(String user, String password) {
        
        //Decrypt password
        byte[] decodeBase64 = null;
        byte[] decodeBase642 = null;
        try {
            decodeBase64 = Base64.decodeBase64(password.getBytes("utf-8"));
            decodeBase642 = Base64.decodeBase64(user.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e1) {
            
            logger.debug("不支持的编码异常！", e1);
        }
        String userPassword = new String(decodeBase64);
        String userAccount = new String(decodeBase642);
        String password_md5 = passwordService.encodePassword(userAccount, userPassword);
        //return message
        ReturnMessage message = new ReturnMessage();
        //search user
        UserVO userVO = userPubService.queryUserByAccount(userAccount);
        if (userVO == null)
        {
            message.setCode(ReturnMessage.code_user_error);
            message.setMessage("用户名不存在");
            ;
        }
        else if (!password_md5.equals(userVO.getPassword())) {
            message.setCode(ReturnMessage.code_password_error);
            message.setMessage("密码不正确");
            ;
        }
        else {
            List<MenuRegisterVO> menus = powerMgtPubService.queryMenuListByUser(userVO.getId());
            String viewCode = AppConfig.getInstance().getString("birt.viewCode");
            int count = 0;
            for (MenuRegisterVO vo : menus)
            {
                if (viewCode.equals(vo.getFuncode()))
                {
                    count++;
                    message.setCode(ReturnMessage.code_success);
                    message.setMessage("验证通过");
                    break;
                }
            }
            if (count == 0)
            {
                message.setCode(ReturnMessage.code_power_error);
                message.setMessage("权限不足");
                ;
            }
        }
        String value = "";
        try {
            value = Json2ObjectMapper.getInstance().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            
            logger.debug("json处理异常！", e);
        }
        return value;
    }

    @Override
    public String checkST(String st) {
        
        String returnMessage = "";
        String ticket = "";
        //return message 
        ReturnMessage message = new ReturnMessage();
        //get tokenCache from cache by st
        if (StringUtils.isNotEmpty(st)) {
            ticket = tokenService.saveToken(st);
            //remove tokenCache from cache by ticket
            tokenService.removeToken(ticket);
            if (StringUtils.isNotEmpty(ticket) && st.equals(ticket)) {
                message.setMessage("验证通过");
                message.setCode(ReturnMessage.code_success);
            } else {
                message.setMessage("验证失败");
                message.setCode(ReturnMessage.code_ST_error);
            }
        } else {
            message.setMessage("验证失败");
            message.setCode(ReturnMessage.code_ST_error);
        }
        try {
            returnMessage = Json2ObjectMapper.getInstance().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            
            logger.debug("json处理异常！", e);
        }
        return returnMessage;
    }
}

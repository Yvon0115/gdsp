/**
 * 
 */
package com.gdsp.ptbase.powercheck.impl;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.ptbase.powercheck.service.ITokenService;
import org.apache.commons.ssl.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * @author wangliyuan
 *
 */
@Service
public class TokenServiceImpl implements ITokenService {
    
    private static final Logger logger=LoggerFactory.getLogger(TokenServiceImpl.class);
    
    @Override
    public String issueToken(String UUID) {
        
        String ticket=null;
        byte[] encode = null;
        String account = AppContext.getContext().getContextUser().getAccount();
        Date time=new Date();
        String str=account+"&"+time+"&"+UUID;
        try {
            encode=  Base64.encodeBase64(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            
            logger.debug("不支持的编码异常", e);
        }
        ticket=new String(encode);
        // save token to cache
        saveToken(ticket);
        return ticket;
    }

    @Override
    @Cacheable(value = "tokenCache", key ="#ticket")
    public String saveToken(String ticket) {
        
        logger.debug("token has be saved the key is:"+ticket);
        return ticket;
    }

    @Override
    @CacheEvict(value ="tokenCache", key="#ticket", beforeInvocation=true,allEntries=true)
    public void removeToken(String ticket) {
        
        logger.debug("remove cache complete the cachekey is:"+ticket); 
    }
}

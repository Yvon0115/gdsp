package com.gdsp.portal.alipay.helper.filter;

import com.alipay.cap.session.common.web.service.CapRemoteSessionService;
import com.alipay.cap.session.common.web.service.impl.CapRemoteSessionServiceImpl;
import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.utils.web.HttpClientUtils;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.platform.common.helper.DpcTransParamConst;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

/**
 * @author yucl
 * @version 0.0.1 2018/5/31 15:56
 * @since 1.6
 */
@WebListener
public class SessionTimeOutListener implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(SessionTimeOutListener.class);

    private CapRemoteSessionService capRemoteSessionService;

    public SessionTimeOutListener() {
        //auto generated constructor stub
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.debug("HttpSession has been created!");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String tokenId = "";
        if (null != session.getAttribute(DpcTransParamConst.LOGIN_TOKEN_KEY)) {
            tokenId = session.getAttribute(DpcTransParamConst.LOGIN_TOKEN_KEY).toString();
        }
        LinkedMap<String, Object> params = new LinkedMap<>();
        params.put(DpcTransParamConst.LOGIN_TOKEN_KEY, tokenId);
        String uasServerUrl = FileUtils.getFileIO("uas.sessionInvalidate", DBoolean.TRUE.booleanValue());
        try {
            capRemoteSessionService.invalidate(tokenId,null);
            HttpClientUtils.httpGetText(uasServerUrl, params);
        } catch (HttpException e) {
            throw new BusinessException("exception occurs when httpclient request is requested!", e);
        } catch (IOException e) {
            throw new BusinessException("remote session invalidate error!", e);
        }
    }

    public void setCapRemoteSessionService(CapRemoteSessionServiceImpl capRemoteSessionService) {
        this.capRemoteSessionService = capRemoteSessionService;
    }
}

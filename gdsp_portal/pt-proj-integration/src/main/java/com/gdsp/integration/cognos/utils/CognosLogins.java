package com.gdsp.integration.cognos.utils;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_Port;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;

public class CognosLogins {
	private static final Logger logger = LoggerFactory.getLogger(CognosLogins.class);
    private XmlEncodedXML              credentialXML = null;
    private ContentManagerService_Port contentManager_service;

    public CognosLogins(String dispatcherEndPoint) {
        try {
            createServices(dispatcherEndPoint);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
    }

    private void createServices(String endPoint) throws MalformedURLException, ServiceException{
        java.net.URL serverURL = new java.net.URL(endPoint);
        // Create service locators
        ContentManagerService_ServiceLocator contentManager_locator = new ContentManagerService_ServiceLocator();
        // Create service ports with given URL
        contentManager_service = contentManager_locator.getcontentManagerService(serverURL);
    }

    public void setCredentials(String namespace, String userID, String password) {
        StringBuilder credentialXML = new StringBuilder();
        credentialXML.append("<credential>");
        credentialXML.append("<namespace>");
        credentialXML.append(namespace);
        credentialXML.append("</namespace>");
        credentialXML.append("<username>");
        credentialXML.append(userID);
        credentialXML.append("</username>");
        credentialXML.append("<password>");
        credentialXML.append(password);
        credentialXML.append("</password>");
        credentialXML.append("</credential>");
        this.credentialXML = new XmlEncodedXML(credentialXML.toString());
    }

    private String logon() {
        try {
            contentManager_service.logon(credentialXML, new SearchPathSingleObject[] {});
        } catch (RemoteException e) {
            logger.error(e.getMessage(),e);
        }
        BiBusHeader bibus = (BiBusHeader) ((Stub) contentManager_service).getHeaderObject("", "biBusHeader");
        String m_passportID = "";
        for (int i = 0; i < bibus.getHdrSession().getSetCookieVars().length; i++) {
            if ("cam_passport".equals(bibus.getHdrSession()
                    .getSetCookieVars()[i].getName())) {
                m_passportID = "m_passportID=" + bibus.getHdrSession()
                        .getSetCookieVars()[i].getValue();
                break;
            }
        }
        return m_passportID;
    }

    public String logon(String namespace, String uid, String pwd){
        setCredentials(namespace, uid, pwd);
        return logon();
    }
}

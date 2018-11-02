package com.gdsp.integration.cognos.utils;

import java.net.MalformedURLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.CAMPassport;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_Port;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.DisplayObject;
import com.cognos.developer.schemas.bibus._3.EventManagementService_Port;
import com.cognos.developer.schemas.bibus._3.EventManagementService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.PromptOption;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.ReportService_Port;
import com.cognos.developer.schemas.bibus._3.ReportService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.XmlEncodedXML;
import com.gdsp.dev.base.exceptions.BusinessException;

/**
 * 
 * @ClassName: CognosConnection
 * (Cognos连接类，主要是执行Cognos登录，连接，)
 * @author hongwei.xu
 * @date 2015年7月14日 下午1:36:06
 */
public class CognosConnection {

    private static final Logger         log                    = LoggerFactory.getLogger(CognosConnection.class);
    // IBM Cognos 8 services
    /** 连接cognos内容库的服务定位对象 */
    private ContentManagerService_Port  contentManager_service;
    private ReportService_Port          report_service;
    /** 连接cognos事件管理的服务定位对象 */
    private EventManagementService_Port eventManagementService = null;
    // JSP application and session
    private ServletContext              application;
    private HttpSession                 session;
    private XmlEncodedXML               credentialXML          = null;

    /**
     * 
     * <p>
     * Title: 构造函数
     * <p>
     * Description:
     * </p>
     * @param endPoint
     * @param app
     * @param sess
     * @throws ServiceException 
     * @throws MalformedURLException 
     * @throws Exception
     */
    public CognosConnection(String endPoint, ServletContext app, HttpSession sess) throws BusinessException, MalformedURLException, ServiceException {
        if (endPoint == null)
            throw new BusinessException("Invalid IBM Cognos WebService endPoint provided. Provided end point is: [" + endPoint
                    + "]");
        createServices(endPoint);
        try {
            contentManager_service.query(new SearchPathMultipleObject("~"), new PropEnum[] {}, new Sort[] {},
                    new QueryOptions());
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        }
        application = app;
        session = sess;
    }

    /**
     * @throws ServiceException 
     * @throws MalformedURLException 
     * 
    * @Title: createServices
    * (根据URL创建CognosService)
    * @param endPoint
    * @throws Exception 
    * @return void
    *      */
    private void createServices(String endPoint) throws BusinessException, MalformedURLException, ServiceException {
        // Create URL
        java.net.URL serverURL = new java.net.URL(endPoint);
        // Create service locators
        ContentManagerService_ServiceLocator contentManager_locator = new ContentManagerService_ServiceLocator();
        ReportService_ServiceLocator report_locator = new ReportService_ServiceLocator();
        // Create service ports with given URL
        contentManager_service = contentManager_locator.getcontentManagerService(serverURL);
        // 创建一个连接cognos事件管理的服务定位对象
        EventManagementService_ServiceLocator evtServiceLocator = new EventManagementService_ServiceLocator();
        // 通过cognos服务器的服务URL，得到本机调用远程cognoscognos事件管理服务的入口方法
        eventManagementService = evtServiceLocator.geteventManagementService(serverURL);
        report_service = report_locator.getreportService(serverURL);
        ((Stub) report_service).setTimeout(0);
    }

    /**
     * 
    * @Title: getNamespaces
    * (获取NameSpace)
    * @return
    * @throws Exception 
    * @return String[]
    *      */
    public String[] getNamespaces(){
        try {
            contentManager_service.logoff();
            contentManager_service.query(new SearchPathMultipleObject("/"), new PropEnum[] {}, new Sort[] {},
                    new QueryOptions());
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        }
        BiBusHeader bibus = (BiBusHeader) ((Stub) contentManager_service).getHeaderObject("", "biBusHeader");
        String[] namespaces = new String[] {};
        DisplayObject[] dob = bibus.getCAM().getException().getPromptInfo().getDisplayObjects();
        if(dob != null){
        	for (int i = 0; i < dob.length; i++) {
                if (dob[i].getName().equalsIgnoreCase("CAMNamespace")) {
                    PromptOption[] pop = dob[i].getPromptOptions();
                    if (pop != null) {
                        namespaces = new String[pop.length];
                        for (int j = 0; j < pop.length; j++)
                            namespaces[j] = pop[j].getId();
                    } else // there is only one namespace
                    {
                        namespaces = new String[1];
                        namespaces[0] = dob[i].getValue();
                    }
                    break;
                }
            }
        }else{
        	namespaces = null;
        	log.error("DisplayObject[] dob = bibus.getCAM().getException().getPromptInfo().getDisplayObjects();// dob 对象为空！");
        }
        ((Stub) contentManager_service).getServiceContext().clearHeaders();
        return namespaces;
    }

    /**
     * Set the value credentialXML using the given namespace ID, userID, and password.
     */
    /**
     * 
    * @Title: setCredentials
    * (Set the value credentialXML using the given namespace ID, userID, and password.)
    * @param namespace
    * @param userID
    * @param password  
    * @return void
    *      */
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

    /**
     * Logs user on to IBM Cognos with the given namespace ID, user ID, and password
     * 
     * @param namespace
     *            - the namespace to be connected to.
     * @param uid
     *            - the user ID used to log on
     * @param pwd
     *            - the password used to log on
     * @return - true if the logon was successfull and false otherwise
     */
    public boolean logon(String namespace, String uid, String pwd){
        setCredentials(namespace, uid, pwd);
        return logon();
    }

    /**
     * Logs user on to IBM Cognos 
     * 
     * @return - true if logon is successful, false otherwise.
     */
    private boolean logon() {
        try {
            contentManager_service.logon(credentialXML, new SearchPathSingleObject[] {});
            BiBusHeader bibus = (BiBusHeader) ((Stub) contentManager_service).getHeaderObject("", "biBusHeader");
            ((Stub) eventManagementService).setHeader("", "biBusHeader", bibus);
            ((Stub) report_service).setHeader("", "biBusHeader", bibus);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Returns true if a user has been logged on, false otherwise.
     */
    public boolean isLoggedOn() {
        // A user is logged on if there is a CAMPassport object located within
        // the CAM object of the BiBusHeader.
        try {
            CAMPassport x = ((BiBusHeader) ((Stub) contentManager_service).getHeaderObject("", "biBusHeader")).getCAM()
                    .getCAMPassport();
            log.info("isCanCallLogon::" + x.isCanCallLogon());
            return x != null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return logon();
        }
    }

    public ReportService_Port getReportService() throws BusinessException {
        if (!isLoggedOn()) {
            throw new BusinessException("IBM Cognos服务不允许访问，用户认证失败，请联系管理人员！");
        }
        BiBusHeader bibus = null;
        bibus = (BiBusHeader) ((Stub) report_service).getHeaderObject("", "biBusHeader");
        if (bibus == null) {
            BiBusHeader CMbibus = (BiBusHeader) ((Stub) contentManager_service).getHeaderObject("", "biBusHeader");
            ((Stub) report_service).setHeader("", "biBusHeader", CMbibus);
        }
        return report_service;
    }

    public ServletContext getJSPApplication() {
        return application;
    }

    public HttpSession getJSPSession() {
        return session;
    }

    public ContentManagerService_Port getContentManager_service() {
        if (!isLoggedOn()) {
            throw new BusinessException("IBM Cognos服务不允许访问，用户认证失败，请联系管理人员！");
        }
        return contentManager_service;
    }

    public ReportService_Port getReport_service() {
        return report_service;
    }

    public EventManagementService_Port getEventManagementService() {
        if (!isLoggedOn()) {
            throw new BusinessException("IBM Cognos服务不允许访问，用户认证失败，请联系管理人员！");
        }
        return eventManagementService;
    }
}

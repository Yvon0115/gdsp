package com.gdsp.integration.cognos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_Port;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.dev.web.utils.WebUtils;
import com.gdsp.integration.cognos.model.BICognoURLPropertiesVO;
import com.gdsp.integration.cognos.model.BICognosDisplayMetaVO;
import com.gdsp.integration.cognos.utils.BICognosURLGenUtils;
import com.gdsp.integration.cognos.utils.CognosConnection;
import com.gdsp.integration.cognos.utils.CognosLogins;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.integration.framework.kpi.service.IKpiExtendService;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.service.IParamService;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.integration.framework.reportentry.service.IReportOprationService;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datadic.service.IDataDicService;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
import com.gdsp.platform.systools.datalicense.service.IDataLicenseService;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceQueryPubService;
import com.gdsp.ptbase.portal.helper.PTLoggerHelper;

public class CognosOprationServiceImpl implements IReportOprationService {

    private static final PTLoggerHelper log = new PTLoggerHelper(CognosOprationServiceImpl.class);
    /** cognos 数据源 */
    private static final Map<String, Object>   cognosConnCache     = new HashMap<String, Object>();
    /** cognos 数据源 */
    private static final Map<String, Object>   cognosPassportCache = new HashMap<String, Object>();
    // 当前数据源
    private CognosConnection            currentCognosConn = null;
    private DataSourceVO                dataSource = null;
    //从application.properties中读取秘钥
    private String encryptKey = FileUtils.getFileIO("reportKey",true);

    @Override
    public void login(DataSourceVO vo) {
        this.dataSource = vo;
        
        log.debugMethodStart(vo.getUrl());
        String key = vo.getUrl() + EncryptAndDecodeUtils.getDecryptString(vo.getPassword(),encryptKey) + vo.getUsername();
        try {
            CognosConnection conn = (CognosConnection) cognosConnCache.get(key);
            Date date = (Date) cognosConnCache.get(key + "insertCognosConnectionDate");
            if (conn != null && date != null) {//缓存中有
                long time = new Date().getTime() - date.getTime();
                //时间过期,重新登录并放入缓存
                if (time >= ReportConst.EXPIRED_TIME) {
                    currentCognosConn = new CognosConnection(vo.getUrl(), null, null);
                    boolean login = currentCognosConn.logon(vo.getSpan(), vo.getUsername(), EncryptAndDecodeUtils.getDecryptString(vo.getPassword(),encryptKey));
                    if (!login) {
                        log.error("cognos login ERROR");
                        throw new BusinessException("登录出错：cognos server 认证失败，请联系管理员！");
                    }
                    cognosConnCache.put(key, currentCognosConn);
                    cognosConnCache.put(key + "insertCognosConnectionDate", new Date());
                } else {
                    currentCognosConn = conn;
                    //放入缓存
                    cognosConnCache.put(key, currentCognosConn);
                    cognosConnCache.put(key + "insertCognosConnectionDate", new Date());
                }
            } else {
                //缓存中没有，new连接并登录。
                if (currentCognosConn == null) {
                    currentCognosConn = new CognosConnection(vo.getUrl(), null, null);
                }
                boolean login = currentCognosConn.logon(vo.getSpan(), vo.getUsername(), EncryptAndDecodeUtils.getDecryptString(vo.getPassword(),encryptKey));
                if (!login) {
                    log.error("cognos login ERROR");
                    throw new BusinessException("");
                }
                //放入缓存
                cognosConnCache.put(key, currentCognosConn);
                cognosConnCache.put(key + "insertCognosConnectionDate", new Date());
            }
        } catch (Exception e) {
            log.error("initCognosConn方法中logError()打印 : cognos login ERROR",e);
            throw new BusinessException("initCognosConn方法中抛出BusinessException异常 : cognos server 认证失败，请联系管理员！" + e.getMessage(),e);
        }
        log.debugMethodEnd(vo.getUrl());
    }

    @Override
    public List<ReportVO> getReportFromRemote(DataSourceVO dataSourceVO) {
        List<ReportVO> nodes = new ArrayList<ReportVO>();
        getChildExteriorRess(getCognosConn(), dataSourceVO.getPath(), nodes);
        return nodes;
    }
    @Override
    public List<ReportVO> getReportFromRemote(String Path) {
//        List<ReportVO> nodes = new ArrayList<ReportVO>();
//        getChildExteriorRess(getCognosConn(), Path, nodes);
//        return nodes;
        return null;
    }

    @Override
    public String forwardToPage(Model model, ReportVO reportVO, List<String> param,
            List<String> kpi) {
        BICognosDisplayMetaVO reportMetaVO = new BICognosDisplayMetaVO();
        reportURLProcesser(reportVO, reportMetaVO);//处理url，单点
        queryConditionProcesser(reportVO, reportMetaVO, param);//参数处理
        kpiInfoProcesser(model, kpi);//处理指标
        powerDataDicProcesser(model);//处理数据字典授权数据，作为参数提交到报表服务器
        String page = displayPageProcesser(reportVO);//展示样式
        model.addAttribute("reportMetaVO", reportMetaVO);//页面配置
        return page;
    }

    /**
     * 
     * @Title: getChildExteriorRess
     * (获取外部资源的子节点信息)
     * @param cognosConn
     * @param parentId
     * @param type
     * @param @return 设定文件
     * @return List<JSONTreeNode> 返回类型
     *      */
    private List<ReportVO> getChildExteriorRess(CognosConnection cognosConn, String path,
            List<ReportVO> nodes) {
        // 获取cognos的目录信息。
        ContentManagerService_Port cmService = null;
        try {
            cmService = cognosConn.getContentManager_service();
            String rootSearchPath = path + dataSource.getPermissionurl();
            BaseClass[] items = cmService.query(new SearchPathMultipleObject(rootSearchPath), new PropEnum[] {
                    PropEnum.defaultName, PropEnum.searchPath, PropEnum.defaultDescription,
                    PropEnum.hasChildren, PropEnum.storeID,
                    PropEnum.objectClass,
                    PropEnum.specification
            }, new Sort[] {}, new QueryOptions());
            for (BaseClass item : items) {
                String resType = item.getObjectClass().getValue().toString();
                log.debug("节点ID" + resType);
                if (resType.equals(ReportConst.COGNOS_REPORT_TYPE_FOLDER)
                        || resType.equals(ReportConst.COGNOS_REPORT_TYPE_REPORT)
                        || resType.equals(ReportConst.COGNOS_REPORT_TYPE_QUERY)
                        || resType.equals(ReportConst.COGNOS_REPORT_TYPE_ANALYSIS)
                        || resType.equals(ReportConst.COGNOS_REPORT_TYPE_PACKAGE)) {
                    String searchPath = item.getSearchPath().getValue();
                    String name = item.getDefaultName().getValue();
                    ReportVO node = new ReportVO();
                    node.setReport_name(name);
                    node.setParent_path(path);
                    node.setLeaf(ReportConst.COGNOS_REPORT_TYPE_FOLDER.equals(resType) ||
                            ReportConst.COGNOS_REPORT_TYPE_PACKAGE.equals(resType) ? "N" : "Y");
                    node.setReport_type(resType);
                    node.setVersion(0);
                    node.setReport_path(searchPath);
                    node.setId(UUIDUtils.getRandomUUID());
                    // 默认无条件
                    node.setParam_type(ReportConst.CONDITION_TYPE_NO);
                    nodes.add(node);
                    log.debug("节点ID" + searchPath);
                    // 如果获得的是folder
                    if (resType.equals(ReportConst.COGNOS_REPORT_TYPE_FOLDER) || resType.equals(ReportConst.COGNOS_REPORT_TYPE_PACKAGE)) {
                        getChildExteriorRess(cognosConn, searchPath, nodes);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return nodes;
    }

    /**
     * 
     * @Title: getCognosConn
     * (这里用一句话描述这个方法的作用)
     * @param @return 设定文件
     * @return CognosConnection 返回类型
     *      */
    private CognosConnection getCognosConn() {
        CognosConnection conn = null;
        if (cognosConnCache.size() > 0) {
            conn = (CognosConnection) cognosConnCache.get(dataSource.getUrl() + EncryptAndDecodeUtils.getDecryptString(dataSource.getPassword(),encryptKey) + dataSource.getUsername());
            Date date = (Date) cognosConnCache.get("insertCognosConnectionDate");
            System.out.println(date);
            if (conn != null && date != null && (new Date().getTime() - date.getTime()) < ReportConst.EXPIRED_TIME) {
                return conn;
            } else {
                log.debugMethodStart(dataSource.getUrl());
                try {
                    conn = new CognosConnection(dataSource.getUrl(), null, null);
                    boolean login = conn.logon(dataSource.getSpan(), dataSource.getUsername(), EncryptAndDecodeUtils.getDecryptString(dataSource.getPassword(),encryptKey));
                    if (!login) {
                        log.logger.error("getCognosConn方法抛出：cognos login ERROR");
                        throw new BusinessException("cognos server 认证失败，请联系管理员！");
                    }
                } catch (Exception e) {
                    log.error("getCognosConn方法catch抛出：cognos login ERROR",e);
                    throw new BusinessException("cognos server 认证失败，请联系管理员！");
                }
                log.debugMethodEnd(dataSource.getUrl());
                return conn;
            }
        }
        return conn;
    }

    private void reportURLProcesser(ReportVO reportVO, BICognosDisplayMetaVO reportMetaVO) {
        String reportTYpe = reportVO.getReport_type();
        String url = null;
        if (!StringUtils.isEmpty(reportTYpe) && reportTYpe.equals(ReportConst.COGNOS_REPORT_TYPE_DEMO)) {
            url = WebUtils.getHtmlPath() + reportVO.getParam_template_path();
        } else {
            BICognoURLPropertiesVO cognosURLvo = new BICognoURLPropertiesVO();
            //reportID
            cognosURLvo.setUi_object(reportVO.getReport_path());
            cognosURLvo.setUi_tool(reportVO.getReport_type());
            String ds_id = reportVO.getData_source();
            IDataSourceQueryPubService dataSourceQueryPubService = AppContext.lookupBean(IDataSourceQueryPubService.class);
            DataSourceVO dsVo = dataSourceQueryPubService.load(ds_id);
            cognosURLvo.setCognosBasicURL(dsVo.getUrl());
            cognosURLvo.setSpan(dsVo.getSpan());
            cognosURLvo.setUsername(dsVo.getUsername());
            cognosURLvo.setPassword(EncryptAndDecodeUtils.getDecryptString(dsVo.getPassword(),encryptKey));
            url = getCognosURL(cognosURLvo);
        }
        //报表类型
        reportMetaVO.setReport_type(reportTYpe);
        if (!StringUtils.isEmpty(url)) {
            reportMetaVO.setUrl(url);
        }
        reportMetaVO.setComments(reportVO.getComments());
    }

    private void queryConditionProcesser(ReportVO reportVO, BICognosDisplayMetaVO reportMetaVO, List<String> param) {
        //没有参数模板-自由参数
        String param_type = reportVO.getParam_type();
        //自由定义
        if (StringUtils.isEmpty(param_type) || param_type.equals(ReportConst.PARAM_free)) {
            IParamService paramService = AppContext.lookupBean(IParamService.class);
            List<AcParam> params = paramService.queryByIds(param);
            freeConditionProcesser(params, reportMetaVO);
        }
        //模板
        else if (param_type.equals(ReportConst.PARAM_TEMPLATE)) {
            reportMetaVO.getOtherVarMap().put("param_template_path", reportVO.getParam_template_path());
        }
        //无参数
        else {}
        //参数类型
        reportMetaVO.setParam_type(param_type);
    }

    /**
    * @param kpi 
    * 
    * @Title: kpiInfoProcesser
    * (kpi处理逻辑)
    * @param model
    * @param portletMeta - 资源元数据
    * @return void
    *     */
    private void kpiInfoProcesser(Model model, List<String> kpi) {
        //处理报表说明显示
        List<KpisVO> kpiList = new ArrayList<KpisVO>();
        if (kpi.size() > 0) {
            IKpiExtendService ikpiService = AppContext.lookupBean(IKpiExtendService.class);
            kpiList = ikpiService.queryKpiList(kpi);
        }
        model.addAttribute("kpiList", kpiList);
    }
    /**
     * 处理有权限的数据字典
     * @param model
     */
    private void powerDataDicProcesser(Model model){
        Map<String,StringBuilder> map= new HashMap<String,StringBuilder>();
        String UserId = AppContext.getContext().getContextUserId();
        String userAccount = AppContext.getContext().getContextAccount(); 
        IUserQueryPubService userQueryPubService = AppContext.lookupBean(IUserQueryPubService.class);
        UserVO userVO = userQueryPubService.load(UserId);
        IOrgQueryPubService orgQueryPubService = AppContext.lookupBean(IOrgQueryPubService.class);
        OrgVO orgVO = orgQueryPubService.load(userVO.getPk_org());
        //当前用户所属角色
        IUserRoleQueryPubService userRoleQueryPubService = AppContext.lookupBean(IUserRoleQueryPubService.class);
        List<RoleVO> queryRoleList = userRoleQueryPubService.queryRoleListByUserId(UserId);
        List<String> roleIdList = new ArrayList<String>();
        if (queryRoleList != null && queryRoleList.size() > 0) {
            for (RoleVO roleVO : queryRoleList) {
                roleIdList.add(roleVO.getId());
            }
        }
            //查询当前系统登录用户有权限的数据字典
            IDataLicenseService dataLicenseService = AppContext.lookupBean(IDataLicenseService.class);
            List<DataLicenseVO>  queryDataDicList = dataLicenseService.queryDataDicByRoleIds(roleIdList);
            List<String> dicValueIdsList = new ArrayList<String>();
            if (queryDataDicList != null && queryDataDicList.size() > 0) {
                Set<String> dataDicValueIdSet = new HashSet<String>();
                for (DataLicenseVO dataVO : queryDataDicList) {
                    dataDicValueIdSet.add(dataVO.getPk_dicval());
                }
                Iterator iterator = dataDicValueIdSet.iterator();
                while (iterator.hasNext()) {
                    dicValueIdsList.add((String) iterator.next());
                }
                //查询当前登录用户有权限的数据字典值详细信息
                IDataDicService dataDicService = AppContext.lookupBean(IDataDicService.class);
                List<RoleAuthorityVO> queryDataDicInfoList = dataDicService.queryDataDicInfo(dicValueIdsList);
             if(queryDataDicInfoList!=null && queryDataDicInfoList.size()>0){
                 for(int i=0;i<queryDataDicInfoList.size();i++){
                     String key=queryDataDicInfoList.get(i).getDic_code();
                     if(map.containsKey(key)){
                         map.get(key).append(","); 
                      map.get(key).append(queryDataDicInfoList.get(i).getDimvl_code());    
                     }else{
                         StringBuilder sb=new StringBuilder();
                         StringBuilder append = sb.append(queryDataDicInfoList.get(i).getDimvl_code());
                         map.put(key, append);
                     }
                  }
               }
            } 
        model.addAttribute("orgcode", orgVO.getOrgcode());
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("dataDic", map);
    }
    
    private String displayPageProcesser(ReportVO reportVO) {
        //页面
        int menuType = FuncConst.MENUTYPE_BUSI;
        if (AppContext.getContext().getRequestInfo() != null) {
            menuType = AppContext.getContext().getRequestInfo().getMenuType();
        }
        //页面或者参数无条件,或者原生报表，条件以及到处都是用原来的Cognos自带功能
        if (FuncConst.MENUTYPE_PAGE == menuType
                || ReportConst.CONDITION_TYPE_NO.equals(reportVO.getParam_type())
                || ReportConst.COGNOS_REPORT_TYPE_FIX.equals(reportVO.getReport_type())) {
            return "/portlet/cognos/cognos-page";
        }
        return "/portlet/cognos/cognos-fun";
    }

    /**
     * 
    * @Title: getCognosURL
    * (构造CognosUI)
    * @param urlPropertiesVO  - cognosURL属性
    * @return void 
    *      */
    private String getCognosURL(BICognoURLPropertiesVO propertiesVO) {
        //设置cognosURL
        String strCognosURL = BICognosURLGenUtils.getCognosURL(propertiesVO);
        /**
         * 设置m_passportID方式登录
         */
        String needLogin = AppConfig.getInstance().getString("cognos.login.passport.switch", "N");
        //需要 拼接passport认证
        if ("Y".equals(needLogin)) {
            String passport = (String) cognosPassportCache.get("passport");
            Date date = (Date) cognosPassportCache.get("passportDate");
            if (passport != null && date != null) {//缓存中有
                long time = new Date().getTime() - date.getTime();
                //时间过期,重新登录并放入缓存
                if (time >= ReportConst.EXPIRED_TIME) {
                    CognosLogins login = new CognosLogins(propertiesVO.getCognosBasicURL());
                    try {
                        passport = login.logon(propertiesVO.getSpan(), propertiesVO.getUsername(), EncryptAndDecodeUtils.getDecryptString(propertiesVO.getPassword(),encryptKey));
                    } catch (Exception e) {
                    	log.error(e.getMessage(),e);
                        throw new BusinessException("生成passport失败",e);
                    }
                    if (passport != null) {
                        cognosPassportCache.put("passport", passport);
                        cognosPassportCache.put("passportDate", new Date());
                    }
                } else {
                    //放入缓存
                    cognosPassportCache.put("passport", passport);
                    cognosPassportCache.put("passportDate", new Date());
                }
            } else {
                //缓存中没有，new连接并登录。
                if (passport == null) {
                    CognosLogins login = new CognosLogins(propertiesVO.getCognosBasicURL());
                    try {
                        passport = login.logon(propertiesVO.getSpan(), propertiesVO.getUsername(), EncryptAndDecodeUtils.getDecryptString(propertiesVO.getPassword(),encryptKey));
                    } catch (Exception e) {
                    	log.error(e.getMessage(),e);
                        throw new BusinessException("生成passport失败",e);
                    }
                }
                //放入缓存
                if (passport != null) {
                    cognosPassportCache.put("passport", passport);
                    cognosPassportCache.put("passportDate", new Date());
                }
            }
            strCognosURL += "&" + passport;
        }
        return strCognosURL;
    }

    /**
    * @param reportMetaVO 
    * @throws DevException 
    * 
    * @Title: freeConditionProcesser
    * (处理自由设置参数)
    * @param     设定文件
    * @return void    返回类型
    *     */
    private void freeConditionProcesser(List<AcParam> params, BICognosDisplayMetaVO reportMetaVO) {
        if (params == null) {
            return;
        }
        Iterator<AcParam> it = params.iterator();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> defaultValueMap = new HashMap<String, Object>();
        Map<String, Object> cubeValueMap = new HashMap<String, Object>();
        while (it.hasNext()) {
            AcParam parametrVo = it.next();
            String dataSourceType = parametrVo.getDataFromType();//数据来源类型
            String dataSource = parametrVo.getDataFrom();//数据源
            String paraKey = parametrVo.getName();
            String cube_text_format = parametrVo.getCubeTextFormat();
            //自定义档案
            if (ReportConst.DOC_LIST.equals(dataSourceType)) {
                IDefDocListService defListService = AppContext.lookupBean(IDefDocListService.class);
                List<DefDocVO> docList = defListService.getDefDocsByTypeID(dataSource);
                paramMap.put(paraKey, docList);
            }
            //处理cube报表的格式值
            cubeValueMap.put(paraKey, cube_text_format);
            //处理默认值
            String defaultValue = parametrVo.getDefaultValue();
            if (!StringUtils.isEmpty(defaultValue)) {
                defaultValueMap.put(paraKey, defaultValue);
            }
        }
        reportMetaVO.setDvMap(defaultValueMap);
        reportMetaVO.setCtMap(paramMap);
        reportMetaVO.setParams(params);
        reportMetaVO.setCubeValueMap(cubeValueMap);
    }

}

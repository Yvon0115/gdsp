package com.gdsp.integration.smartbi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

import smartbi.catalogtree.ICatalogElement;
import smartbi.sdk.ClientConnector;
import smartbi.sdk.InvokeResult;
import smartbi.sdk.service.catalog.CatalogService;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.integration.framework.kpi.service.IKpiExtendService;
import com.gdsp.integration.framework.reportentry.model.BIReportDisplayMetaVO;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.integration.framework.reportentry.service.IReportOprationService;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;
import com.gdsp.integration.smartbi.model.SmartbiURLPropVO;
import com.gdsp.integration.smartbi.utils.SmartbiConnection;
import com.gdsp.integration.smartbi.utils.SmartbiURLGenerator;
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

public class SmartbiOprationServiceImpl implements IReportOprationService {

    private static final PTLoggerHelper log = new PTLoggerHelper(SmartbiOprationServiceImpl.class);
    //smartbi连接工具类
    private SmartbiConnection conn = null;
    //smartbi连接器
    private ClientConnector connector = null;
    //smartbi目录服务
    private CatalogService catalogService = null;
    //从application.properties中读取秘钥
    private String encryptKey = FileUtils.getFileIO("reportKey",true);    
    @Override
    public void login(DataSourceVO vo) {
        log.debugMethodStart(vo.getUrl());
        log.debug("smartBI url:"+vo.getUrl());
        log.debug("smartBI userName:"+vo.getUsername());
        log.debug("smartBI password:"+EncryptAndDecodeUtils.getDecryptString(vo.getPassword(), encryptKey));
        conn = new SmartbiConnection();
        try{
        	connector = conn.createClientConnector(vo.getUrl(), vo.getUsername(), EncryptAndDecodeUtils.getDecryptString(vo.getPassword(),encryptKey));
        }catch(Exception e){
        	conn.closeClientConnector(connector);
        	log.error("smartbi创建连接出错或smartbi认证出错!", e);
        }
        log.debugMethodEnd(vo.getUrl());
    }

    @Override
    public List<ReportVO> getReportFromRemote(DataSourceVO dataSourceVO) {
      //获取smartbi报表目录ID
        String dirID = dataSourceVO.getPath();
//        String dirID = AppConfig.getInstance().getString("smartbi.dirId");
        if(dirID != null){
            //递归获取所有节点
            List<ReportVO> nodes = new ArrayList<ReportVO>();
            //创建smartbi目录服务
            catalogService = conn.createCatalogService(connector);
            String[] ids = dirID.split(",");
            log.debug("smartBI dirId: "+dirID);
            log.debug(catalogService.toString());
            if(ids != null && ids.length > 0){
//                log.debug("smartBI的ids:"+ids.toString());
                for (String id : ids) {
                    log.debug("smartBI 变量ID "+id);
                    //循环获取smartbi目录和报表信息
                    ICatalogElement element = catalogService.getCatalogElementById(id);
                    //转换为报表资源VO
                    ReportVO node = new ReportVO();
                    if(element!=null){
                        node.setResource_id(element.getId());
//                        node.setReport_name(element.getName());
                        //显示别名
                        node.setReport_name(element.getAlias());
                        node.setParent_path(dataSourceVO.getPath());
                        // 是否为叶子节点规则
                        node.setLeaf(element.isHasChild()?"N":"Y");
                        node.setReport_type(element.getType());
                        node.setVersion(0);
                        //获取smartbi目录或报表的路径
                        InvokeResult ret = connector.remoteInvoke("CatalogService", "getCatalogElementFullPath",
                                new Object[] { element.getId() });
                        node.setReport_path(ret == null ? null : ret.getResult().toString());
                        node.setId(UUIDUtils.getRandomUUID());
                        //默认无条件
                        node.setParam_type(ReportConst.SMARTBI_CONDITION_TYPE_NO);
                        nodes.add(node);
                        //递归获取目录的子节点，并加入nodes
                        getChildNodes(element,nodes); 
                    }else{
                        nodes.add(node);
                    }
                }
            }
            return nodes;
        }
        return null;
    }
    
    @Override
    public List<ReportVO> getReportFromRemote(String path) {
//        //获取smartbi报表目录ID
//        IDataSourceService dataSourceService = AppContext.lookupBean(IDataSourceService.class); 
//        List<DataSourceVO> lists = dataSourceService.queryDataSourceByType("smartbi");
//        String dirID = lists.get(0).getDirID();
////        String dirID = AppConfig.getInstance().getString("smartbi.dirId");
//        if(dirID != null){
//        	//递归获取所有节点
//        	List<ReportVO> nodes = new ArrayList<ReportVO>();
//        	//创建smartbi目录服务
//        	catalogService = conn.createCatalogService(connector);
//        	String[] ids = dirID.split(",");
//        	for (String id : ids) {
//        		//循环获取smartbi目录和报表信息
//        		ICatalogElement element = catalogService.getCatalogElementById(id);
//        		//转换为报表资源VO
//        		ReportVO node = new ReportVO();
//        		node.setResource_id(element.getId());
//                node.setReport_name(element.getName());
//                node.setParent_path(path);
//                // 是否为叶子节点规则
//                node.setLeaf(element.isHasChild()?"N":"Y");
//                node.setReport_type(element.getType());
//                node.setVersion(0);
//                //获取smartbi目录或报表的路径
//                InvokeResult ret = connector.remoteInvoke("CatalogService", "getCatalogElementFullPath",
//        				new Object[] { element.getId() });
//                node.setReport_path(ret == null ? null : ret.getResult().toString());
//                node.setId(UUIDUtils.getRandomUUID());
//                //默认无条件
//                node.setParam_type(ReportConst.SMARTBI_CONDITION_TYPE_NO);
//                nodes.add(node);
//                //递归获取目录的子节点，并加入nodes
//        		getChildNodes(element,nodes);
//			}
//            return nodes;
//        }
        return null;
    }
    /**
     * 
     * @Title: getChildNodes 
     * @Description: 递归获取子节点
     * @param element
     * @param nodes
     * @return
     * @return List<ReportVO>    返回类型
     */
    private List<ReportVO> getChildNodes(ICatalogElement element,
			List<ReportVO> nodes) {
    	List<? extends ICatalogElement> elements = catalogService.getChildElements(element.getId());
        if(elements != null){
        	InvokeResult parentPath = connector.remoteInvoke("CatalogService", "getCatalogElementFullPath",
    				new Object[] { element.getId() });
        	for (int i = 0; i < elements.size(); i++) {
        		ICatalogElement elementItem = elements.get(i);
        		String type = elementItem.getType();
        		// 根据报表类型过滤同步的资源，以下报表类型可同步
                if(ReportConst.SMARTBI_REPORT_TYPE_DEFAULT_TREENODE.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_SELF_TREENODE.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_INSIGHT.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_COMBINED_QUERY.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_SIMPLE_REPORT.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_DASHBOARD.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_DASHBOARDMAP.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_OLAP_REPORT.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_SPREADSHEET_REPORT.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_OFFICE_REPORT.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_PAGE.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_URL.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_FILE_RESOURCE.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_BUSINESS_VIEW.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_TEXT_BUSINESS_VIEW.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_RAWSQL_BUSINESS_VIEW.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_PROC_BUSINESS_VIEW.equals(type)
                        || ReportConst.SMARTBI_REPORT_TYPE_JAVA_BUSINESS_VIEW.equals(type)){//只同步这些类型的报表
        			ReportVO node = new ReportVO();
        			node.setResource_id(elementItem.getId());
//                    node.setReport_name(elementItem.getName());
                    //同步别名
                    node.setReport_name(elementItem.getAlias());
                    node.setParent_path(parentPath == null ? null : parentPath.getResult().toString());
                    //排除查询类资源
                    if (type.endsWith("VIEW")) {
                        node.setLeaf("Y");
                    }else {
                        node.setLeaf(elementItem.isHasChild()?"N":"Y");
                    }
                    node.setReport_type(elementItem.getType());
                    node.setVersion(0);
                    InvokeResult path = connector.remoteInvoke("CatalogService", "getCatalogElementFullPath",
            				new Object[] { elementItem.getId() });
                    node.setReport_path(path == null ? null : path.getResult().toString());
                    node.setId(UUIDUtils.getRandomUUID());
                    //默认无条件
                    node.setParam_type(ReportConst.CONDITION_TYPE_NO);
                    nodes.add(node);
                    log.debug("#节点路径 ：   " + (path == null ? "节点路径为空！" : path.getResult().toString()) );
                    //如果节点还有子节点，继续递归
                    if ("N".equalsIgnoreCase(node.getLeaf())&&elementItem.isHasChild()) {
                    	getChildNodes(elementItem, nodes);
                    }
        		}
    		}
        	return nodes;
        }
    	return null;
	}

	@Override
    public String forwardToPage(Model model, ReportVO reportVO, List<String> param,
            List<String> kpi) {
		BIReportDisplayMetaVO reportMetaVO = new BIReportDisplayMetaVO();
        displayPageProcesser(reportVO, reportMetaVO); //处理展现层
        kpiInfoProcesser(model, kpi);//处理指标指引
        powerDataDicProcesser(model);//处理数据字典授权数据，作为参数提交到报表服务器
        model.addAttribute("reportMetaVO", reportMetaVO);
        return "/portlet/smartbi/smartbi-fun";
    }
	/**
     * 设置查询区域
     * 拼接url
     * @param reportMetaVO 
     */
    private void displayPageProcesser(ReportVO reportVO, BIReportDisplayMetaVO reportMetaVO) {
        int menuType = 4;//页面定制类型
        if (AppContext.getContext().getRequestInfo() != null) {
            menuType = AppContext.getContext().getRequestInfo().getMenuType();
        }
        IDataSourceQueryPubService dataSourceQueryPubService = AppContext.lookupBean(IDataSourceQueryPubService.class);
        DataSourceVO dataSourceVo = dataSourceQueryPubService.load(reportVO.getData_source());
        if (dataSourceVo == null) {
            throw new BusinessException("未找到数据源，请联系管理员！");
        }
        String url = dataSourceVo.getUrl();//获取公共路径（目录前的路径）
        String resid = reportVO.getResource_id();
        String name = "查询条件";
        String comments = reportVO.getComments();
        if (comments != null) {//添加换行符处理
            comments = comments.replace("\r\n", "<br>");
        }
        if (menuType == 4) {
            name = reportVO.getReport_name();//设置为报表名称
        }
        reportMetaVO.setComments(comments);
        reportMetaVO.setName(name);
        reportMetaVO.setMenuType(menuType);
        if (StringUtils.isNotEmpty(resid)) {
            String needLogin = AppConfig.getInstance().getString("smartbi.login.passport.switch", "N");
        	SmartbiURLPropVO propertiesVO = new SmartbiURLPropVO();
        	propertiesVO.setBaseURL(url + ReportConst.SMARTBI_QUERY_SUFFIX + resid);
        	propertiesVO.setUsername(dataSourceVo.getUsername());
        	propertiesVO.setPassword(EncryptAndDecodeUtils.getDecryptString(dataSourceVo.getPassword(),encryptKey));
        	String queryUrl = SmartbiURLGenerator.getSmartbiURL(propertiesVO,needLogin);
            reportMetaVO.setQueryUrl(queryUrl);//设置查询路径
        }
        //设置查询区域路径
        if (StringUtils.isEmpty(reportVO.getParam_template_path()) ||
                "default.ftl".endsWith(reportVO.getParam_template_path())) {
            reportMetaVO.setParamFilePath("default.ftl");
            reportMetaVO.setMenuType(4);//设置menuType，用来隐藏查询条件
        } else {
            reportMetaVO.setParamFilePath(reportVO.getParam_template_path());
        }
    }

    /**
     * 加载指标信息
     * @param model
     * @param portletMeta
     */
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

}

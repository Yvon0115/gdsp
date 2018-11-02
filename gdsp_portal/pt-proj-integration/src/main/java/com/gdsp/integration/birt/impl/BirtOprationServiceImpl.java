package com.gdsp.integration.birt.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gdsp.birt.interfaceintegrate.service.IBirtInterfaceIntegrateService;
import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.integration.framework.kpi.service.IKpiExtendService;
import com.gdsp.integration.framework.reportentry.model.BIReportDisplayMetaVO;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.integration.framework.reportentry.service.IReportOprationService;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;
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
import com.gdsp.platform.systools.datasource.service.IDataSourceService;
import com.gdsp.ptbase.powercheck.service.ITokenService;


public class BirtOprationServiceImpl implements IReportOprationService {
    
    private static Logger logger = LoggerFactory.getLogger(BirtOprationServiceImpl.class);  
    @Override
    public void login(DataSourceVO vo) {
        logger.info("kongfangfa");
    }
    @Override
    public List<ReportVO> getReportFromRemote(DataSourceVO dataSourceVO) {
        List<ReportVO> vos = new ArrayList<ReportVO>();
        HessianProxyFactory factory = new HessianProxyFactory();
      //注意设置超时时间 毫秒  
        factory.setConnectTimeout(ReportConst.EXPIRED_TIME);
        //注意设置读取时间 毫秒
        factory.setReadTimeout(ReportConst.EXPIRED_TIME);
        IBirtInterfaceIntegrateService birtService = null; 
        String randomUUID = UUIDUtils.getRandomUUID();
        ITokenService tokenService=AppContext.lookupBean(ITokenService.class);
        String produceToken = tokenService.issueToken(randomUUID); 
         try {
            String url = dataSourceVO.getUrl();
//            String url = AppConfig.getInstance().getString("birt.url");//获取公共路径（目录前的路径）
            String realUrl=url+"?token="+produceToken;
            birtService = (IBirtInterfaceIntegrateService) factory.create(IBirtInterfaceIntegrateService.class, realUrl);
        } catch (MalformedURLException e) {
            logger.error("url不正确！", e);
        }  
        //调用服务端接口
        List<com.gdsp.birt.interfaceintegrate.model.ReportVO> reportVOList = null;
        if(birtService != null){
            try {
                reportVOList = birtService.getReportFromRemote("/");
            } catch (RuntimeException e) {
                logger.error("无访问权限！", e);
                throw new BusinessException("无访问权限！");
            }
           
            
        }
        if(reportVOList != null){
            for (com.gdsp.birt.interfaceintegrate.model.ReportVO reportVO : reportVOList) {
                ReportVO reVO = new ReportVO();
                reVO.setReport_name(reportVO.getReport_name());
                reVO.setReport_path(reportVO.getReport_path());
                reVO.setParent_path(reportVO.getParent_path());
                reVO.setVersion(0);
                reVO.setId(UUIDUtils.getRandomUUID());
                reVO.setLeaf(reportVO.getLeaf());
                reVO.setReport_type(ReportConst.BIRT);
                if ("Y".equals(reportVO.getLeaf())) {
                    reVO.setParam_type(ReportConst.PARAM_TEMPLATE);
                }
                vos.add(reVO);
            }
        }else{
            logger.error("报表服务获取失败！");
        }
        return vos;
    }
    @Override
    public List<ReportVO> getReportFromRemote(String path) {
//        List<ReportVO> vos = new ArrayList<ReportVO>();
//        HessianProxyFactory factory = new HessianProxyFactory();
//        //注意设置超时时间 毫秒  
//        factory.setConnectTimeout(ReportConst.EXPIRED_TIME);
//        //注意设置读取时间 毫秒
//        factory.setReadTimeout(ReportConst.EXPIRED_TIME);
//        IBirtInterfaceIntegrateService birtService = null; 
//        String randomUUID = UUIDUtils.getRandomUUID();
//        ITokenService tokenService=AppContext.lookupBean(ITokenService.class);
//        String produceToken = tokenService.issueToken(randomUUID); 
//         try {
//            IDataSourceService dataSourceService = AppContext.lookupBean(IDataSourceService.class); 
//            List<DataSourceVO> lists = dataSourceService.queryDataSourceByType("birt"); 
//            String url = lists.get(0).getUrl();
////            String url = AppConfig.getInstance().getString("birt.url");//获取公共路径（目录前的路径）
//            String realUrl=url+"?token="+produceToken;
//            birtService = (IBirtInterfaceIntegrateService) factory.create(IBirtInterfaceIntegrateService.class, realUrl);
//        } catch (MalformedURLException e) {
//            logger.error("url不正确！", e);
//        }  
//        //调用服务端接口
//        List<com.gdsp.birt.interfaceintegrate.model.ReportVO> reportVOList = null;
//        if(birtService != null){
//            try {
//                reportVOList = birtService.getReportFromRemote("/");
//            } catch (RuntimeException e) {
//                logger.error("无访问权限！", e);
//                throw new BusinessException("无访问权限！");
//            }
//           
//        	
//        }
//        if(reportVOList != null){
//        	for (com.gdsp.birt.interfaceintegrate.model.ReportVO reportVO : reportVOList) {
//                ReportVO reVO = new ReportVO();
//                reVO.setReport_name(reportVO.getReport_name());
//                reVO.setReport_path(reportVO.getReport_path());
//                reVO.setParent_path(reportVO.getParent_path());
//                reVO.setVersion(0);
//                reVO.setId(UUIDUtils.getRandomUUID());
//                reVO.setLeaf(reportVO.getLeaf());
//                reVO.setReport_type(ReportConst.BIRT);
//                if ("Y".equals(reportVO.getLeaf())) {
//                    reVO.setParam_type(ReportConst.PARAM_TEMPLATE);
//                }
//                vos.add(reVO);
//            }
//        }else{
//        	logger.error("报表服务获取失败！");
//        }
//        return vos;
        return null;
    }

    @Override
    public String forwardToPage(Model model, ReportVO reportVO,
            List<String> param, List<String> kpi) {
        BIReportDisplayMetaVO reportMetaVO = new BIReportDisplayMetaVO();
        displayPageProcesser(reportVO, reportMetaVO); //处理展现层
        kpiInfoProcesser(model, kpi);//处理指标指引
        powerDataDicProcesser(model);//处理数据字典授权数据，作为参数提交到报表服务器
        model.addAttribute("reportMetaVO", reportMetaVO);
        return "/portlet/birt/birt-fun";
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
        String url = dataSourceVo.getPx_url();//获取公共路径（目录前的路径）
        String reportPath = reportVO.getReport_path();
        String  relPath = reportPath.replace("\\", "\\\\");
        String name = "查询条件";
        String comments = reportVO.getComments();
        if (comments != null) {//添加换行符处理
            comments = comments.replace("\r\n", "<br>");
        }
        reportMetaVO.setComments(comments);
        reportMetaVO.setName(name);
        reportMetaVO.setMenuType(menuType);
        if (StringUtils.isNotEmpty(relPath)) {
            reportMetaVO.setQueryUrl(url + ReportConst.BIRT_QUERY_SUFFIX + relPath);//设置查询路径
            reportMetaVO.setExportUrl(url + ReportConst.BIRT_QUERY_SUFFIX + relPath + ReportConst.BIRT_EXPORT_SUFFIX);//设置导出路径
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
     *  加载指标信息
     * @param model
     * @param portletMeta
     */
    private void kpiInfoProcesser(Model model, List<String> kpi) {
        //处理报表说明显示
        List<KpisVO> kpiList = new ArrayList<KpisVO>();
        if (!kpi.isEmpty()) {
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

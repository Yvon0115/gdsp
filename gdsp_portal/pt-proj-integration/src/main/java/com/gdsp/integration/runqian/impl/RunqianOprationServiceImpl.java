package com.gdsp.integration.runqian.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.ui.Model;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;
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
import com.gdsp.ptbase.portal.helper.PTLoggerHelper;

public class RunqianOprationServiceImpl implements IReportOprationService {

    private static final PTLoggerHelper LOG       = new PTLoggerHelper(RunqianOprationServiceImpl.class);
    // host地址
    private String                      host;
    // 用户名
    private String                      userName;
    // 用户密码
    private String                      passWord;
    // 端口
    private int                         port;
    FTPClient                           ftpClient = new FTPClient();
    //从application.properties中读取秘钥
    private String encryptKey = FileUtils.getFileIO("reportKey",true);

    @Override
    public void login(DataSourceVO vo) {
        
        this.host = vo.getIp();
        this.userName = vo.getUsername();
        this.passWord = EncryptAndDecodeUtils.getDecryptString(vo.getPassword(),encryptKey);
        this.port = Integer.valueOf(vo.getPort());
        ftpInit();
    }

    /**
     *  初始化连接
     */
    private void ftpInit() {
        // 对有无port的情况，采用不同的连接
        try {
            if (-1 == port) {
                ftpClient.connect(host);
            } else {
                ftpClient.connect(host, port);
            }
            if (!ftpClient.login(userName, passWord)) {
                LOG.error("用户名或密码错误，登录失败");
                throw new BusinessException("连接服务器失败，失败信息：" + ftpClient.getReplyString());
            } else {
                LOG.debug("用户：" + userName + "成功登陆服务器。");
            }
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding(ReportConst.ECODING_METHOD_UTF);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();//设置连接方式为被动模式
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public List<ReportVO> getReportFromRemote(DataSourceVO dataSourceVO) {
        List<ReportVO> reportVOs = new ArrayList<ReportVO>();
        try {
            getAllFromRemoteDirectory(dataSourceVO.getPath(), reportVOs);
        } catch (IOException e) {
            LOG.error(e.getMessage(),e);
        }
        return reportVOs;
    }
    
    @Override
    public List<ReportVO> getReportFromRemote(String path) {
//        List<ReportVO> reportVO = new ArrayList<ReportVO>();
//        try {
//            getAllFromRemoteDirectory(path, reportVO);
//        } catch (IOException e) {
//            LOG.error(e.getMessage(),e);
//        }
//        return reportVO;
        return null;
    }

    /**
     * 获取远程文件路径下的所有文件(文件和文件夹)
     * @param path
     * @return
     * @throws IOException 
     */
    private void getAllFromRemoteDirectory(String path, List<ReportVO> reportVO) throws IOException {
        LOG.debug("currentPath:" + path);
        ftpClient.doCommand("opts", "utf8 off");
        FTPFile ftpFiles[] = ftpClient.listFiles(//path);
                new String(path.getBytes(ReportConst.ECODING_METHOD_UTF), "iso-8859-1"));
        if (null == ftpFiles || 0 == ftpFiles.length) {
            LOG.debug("the directory has not any file,the directory path is:" + path);
            return;
        }
        for (FTPFile ftpFile : ftpFiles) {
            ReportVO node = new ReportVO();
            node.setReport_name(ftpFile.getName());//new String(ftpFile.getName().getBytes("UTF-8"),"GBK"));
            node.setReport_path(path + "/" + ftpFile.getName());
            node.setParent_path(path);//父路径
            node.setVersion(0);
            node.setId(UUIDUtils.getRandomUUID());
            node.setReport_type(ReportConst.RUNQIAN);
            //node.setData_source(data_source);
            if (ftpFile.isFile()) {
                String fileName = ftpFile.getName();
                if (fileName.endsWith(ReportConst.SUFFIX) && !fileName.endsWith(ReportConst.ARG_SUFFIX)) {//去除参数文件
                    node.setLeaf("Y");
                    node.setParam_type(ReportConst.PARAM_TEMPLATE);
                    reportVO.add(node);
                }
            } else {
                String newPath = node.getReport_path();
                node.setLeaf("N");
                reportVO.add(node);
                getAllFromRemoteDirectory(newPath, reportVO);//递归调用
            }
        }
    }

    @Override
    public String forwardToPage(Model model, ReportVO reportVO, List<String> param,
            List<String> kpi) {
        BIReportDisplayMetaVO reportMetaVO = new BIReportDisplayMetaVO();
        displayPageProcesser(reportVO, reportMetaVO); //处理展现层
        kpiInfoProcesser(model, kpi);//处理指标指引
        powerDataDicProcesser(model);//处理数据字典授权数据，作为参数提交到报表服务器
        model.addAttribute("reportMetaVO", reportMetaVO);
        return "/portlet/runqian/runqian-fun";
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
        String reportPath = reportVO.getReport_path();
        String name = "查询条件";
        String comments = reportVO.getComments();
        if (comments != null) {//添加换行符处理
            comments = comments.replace("\r\n", "<br>");
        }
        if (menuType == 4) {
            name = reportVO.getReport_name().replace(".raq", "");//设置为报表名称
        }
        reportMetaVO.setComments(comments);
        reportMetaVO.setName(name);
        reportMetaVO.setMenuType(menuType);
        if (StringUtils.isNotEmpty(reportPath)) {
            reportMetaVO.setQueryUrl(url + ReportConst.RUNQIAN_QUERY_SUFFIX + reportPath);//设置查询路径
            reportMetaVO.setExportUrl(url + ReportConst.RUNQIAN_EXPORT_SUFFIX);//设置导出路径
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

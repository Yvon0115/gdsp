package com.gdsp.platform.log.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantUtils;
import com.gdsp.platform.log.helper.PortalQueryConst;
import com.gdsp.platform.log.model.DetailOpLogVO;
import com.gdsp.platform.log.model.OperationLogVO;
import com.gdsp.platform.log.model.ResAccessLogVO;
import com.gdsp.platform.log.service.IOperationLogService;
import com.gdsp.platform.log.utils.CsvUtils;
import com.gdsp.platform.log.utils.LogXmlAnalyzeUtils;

//修改人：wangxiaolong
//修改时间：2017-4-7
//修改原因：只有在初始化页面时，默认查询近一个月记录，type生效，其他操作，type失效，均已p_start_date，p_end_date为准
@Controller
@RequestMapping("systools/operationlog")
public class OperationLogController {
	
	@Autowired
    private IOperationLogService operationLogService;
	@Autowired
    private IUserQueryPubService userQueryPubService;

    /**
     * 加载用户操作日志
     * @param model
     * @return 加载页面
     */
    @RequestMapping("/list.d")
    public String list(Model model, Condition condition, Pageable page) {
    	//加载一个月的信息
//    	condition.addExpression("r.createTime",  new DDate(DateUtils.addMonth(new Date(), -1)).getMillis() / 1000, ">=");
//    	List<OperationLogVO> operationLogPage = operationLogService.operationLogList(condition);
//    	List<UserVO>  alluserList = userQueryPubService.findAllUsersList();
//    	for (OperationLogVO operationLogVO : operationLogPage) {
//			for (UserVO userVO : alluserList) {
//				if(operationLogVO.getCreateBy().equals(userVO.getId())){
//					operationLogVO.setUsername(userVO.getUsername());
//				}
//			}
//		}
//    	if(operationLogPage != null && operationLogPage.size()>0){
//	       	Page<OperationLogVO> operationLogPages = GrantUtils.convertListToPage(operationLogPage, page);
//	       	model.addAttribute("OperationLogPages", operationLogPages);
//    	}else{
//    		model.addAttribute("OperationLogPages", new PageImpl<OperationLogVO>(new ArrayList<OperationLogVO>(), page,0));
//    	}
    	//操作表读取xml文件而出
    	List<String> opTableNames = LogXmlAnalyzeUtils.getTableNameList();
    	List<String> opTableDescs = new ArrayList<String>();
    	for (String tableName : opTableNames) {
			String tableDesc = LogXmlAnalyzeUtils.getTableDesc(tableName);
			opTableDescs.add(tableDesc);
		}
    	model.addAttribute("opTables", opTableDescs);
        return "systools/operationlog/list";
    }
    /**
     * 条件查询用户操作日志，局部刷新
     * @param model
     * @param condition
     * @param page
     * @param uname
     * @param p_start_date
     * @param p_end_date
     * @param queryscope
     * @param optype
     * @param tname
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model,Condition condition, Pageable page, String uname, String p_start_date, String p_end_date, String queryscope,String optype,String opTable){
    /*	//自由
        if (PortalQueryConst.freeString.equals(queryscope) || StringUtils.isEmpty(queryscope)) {
            if (StringUtils.isNotEmpty(p_start_date)) {
                condition.addExpression("r.createTime", new DDate(p_start_date).getMillis() / 1000, ">=");
            }
            if (StringUtils.isNotEmpty(p_end_date)) {
                condition.addExpression("r.createTime", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
            }
        }else if (PortalQueryConst.todayString.equals(queryscope)) {
            //时间范围查询
            //今天
            condition.addExpression("r.createTime", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, ">=");
            //防止有脏数据
            condition.addExpression("r.createTime", new DDate(DateUtils.getDate(new Date())).getDateAfter(1).getMillis() / 1000, "<");
        }else if (PortalQueryConst.weekString.equals(queryscope)) {
        	//近7天
            condition.addExpression("r.createTime", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -6)).getMillis() / 1000, ">=");
        }else if (PortalQueryConst.monthString.equals(queryscope)) {
        	//近一个月
            condition.addExpression("r.createTime", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -29)).getMillis() / 1000, ">=");
        }else if (PortalQueryConst.threeMonthString.equals(queryscope)) {
        	//近三个月
            condition.addExpression("r.createTime", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -89)).getMillis() / 1000, ">=");
        }*/
        if (StringUtils.isNotEmpty(p_start_date)) {
            condition.addExpression("r.createTime", new DDate(p_start_date).getMillis() / 1000, ">=");
        }
        if (StringUtils.isNotEmpty(p_end_date)) {
            condition.addExpression("r.createTime", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
        }
        
        //操作类型查询
        if(StringUtils.isNotEmpty(optype)){
        	condition.addExpression("r.type", optype, "=");
        }
        //操作表名查询
        if (StringUtils.isNotEmpty(opTable)) {
            condition.addExpression("r.table_desc", opTable, "=");
        }
    	List<OperationLogVO> operationLogPage = operationLogService.operationLogList(condition);
    	List<UserVO>  alluserList = userQueryPubService.findAllUsersList();
    	for (OperationLogVO operationLogVO : operationLogPage) {
			for (UserVO userVO : alluserList) {
				if(operationLogVO.getCreateBy().equals(userVO.getId())){
					operationLogVO.setUsername(userVO.getUsername());
				}
			}
		}
    	List<OperationLogVO>  opLogList = new ArrayList<OperationLogVO>();
    	
    	if(StringUtils.isEmpty(uname)){
    		opLogList = operationLogPage;
    	} else {
    		for (OperationLogVO operationLogVO : operationLogPage) {
    			String name = operationLogVO.getUsername();
    			if(name!=null){
				if(name.contains(uname)){
					opLogList.add(operationLogVO);
					
				}
    			}
			}
    	}
    	if(opLogList != null && opLogList.size()>0){
	       	Page<OperationLogVO> operationLogPages = GrantUtils.convertListToPage(opLogList, page);
	       	model.addAttribute("OperationLogPages", operationLogPages);
    	}
    	return "systools/operationlog/list-data";
    }
    /**
     * 操作日志详情，具体修改字段
     * @param model
     * @param id
     * @param page
     * @return
     */
    @RequestMapping("/toDetailInfo.d")
    @ViewWrapper(wrapped = false)
    public String showDetail(Model model, String id) {
    	//因为在弹出框中不需要分页显示，所以获取list而不加page包装
    	List<DetailOpLogVO> detailOpLogList = operationLogService.queryDetailByCondition(id);
    	//先把表的信息放入model
    	OperationLogVO operationLogVO = operationLogService.load(id);
    	UserVO userVO = userQueryPubService.load(operationLogVO.getCreateBy());
    	operationLogVO.setCreateBy(userVO.getUsername());
    	model.addAttribute("operationLogVO", operationLogVO);
    	//把字段信息变化信息放入model
        model.addAttribute("DetailOpLogPages", detailOpLogList);
        return "systools/operationlog/detail-list";
    }
    @RequestMapping("/toDetailDataInfo.d")
    @ViewWrapper(wrapped = false)
    public String toDetailDataInfo(Model model, String id) {
    	List<DetailOpLogVO> detailOpLogList = operationLogService.queryDetailByCondition(id);
        model.addAttribute("DetailOpLogPages", detailOpLogList);
        return "systools/operationlog/detail-list-data";
    }
    
    /**
     * 导出操作日志
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportOpLogModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportOpLogModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String userName,String scopeValue,
    		String opType,String tname,String startTime,String endTime) throws IOException{
    	List<String> headList = new ArrayList<String>();
    	//设置表头信息
    	String busiHead = "类型,表,表名,操作人,时间,字段,名称,旧值,新值";
    	headList.add(busiHead);
    	//自由
        /*if (PortalQueryConst.freeString.equals(scopeValue) || StringUtils.isEmpty(scopeValue)) {
            if (StringUtils.isNotEmpty(startTime)) {
                condition.addExpression("r.createTime", new DDate(startTime).getMillis() / 1000, ">=");
            }
            if (StringUtils.isNotEmpty(endTime)) {
                condition.addExpression("r.createTime", new DDate(endTime).getDateAfter(1).getMillis() / 1000, "<=");
            }
        }else if (PortalQueryConst.todayString.equals(scopeValue)) {
            //时间范围查询
            //今天
            condition.addExpression("r.createTime", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, ">=");
        }else if (PortalQueryConst.weekString.equals(scopeValue)) {
        	//近7天
            condition.addExpression("r.createTime", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -6)).getMillis() / 1000, ">=");
        }else if (PortalQueryConst.monthString.equals(scopeValue)) {
        	//近一个月
            condition.addExpression("r.createTime", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -29)).getMillis() / 1000, ">=");
        }else if (PortalQueryConst.threeMonthString.equals(scopeValue)) {
        	//近三个月
            condition.addExpression("r.createTime", new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -89)).getMillis() / 1000, ">=");
        }*/
    	 if (StringUtils.isNotEmpty(startTime)) {
             condition.addExpression("r.createTime", new DDate(startTime).getMillis() / 1000, ">=");
         }
         if (StringUtils.isNotEmpty(endTime)) {
             condition.addExpression("r.createTime", new DDate(endTime).getDateAfter(1).getMillis() / 1000, "<=");
         }
        //操作类型查询
        if(StringUtils.isNotEmpty(opType)){
        	condition.addExpression("r.type", opType, "=");
        }
        //操作表名查询
        if (StringUtils.isNotEmpty(tname)) {
            condition.addExpression("r.table_desc", tname, "=");
        }
        //获得查询后的数据list
        List<OperationLogVO> operationLogPage = operationLogService.operationLogList(condition);
    	List<UserVO>  alluserList = userQueryPubService.findAllUsersList();
    	for (OperationLogVO operationLogVO : operationLogPage) {
			for (UserVO userVO : alluserList) {
				if(operationLogVO.getCreateBy().equals(userVO.getId())){
					operationLogVO.setUsername(userVO.getUsername());
				}
			}
		}
    	List<OperationLogVO>  opLogList = new ArrayList<OperationLogVO>();
    	if(StringUtils.isEmpty(userName)){
    		opLogList = operationLogPage;
    	} else {
    		for (OperationLogVO operationLogVO : operationLogPage) {
				if(operationLogVO.getUsername().contains(userName)){
					opLogList.add(operationLogVO);
				}
			}
    	}
    	List<String> downList = new ArrayList<String>();
    	downList = operationLogService.getContentList(opLogList);
        response.reset();  
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        String downloadFileName =new String("用户操作日志".getBytes("gb2312"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ downloadFileName + ".csv\"");
        ServletOutputStream sos = response.getOutputStream();
        //导出csv文件信息
        CsvUtils.exportCsv(sos ,headList,downList);
    }
    
}


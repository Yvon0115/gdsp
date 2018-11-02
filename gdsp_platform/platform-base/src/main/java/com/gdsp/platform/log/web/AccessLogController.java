package com.gdsp.platform.log.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.log.helper.PortalQueryConst;
import com.gdsp.platform.log.model.ResAccessLogVO;
import com.gdsp.platform.log.service.IResAccessLogService;
import com.gdsp.platform.log.utils.CsvUtils;

/**
 * 
 * @ClassName: AccessLogController
 * (访问记录管理控制类)
 * @author songxiang
 * @date 2015年10月28日 上午11:36:52
 *
 */

// 修改人：wangxiaolong
// 修改时间：2017-3-20
// 修改原因：需要对查询日志的展示进行修改，添加了新的展示字段，重写了sql,对表起了别名a

//修改时间：2017-4-7
//修改原因：只有在初始化页面时，默认查询近一个月记录，type生效，其他操作，type失效，均已p_start_date，p_end_date为准
@Controller
@RequestMapping("systools/log")
public class AccessLogController {

	@Autowired
	private IResAccessLogService resAccessLogService;

	/**
	 *  报表访问Top10 
	 */
	@RequestMapping("/res_Access_top10.d")
	public String res_Access_top10(Model model) {
		
		Sorter sort = new Sorter(Direction.DESC, "a.createTime");
		model.addAttribute("top10Vos", resAccessLogService.res_Access_top(null, 10, sort));
		return "systools/busilog/simple-list";
	}

	/**
	 * 用户最近访问top10
	 * @param model
	 * @author wqh
	 * @since 2017年6月23日
	 */
	@RequestMapping("/recentVisitTop10.d")
	public String recentVisitTop10(Model model) {
		String userID = AppContext.getContext().getContextUserId();
		model.addAttribute("visitTop10", resAccessLogService.findRecentVisitRecords(userID, 10));
		return "systools/busilog/visitrecords-list";
	}

	
	
	/**
	 * @Title: list (页面加载) @param model @param
	 * condition @param pageable @return 参数说明 	 */
	@RequestMapping("/list.d")
	public String list(Model model, Condition condition, Pageable pageable) {
		// 一开始默认加载的是一个月的访问记录
//		condition.addExpression("a.createTime", new DDate(DateUtils.addMonth(new Date(), -1)).getMillis() / 1000, ">=");
//		Sorter sort = new Sorter(Direction.DESC, "a.createTime");
//		Page<ResAccessLogVO> ResAccessPages = resAccessLogService.findPageByCondition(condition, pageable, sort);
//		model.addAttribute("ResAccessPages", ResAccessPages);
		return "/systools/busilog/list";
	}

	/**
	 * 
	 * @Title: listData (局部刷新页面) @param model @param
	 * condition @param pageable @param p_start_date @param p_end_date @return
	 * 参数说明 	 */
	@RequestMapping("/listData.d")
	@ViewWrapper(wrapped = false)
	public String listData(Model model, Condition condition, Pageable pageable, String type, String p_start_date,
			String p_end_date, String username) {
		Sorter sort = new Sorter(Direction.DESC, "a.createTime");
		if (StringUtils.isNotEmpty(username)) {
			condition.addExpression("a.username", username, "like");
		}
		// 自由
		/*
		 * if (PortalQueryConst.freeString.equals(type) ||
		 * StringUtils.isEmpty(type)) { if (p_start_date != null) {
		 * condition.addExpression("a.createTime", new
		 * DDate(p_start_date).getMillis() / 1000, ">="); } if (p_end_date !=
		 * null) { condition.addExpression("a.createTime", new
		 * DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<="); } } //今天
		 * else if (PortalQueryConst.todayString.equals(type)) {
		 * condition.addExpression("a.createTime", new
		 * DDate(DateUtils.getDate(new Date())).getMillis() / 1000, ">="); }
		 * //昨天 else if (PortalQueryConst.yestodayString.equals(type)) {
		 * condition.addExpression("a.createTime", new
		 * DDate(DateUtils.addDay(DateUtils.getDate(new Date()),
		 * -1)).getMillis() / 1000, ">=");
		 * condition.addExpression("a.createTime", new
		 * DDate(DateUtils.getDate(new Date())).getMillis() / 1000, "<"); }
		 * //近7天 else if (PortalQueryConst.weekString.equals(type)) {
		 * condition.addExpression("a.createTime", new
		 * DDate(DateUtils.addDay(DateUtils.getDate(new Date()),
		 * -6)).getMillis() / 1000, ">="); } //近30天 else if
		 * (PortalQueryConst.monthString.equals(type)) {
		 * condition.addExpression("a.createTime", new
		 * DDate(DateUtils.addDay(DateUtils.getDate(new Date()),
		 * -29)).getMillis() / 1000, ">="); }
		 */

		if (StringUtils.isNotEmpty(p_start_date)) {
			condition.addExpression("a.createTime", new DDate(p_start_date).getMillis() / 1000, ">=");
		}
		if (StringUtils.isNotEmpty(p_end_date)) {
			condition.addExpression("a.createTime", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
		}

		Page<ResAccessLogVO> ResAccessPages = resAccessLogService.findPageByCondition(condition, pageable, sort);
		model.addAttribute("ResAccessPages", ResAccessPages);
		return "/systools/busilog/list-data";
	}

	/**
	 * 
	 * @Title: delete (删除记录) @param id @return 参数说明 	 */
	@RequestMapping("/delete.d")
	@ResponseBody
	public Object delete(String... id) {
		if (id != null && id.length > 0) {
			resAccessLogService.deletes(id);
		}
		return AjaxResult.SUCCESS;
	}

	/**
	 * 
	 * @Title: query (根据多个条件查询) @param model @param
	 * condition @param pageable @param p_start_date @param p_end_date @return
	 * 参数说明 	 */
	@RequestMapping("/query.d")
	@ViewWrapper(wrapped = false)
	@ResponseBody
	public Object query(Model model, Condition condition, Pageable pageable, String p_start_date, String p_end_date) {
		Sorter sort = new Sorter(Direction.DESC, "a.createTime");
		condition.addExpression("a.createTime", p_start_date, "<=");
		condition.addExpression("a.createTime", p_end_date, ">=");
		Page<ResAccessLogVO> ResAccessPages = resAccessLogService.findPageByCondition(condition, pageable, sort);
		model.addAttribute("ResAccessPages", ResAccessPages);
		return AjaxResult.SUCCESS;
	}

	/**
	 * 导出访问日志
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/doExportBusiLogModel.d")
	@ViewWrapper(wrapped = false)
	@ResponseBody
	public void doExportBusiLogModel(HttpServletResponse response, HttpServletRequest request, Condition condition,
			String scopeValue, String startTime, String endTime) throws IOException {
		List<String> headList = new ArrayList<String>();
		// 设置表头信息
		String busiHead = "编号,名称,类型,访问用户,访问时间";
		headList.add(busiHead);
		Sorter sort = new Sorter(Direction.DESC, "a.createTime");
		if (StringUtils.isNotEmpty(startTime)) {
			condition.addExpression("a.createTime", new DDate(startTime).getMillis() / 1000, ">=");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			condition.addExpression("a.createTime", new DDate(endTime).getDateAfter(1).getMillis() / 1000, "<=");
		}
		/*// 自由
		if (PortalQueryConst.freeString.equals(scopeValue) || StringUtils.isEmpty(scopeValue)) {
			if (StringUtils.isNotEmpty(startTime)) {
				condition.addExpression("a.createTime", new DDate(startTime).getMillis() / 1000, ">=");
			}
			if (StringUtils.isNotEmpty(endTime)) {
				condition.addExpression("a.createTime", new DDate(endTime).getDateAfter(1).getMillis() / 1000, "<=");
			}
		}
		// 今天
		else if (PortalQueryConst.todayString.equals(scopeValue)) {
			condition.addExpression("a.createTime", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, ">=");
		}
		// 昨天
		else if (PortalQueryConst.yestodayString.equals(scopeValue)) {
			condition.addExpression("a.createTime",
					new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -1)).getMillis() / 1000, ">=");
			condition.addExpression("a.createTime", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, "<");
		}
		// 近7天
		else if (PortalQueryConst.weekString.equals(scopeValue)) {
			condition.addExpression("a.createTime",
					new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -6)).getMillis() / 1000, ">=");
		}
		// 近30天
		else if (PortalQueryConst.monthString.equals(scopeValue)) {
			condition.addExpression("a.createTime",
					new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -29)).getMillis() / 1000, ">=");
		}*/
		// 获得查询后的数据list
		List<ResAccessLogVO> resAccessList = resAccessLogService.findListByCondition(condition, sort);
		// 设置内容
		List<String> downList = new ArrayList<String>();
		downList = resAccessLogService.getContentList(resAccessList);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream");
		String downloadFileName = new String("用户访问日志".getBytes("gb2312"), "iso-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + ".csv\"");
		ServletOutputStream sos = response.getOutputStream();
		// 导出csv文件信息
		CsvUtils.exportCsv(sos, headList, downList);
	}
}

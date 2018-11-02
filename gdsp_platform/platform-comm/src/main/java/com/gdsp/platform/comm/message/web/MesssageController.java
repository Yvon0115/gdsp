package com.gdsp.platform.comm.message.web;

import java.util.Date;

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
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.comm.message.model.MessageVO;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.log.helper.PortalQueryConst;

/**
 * 个人消息
 * @author wwb
 * @date 2015年10月30日 下午4:15:46
 */
@Controller
@RequestMapping("tools/message")
public class MesssageController {

    @Autowired
    private IMessageService messageService;

    /** 打开消息列表对话框 */
    @RequestMapping("/messageDlg.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String messageDlg(Model model, Condition condition, String isALL) {
        String loginUserID = AppContext.getContext().getContextUserId();
        Sorter sort = new Sorter(Direction.DESC, "transtime");
        if (StringUtils.isEmpty(isALL) || "N".equals(isALL)) {
            condition.addExpression("iflook", "N");
        }
        condition.addExpression("touserid", loginUserID);
        Pageable pageable = new PageSerRequest(0, 10);
        Page<MessageVO> messagePages = messageService.findMessageByCondition(condition, pageable, sort);
        model.addAttribute("isALL", isALL);
        model.addAttribute("messagePages", messagePages);
        return "message/message-dlg";
    }
    
	/** 数据加载，条件查询 */
	@RequestMapping("/listData.d")
	@ViewWrapper(wrapped = false, onlyAjax = true)
	public String listData(Model model, Condition con ,String type, String subject, 
	        String content, String fromuseraccount, Pageable pageable, String isALL) {
		String loginUserID = AppContext.getContext().getContextUserId();
		Sorter sort = new Sorter(Direction.DESC, "transtime");
		if (PortalQueryConst.freeString.equals(type) || StringUtils.isEmpty(type)) {
		} else if (PortalQueryConst.todayString.equals(type)) { // 今天
			con.addExpression("TRANSTIME", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, ">=");
		} else if (PortalQueryConst.yestodayString.equals(type)) { // 昨天
			con.addExpression("TRANSTIME",
					new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -1)).getMillis() / 1000, ">=");
			con.addExpression("TRANSTIME", new DDate(DateUtils.getDate(new Date())).getMillis() / 1000, "<");
		} else if (PortalQueryConst.weekString.equals(type)) { // 近一周
			con.addExpression("TRANSTIME",
					new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -6)).getMillis() / 1000, ">=");
		} else if (PortalQueryConst.monthString.equals(type)) { // 近一月
			con.addExpression("TRANSTIME",
					new DDate(DateUtils.addDay(DateUtils.getDate(new Date()), -29)).getMillis() / 1000, ">=");
		}
		if (StringUtils.isNotEmpty(subject)) {
			con.addExpression("SUBJECT", subject, "like");
		}
		if (StringUtils.isNotEmpty(content)) {
			con.addExpression("CONTENT", content, "like");
		}
		if (StringUtils.isNotEmpty(fromuseraccount)) {
			con.addExpression("FROMUSERACCOUNT", fromuseraccount, "like");
		}
		con.addExpression("touserid", loginUserID);
		if (StringUtils.isEmpty(isALL) || "N".equals(isALL)) {
			con.addExpression("iflook", "N");
		}
		model.addAttribute("isALL", isALL);
		Page<MessageVO> messagePages = messageService.findMessageByCondition(con, pageable, sort);
		model.addAttribute("messagePages", messagePages);
		return "message/message-list-data";
	}

	/** 查看消息 */
    @RequestMapping("/lookMessage.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String lookMessage(Model model, String id) {
        MessageVO messageVO = messageService.lookInf(id);
        model.addAttribute("messageVO", messageVO);
        return "message/messageForm";
    }
    
    
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            messageService.deleteMessage(id);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/signlook.d")
    @ResponseBody
    public Object signlook(String... id) {
        if (id != null && id.length > 0) {
            messageService.signlook(id);
        }
        return AjaxResult.SUCCESS;
    }

    
    @RequestMapping("/lookMessageInf.d")
    @ResponseBody
    public Object lookMessageInf(MessageVO messageVO) {
        return AjaxResult.SUCCESS;
    }
    
    /** 简版消息主页面 */
    @RequestMapping("/simpleMessageDlg.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String simplemessageDlg(Model model, Condition condition, String isALL) {
        String loginUserID = AppContext.getContext().getContextUserId();
        Sorter sort = new Sorter(Direction.DESC, "transtime");
        if (StringUtils.isEmpty(isALL) || "N".equals(isALL)) {
            condition.addExpression("iflook", "N");
        }
        condition.addExpression("touserid", loginUserID);
        Pageable pageable = new PageSerRequest(0, 10);
        Page<MessageVO> messagePages = messageService.findMessageByCondition(condition, pageable, sort);
        model.addAttribute("isALL", isALL);
        model.addAttribute("messagePages", messagePages);
        return "message/message-simple-list";
    }
    
    /**
     * 简版消息详情
     * @param model
     * @param id
     */
    @RequestMapping("/lookSimpleMessage.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String lookSimpleMessage(Model model, String id) {
        MessageVO messageVO = messageService.lookInf(id);
        model.addAttribute("messageVO", messageVO);
        return "message/message-simple-detail";
    }
}
/*
 * if (p_start_date != null) { con.addExpression("publish_date", new
 * DDate(p_start_date).getMillis() / 1000, ">="); } if (p_end_date
 * != null) { con.addExpression("publish_date", new
 * DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<="); }
 */
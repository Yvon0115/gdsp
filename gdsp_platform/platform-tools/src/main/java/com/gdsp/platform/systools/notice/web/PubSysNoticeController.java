package com.gdsp.platform.systools.notice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.systools.notice.model.NoticeHistoryVO;
import com.gdsp.platform.systools.notice.service.INoticeHistoryService;
import com.gdsp.platform.systools.notice.service.ISysNoticeService;

/**
 * 公告查看处理器
 * @author lt
 * @since 2018年2月8日 
 */
@Controller
@RequestMapping("public/notice")
public class PubSysNoticeController {

    @Autowired
    private ISysNoticeService     noticeService;
    @Autowired
    private INoticeHistoryService historyService;
    
    @RequestMapping("/noticeDlgDetail.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String noticeDlgDetail(Model model, String id) {
    	String loginUserID = AppContext.getContext().getContextUserId();
    	if (historyService.queryNoticeHistory(id, loginUserID) == 0) {
    		NoticeHistoryVO notHistoryVO = new NoticeHistoryVO();
    		notHistoryVO.setNotice_id(id);
    		notHistoryVO.setAccess_date(System.currentTimeMillis() / 1000 + "");
    		notHistoryVO.setUser_id(loginUserID);
    		historyService.insert(notHistoryVO);
    	}
    	model.addAttribute("sysNoticeVo", noticeService.loadById(id));
    	return "sysnotice/notice-dlg-detail";
    }
    
    @RequestMapping("/queryNoticeHistoryCount.d")
    @ResponseBody
    public int queryNoticeHistoryCount(String id){
        String loginUserID = AppContext.getContext().getContextUserId();
        int result = 0;
        result = historyService.queryNoticeHistory(id, loginUserID);
        return result;
    }
    
    /**
     * 首页简版公告详情
     * @param model
     * @param id
     * @return
     */ 
    @RequestMapping("/showSimpleNoticeDetail.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String showSysNoticeDetail(Model model, String id) {
    	  //读取过的公告存入历史记录表
    	  String loginUserID = AppContext.getContext().getContextUserId();
          if (historyService.queryNoticeHistory(id, loginUserID) == 0) {
              NoticeHistoryVO notHistoryVO = new NoticeHistoryVO();
              notHistoryVO.setNotice_id(id);
              notHistoryVO.setAccess_date(System.currentTimeMillis() / 1000 + "");
              notHistoryVO.setUser_id(loginUserID);
              historyService.insert(notHistoryVO);
          }
        model.addAttribute("sysNoticeVo", noticeService.loadById(id));
        return "sysnotice/simple-detail";
    }
    
    @RequestMapping("/noticeDlg.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String noticeDlg(Model model, Condition condition, Pageable pageable, Sorter sort) {
        String loginUserID = AppContext.getContext().getContextUserId();
        //model.addAttribute("notices", querySimpleNoticeVosPageByCondition(condition, pageable,loginUserID));
//        condition.addExpression("title", title, "like");
        model.addAttribute("notices", noticeService.querySimpleNoticeVoDlgPage(condition, pageable, loginUserID));
        return "sysnotice/notice-dlg";
    }

    @RequestMapping("/noticeDlg-list.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String noticeDlgList(Model model, Condition condition, Pageable pageable, String type, String title, String p_start_date, String p_end_date) {
        String loginUserID = AppContext.getContext().getContextUserId();
        if (p_start_date != null) {
            condition.addExpression("publish_date", new DDate(p_start_date).getMillis() / 1000, ">=");
        }
        if (p_end_date != null) {
            condition.addExpression("publish_date", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
        }
        if(title != null){
        	condition.addExpression("title", title, "like");
        }
        model.addAttribute("notices", noticeService.querySimpleNoticeVoDlgPage(condition, pageable, loginUserID));
        return "sysnotice/notice-dlg-list";
    }
}

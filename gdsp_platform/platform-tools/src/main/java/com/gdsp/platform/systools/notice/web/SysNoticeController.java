package com.gdsp.platform.systools.notice.web;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
//修改人：wangxiaolong
//修改时间：2017-4-10
//修改原因：只有在初始化页面时，默认查询近一个月记录，type生效，其他操作，type失效，均已startDate，endDate为准
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.systools.notice.model.NoticeHistoryVO;
import com.gdsp.platform.systools.notice.model.SysNoticeVO;
import com.gdsp.platform.systools.notice.service.INoticeHistoryService;
import com.gdsp.platform.systools.notice.service.ISysNoticeService;

/**
 * Created by zhouyu on 2015/7/1.
 */
@Controller
@RequestMapping("sysnotice")
public class SysNoticeController {

    @Autowired
    private ISysNoticeService     noticeService;
    @Autowired
    private INoticeHistoryService historyService;

    @RequestMapping("/list.d")
    public String list(Model model, Condition condition , Pageable pageable){
    	//加载近一月的系统告知
    	condition.addExpression("lastModifyTime", new DDate(DateUtils.addMonth(new Date(), -1)).getMillis()/1000, ">=");
        Page<SysNoticeVO> page = noticeService.queryNoticeVosPageByCondition(condition, pageable);
        model.addAttribute("noticeVoPages", page);
        return "sysnotice/list";
    }

    @RequestMapping("/simpleList.d")
    public String simpleList(Model model, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        Condition condition = new Condition();
        Page<SysNoticeVO> page = noticeService.querySimpleNoticeVoDlgPage(condition, pageable,loginUserID);
        model.addAttribute("noticeVoPages", page);
        return "sysnotice/simple-list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, Pageable pageable, String type,String title, String p_start_date, String p_end_date) {
    	 if (p_start_date != null) {
             condition.addExpression("lastModifyTime", new DDate(p_start_date).getMillis() / 1000, ">=");
         }
         if (p_end_date != null) {
             condition.addExpression("lastModifyTime", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
         }
        Page<SysNoticeVO> page = noticeService.queryNoticeVosPageByCondition(condition, pageable);
        model.addAttribute("noticeVoPages", page);
        return "sysnotice/list-data";
    }

    @RequestMapping("/simpleListData.d")
    @ViewWrapper(wrapped = false)
    public String simpleListData(Model model, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        Condition condition = new Condition();
        Page<SysNoticeVO> page=noticeService.querySimpleNoticeVoDlgPage(condition, pageable,loginUserID);
        model.addAttribute("noticeVoPages", page);
        return "sysnotice/simple-list-data";
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model) {
        return "sysnotice/form";
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("sysNoticeVo", noticeService.loadById(id));
        return "sysnotice/form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(SysNoticeVO sysNoticeVo) {
        if (StringUtils.isNotEmpty(sysNoticeVo.getId())) {
            noticeService.update(sysNoticeVo);
        } else {
        	//公告创建时将最终修改时间也赋初始值
        	sysNoticeVo.setLastModifyTime(new DDateTime());
            noticeService.insert(sysNoticeVo);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            List<SysNoticeVO> list = noticeService.loadByIds(id);
            Iterator<SysNoticeVO> its = list.iterator();
            while (its.hasNext()) {
                SysNoticeVO vo = its.next();
                if (vo.getValid_flag() != null && "Y".equals(vo.getValid_flag().toString())) {
                    return new AjaxResult(AjaxResult.STATUS_ERROR, "已发布的公告不允许删除。");
                }
            }
        }
        noticeService.deleteSysNoticeBatch(id);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/publish.d")
    @ResponseBody
    public Object publish(String id, String valid_flag) {
        SysNoticeVO sysNoticeVO = noticeService.loadById(id);
        sysNoticeVO.setValid_flag(new DBoolean(valid_flag));
        if (sysNoticeVO.getPublish_date() != null) {
            sysNoticeVO.setPublish_date(null);
        }
        noticeService.update(sysNoticeVO);
        return AjaxResult.SUCCESS;
    }

//  该方法为查看系统公告功能应对所有用户公开，已挪至PubSysNoticeController
//  修改人：leiting
//  修改时间：2018/2/8
    /**
     * 首页简版公告详情
     * @param model
     * @param id
     * @return
     *//* 
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

    @RequestMapping("/queryNoticeHistoryCount.d")
    @ResponseBody
    public int queryNoticeHistoryCount(String id){
        String loginUserID = AppContext.getContext().getContextUserId();
        int result = 0;
        result = historyService.queryNoticeHistory(id, loginUserID);
        return result;
    }

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
    }*/
    //    @RequestMapping("/range.d")
    //    @ViewWrapper(wrapped = false)
    //    public String range(Model model,String id, Condition condition, Pageable pageable,Sorter sort)
    //    {
    //    	String loginUserID = AppContext.getContext().getContextUserId();
    //    	model.addAttribute("pageUsers", rangeService.queryUsersByAddCond(id, loginUserID, condition, pageable));
    ////    	model.addAttribute("orgVOs", rangeService.queryOrgByConditions(condition,pageable,id, loginUserID, sort));
    ////    	model.addAttribute("groups", rangeService.queryUserGroupByConditions(condition, pageable, id, loginUserID, sort));
    //    	model.addAttribute("noticeID", id);
    //         return "sysnotice/range";
    //    }
    //    
    //    /**
    //   	 * 
    //   	 * @Title: reloadUser
    //  	 * (加载用户列表)
    //   	 * @param model
    //   	 * @param dirId
    //   	 * @param condition
    //   	 * @param pageable
    //   	 * @return 参数说明
    //   	 * @return String 返回值说明
    //   	 *     //   	 */
    //   	@RequestMapping("/reloadUser.d")
    //   	@ViewWrapper(wrapped = false)
    //   	public String reloadUser(Model model, String noticeID, Condition condition, Pageable pageable) {
    //   		String loginUserID = AppContext.getContext().getContextUserId();
    //   		model.addAttribute("noticeID", noticeID);
    //   		model.addAttribute("pageUsers", rangeService.queryUsersByAddCond(noticeID, loginUserID, condition, pageable));
    //   		return "sysnotice/userlist-data";
    //   	}
    //  
    //   	/**
    //   	 * 
    //   	 * @Title: reloadUserList
    //   	 * (展示用户列表)
    //   	 * @param model
    //  	 * @param dirId
    //  	 * @param pageable
    //   	 * @return 参数说明
    //   	 * @return String 返回值说明
    //   	 *     //   	 */
    //   	@RequestMapping("/reloadUserList.d")
    //   	@ViewWrapper(wrapped = false)
    //   	public String reloadUserList(Model model, String noticeID, Pageable pageable,boolean containSelf,String addCond) {
    //   		String loginUserID = AppContext.getContext().getContextUserId();
    //   		model.addAttribute("noticeID", noticeID);
    //   		model.addAttribute("users", userService.queryUserPageByUserAndCond(loginUserID, pageable, containSelf, addCond));
    //   		return "sysnotice/user-data";
    //   	}
    //   	
    //   	
    //   	
    //    @RequestMapping("/addUser.d")
    //	@ViewWrapper(wrapped = false, onlyAjax = true)
    //	public String addUser(Model model, String noticeID, Pageable pageable,boolean containSelf,String addCond) {
    //		String loginUserID = AppContext.getContext().getContextUserId();
    //		model.addAttribute("noticeID", noticeID);
    //		//addCond+="u.id not in (select range_id from rms_notice_range where notice_id ='" + noticeID + "') ";
    //		model.addAttribute("users", userService.queryUserPageByUserAndCond(loginUserID, pageable, containSelf, addCond));
    //		return "sysnotice/user-add";
    //	}
    // 
    //	@RequestMapping("/saveUsers.d")
    //	@ResponseBody
    //	public Object saveUsers(String noticeID,NoticeRangeVO vo, String... id) {
    //		rangeService.insertNoticeRange(noticeID, 0,id);
    //		return AjaxResult.SUCCESS;
    //	}
    //	
    // 
    //	 @RequestMapping("/reloadOrg.d")
    //	 @ViewWrapper(wrapped = false)
    //		public String orglistDada(Model model, Condition condition, Pageable pageable,Sorter sort,String noticeID) {
    //			String loginUserID = AppContext.getContext().getContextUserId();
    //			model.addAttribute("noticeID", noticeID);
    //	//		model.addAttribute("orgVOs",  rangeService.queryOrgByConditions(condition,pageable,noticeID, loginUserID, sort));
    //			return "sysnotice/orglist-data";
    //		}
    //	
    //	 
    //	 	@RequestMapping("/reloadOrgList.d")
    //	   	@ViewWrapper(wrapped = false)
    //	   	public String reloadOrgList(Model model, String noticeID, Pageable pageable) {
    //	   	//	String loginUserID = AppContext.getContext().getContextUserId();
    //	   		model.addAttribute("noticeID", noticeID);
    //	   		String loginUserID = AppContext.getContext().getContextUserId();
    //			//List<OrgVO> orgVOs = rangeService.queryOrgListByUser(loginUserID);//获取机构
    //		//	model.addAttribute("nodes", orgVOs);
    ////	   		model.addAttribute("orgs",rangeService.queryOrgsPageByAddCond(noticeID, loginUserID, pageable));
    ////	   		return "sysnotice/orgs-data";
    //			return "sysnotice/orgs-data";
    //	   	}
    //	   	
    //	   	
    //	   	
    //	    @RequestMapping("/addOrg.d")
    //		@ViewWrapper(wrapped = false, onlyAjax = true)
    //		public String addOrg(Model model, String noticeID, Pageable pageable) {
    //			String loginUserID = AppContext.getContext().getContextUserId();
    //		//	List<OrgVO> orgVOs = rangeService.queryOrgListByUser(loginUserID);//获取机构
    //			//model.addAttribute("nodes", orgVOs);
    ////			model.addAttribute("noticeID", noticeID);
    ////		     model.addAttribute("orgs",rangeService.queryOrgsPageByAddCond(noticeID, loginUserID, pageable));
    //			return "sysnotice/org-add";
    //		}
    //	 
    //		@RequestMapping("/saveOrgs.d")
    //		@ResponseBody
    //		public Object saveOrgs(String noticeID,NoticeRangeVO vo, String... id) {
    //			rangeService.insertNoticeRange(noticeID, 1,id);
    //			return AjaxResult.SUCCESS;
    //		}
    //		
    //	 
    //	 
    //
    //	 @RequestMapping("/reloadUserGroup.d")
    //	 @ViewWrapper(wrapped = false)
    //	 public String userGroupListData(Model model, Condition condition, Pageable pageable,Sorter sort,String noticeID){
    //		 String loginUserID = AppContext.getContext().getContextUserId();
    //		 model.addAttribute("noticeID", noticeID);
    //		//	model.addAttribute("groups",rangeService.queryUserGroupByConditions(condition, pageable, noticeID, loginUserID, sort));
    //			 return "sysnotice/userGroup-data";
    //		 
    //	 }
    //	 
    //	   	
    //	    @RequestMapping("/addUserGroup.d")
    //		@ViewWrapper(wrapped = false, onlyAjax = true)
    //		public String addUserGroup(Model model, String noticeID, Pageable pageable) {
    //			String loginUserID = AppContext.getContext().getContextUserId();
    //			model.addAttribute("noticeID", noticeID);
    //		//	model.addAttribute("userGroups",rangeService.queryUserGroupsPageByAddCond(noticeID, loginUserID, pageable));
    //			return "sysnotice/userGroups-add";
    //		}
    //	 
    //		@RequestMapping("/saveUserGroups.d")
    //		@ViewWrapper(wrapped = false,onlyAjax = true)
    //		public Object saveUserGroups(String noticeID,NoticeRangeVO vo, String... id) {
    //			rangeService.insertNoticeRange(noticeID, 2,id);
    //			return AjaxResult.SUCCESS;
    //		}
    //		
    //	   	@RequestMapping("/reloadUserGroupList.d")
    //	   	@ViewWrapper(wrapped = false)
    //	   	public String reloadUserGroupList(Model model, String noticeID, Pageable pageable) {
    //	   		String loginUserID = AppContext.getContext().getContextUserId();
    //	   		model.addAttribute("noticeID", noticeID);
    //	   	//	model.addAttribute("userGroups",rangeService.queryUserGroupsPageByAddCond(noticeID, loginUserID, pageable));
    //	   		return "sysnotice/userGrouplist-data";
    //	   	}
    //	   	
    //	   	
    //	   	
    //	   	@RequestMapping("/deleteUsers.d")
    //		@ResponseBody
    //		public Object deleteUsers(String... id) {
    //			if (id != null && id.length > 0) {
    //				rangeService.deleteNoticeRange(id);
    //			}
    //			return AjaxResult.SUCCESS;
    //		}
    //	   	
    //		
    //	   	@RequestMapping("/deletOrgs.d")
    //		@ResponseBody
    //		public Object deletOrgs(String... id) {
    //			if (id != null && id.length > 0) {
    //				rangeService.deleteNoticeRange(id);
    //			}
    //			return AjaxResult.SUCCESS;
    //		}
    //	   	
    //		
    //	   	@RequestMapping("/deleteUserGroups.d")
    //		@ResponseBody
    //		public Object deleteUserGroups(String... id) {
    //			if (id != null && id.length > 0) {
    //				rangeService.deleteNoticeRange(id);
    //			}
    //			return AjaxResult.SUCCESS;
    //		}
}

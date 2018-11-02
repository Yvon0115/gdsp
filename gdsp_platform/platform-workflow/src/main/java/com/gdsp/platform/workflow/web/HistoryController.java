package com.gdsp.platform.workflow.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.PageSerImpl;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.workflow.model.CategoryVO;
import com.gdsp.platform.workflow.model.HistoryVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.ICategoryService;
import com.gdsp.platform.workflow.service.IFormTypeService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;

/**
 * 流程历史
 * @author wqh
 */
@Controller
@RequestMapping("workflow/history")
public class HistoryController {

    @Autowired
    private IProcessHistoryService   processHistoryService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;
    @Autowired
    private ICategoryService         categoryService;
    @Autowired
    IUserQueryPubService             userPubService;
    @Autowired
    private IFormTypeService formTypeService;

    // Initialize loading all  histories
    @RequestMapping("/list.d")
    public String list(Pageable pageable, Model model, Condition condition) {
        // Query history, order by the start time	
        Sorter sort = new Sorter(Direction.DESC, "starttime");
        condition = new Condition();
        // String user = UserUtil.getCurrentUserID();
//        Page<HistoryVO> historyList = processHistoryService.queryAllHistoryForAdmin(condition, pageable, sort);
        model.addAttribute("historyList", processHistoryService.queryAllHistoryForAdmin(condition, pageable, sort));
        // Query categories
        Sorter sort2 = new Sorter(Direction.DESC, "categoryname");
        Condition condition2 = new Condition();
        List<CategoryVO> categories = categoryService.queryCategoryListByCondition(condition2, sort2);
        model.addAttribute("categories", categories);
        return "history/list";
    }

    // Query by conditions(conditions：1.deploymentName 2.categoryName 3.startTime 4.endTime 5.startUser 6.apprUser)
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Pageable pageable, Model model, Condition condition,
            String deploymentName, String categoryName, String startTime, String endTime,
            String startUser, String apprUser) {
        // String user = UserUtil.getCurrentUserID();
        Sorter sort = new Sorter(Direction.DESC, "starttime");
        //		condition = new Condition();
        // “@$@” = empty
        if (!("@$@".equals(deploymentName) || deploymentName == null)) {
            condition.addExpression("d.deploymentname", deploymentName, "like");
        }
        // categoryName, in truth ,it is categoryCode
        if (!("@$@".equals(categoryName) || categoryName == null)) {
            condition.addExpression("c.categorycode", categoryName, "=");
        }
        if (!("@$@".equals(startTime) || startTime == null)) {
            condition.addExpression("p.start_time_", startTime, ">");
        }
        if (!("@$@".equals(endTime) || endTime == null)) {
            condition.addExpression("p.end_time_", endTime, "<");
        }
        // Ignore this condition if the user is empty
        if (!("@$@".equals(startUser) || startUser == null)) {
            String[] st = startUser.split(",");
            condition.addExpression("h.userid", st, "in");
        }
        // same as above
        if (!("@$@".equals(apprUser) || apprUser == null)) {
            String[] ap = apprUser.split(",");
            condition.addExpression("h.userid", ap, "in");
        }
        Page<HistoryVO> hiList = processHistoryService.queryAllHistoryForAdmin(condition, pageable, sort);
        model.addAttribute("historyList", hiList);
        // Query categories
        Sorter sort2 = new Sorter(Direction.DESC, "categoryname");
        Condition condition2 = new Condition();
        List<CategoryVO> categories = categoryService.queryCategoryListByCondition(condition2, sort2);
        model.addAttribute("categories", categories);
        return "history/list-data";
    }

    // 删除流程历史
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... ids) {
        if (ids != null && ids.length > 0) {
            processHistoryService.deleteProcessHistory(ids);
        }
        return AjaxResult.SUCCESS;
    }

    // Condition - select user
    // Query all users - originally belong to the user(platform-base)
    @RequestMapping("/userlist.d")
    public String userlist(Pageable pageable, Model model, Condition condition, String type) {
        // 机构树，暂时用1
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);
        model.addAttribute("nodes", orgVOs);
        List<UserVO> list = new ArrayList<UserVO>();
        Page<UserVO> users = new PageSerImpl<UserVO>(list);
        model.addAttribute("users", users);
//        String typestr = type.getClass().getCanonicalName();
        model.addAttribute("type", type);
        return "history/userlist";
    }

    @RequestMapping("/userlistData.d")
    @ViewWrapper(wrapped = false)
    public String userlistData(Model model, String pk_org, Condition condition, Pageable pageable) { 
    	//权限拆分---------------------------------------2016.12.26
    	//condition.addExpression("u.usertype", GrantConst.USERTYPE_USER);
    	//condition.addExpression("u.id", loginUserID, "<>");
        //Sorter sort = new Sorter(Direction.ASC, "account");
        String loginUserID = AppContext.getContext().getContextUserId();
    	String userName = condition.getFreeValue();
        model.addAttribute("orgID", pk_org);
        if (pk_org == null || "".equals(pk_org)) {
        	Page<UserVO> userPage = null;
            List<UserVO> queryUserByCondition = userPubService.queryUserInfo(loginUserID,GrantConst.USERTYPE_USER,userName);
            int pageNumber = pageable.getPageNumber();
            int pageSize = pageable.getPageSize();
            int startRowNum = pageNumber * pageSize;
            int endRowNum = pageNumber * pageSize + pageSize;
            List<UserVO> resultList = queryUserByCondition.subList(startRowNum, endRowNum > queryUserByCondition.size() ? queryUserByCondition.size() : endRowNum);
            userPage = new PageImpl<UserVO>(resultList,pageable,queryUserByCondition.size());
            model.addAttribute("users", userPage);
            //model.addAttribute("users", userService.queryUserByCondition(condition, pageable, sort));
        } else {
        	 Page<UserVO> userPage = null;
        	List<UserVO> queryUserByOrgId = userPubService.queryUserByOrgId(pk_org, GrantConst.USERTYPE_USER, loginUserID,userName,true);
        	  int pageNumber = pageable.getPageNumber();
              int pageSize = pageable.getPageSize();
              int startRowNum = pageNumber * pageSize;
              int endRowNum = pageNumber * pageSize + pageSize;
              List<UserVO> resultList = queryUserByOrgId.subList(startRowNum, endRowNum > queryUserByOrgId.size() ? queryUserByOrgId.size() : endRowNum);
              userPage = new PageImpl<UserVO>(resultList,pageable,queryUserByOrgId.size());
              model.addAttribute("users", userPage);
              //model.addAttribute("users", userService.queryUserByOrgId(pk_org, condition, pageable, sort, true));
        }
        return "history/userlist-data";
    }
    // participants
    // Query all users - originally belong to the user

    // history details       pass parameter "procInsId" from mainPanel
    @RequestMapping("/detail.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String detail(String procInsId, Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        model.addAttribute("procInsId", procInsId);
        Page<ProcessHistoryVO> phdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("phdetail", phdetail);
        return "history/detail";
    }

    // history details data
    @RequestMapping("/detailData.d")
    @ViewWrapper(wrapped = false)
    public String detailData(String procInsId, Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> phdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("phdetail", phdetail);
        return "history/detail-data";
    }

    @RequestMapping("/userlist2.d")
    public String userlist2(Pageable pageable, Model model, Condition condition, String type) {
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);
        model.addAttribute("nodes", orgVOs);
        List<UserVO> list = new ArrayList<UserVO>();
        Page<UserVO> users = new PageSerImpl<UserVO>(list);
        model.addAttribute("users", users);
        System.out.println(type);
        return "history/userlist";
    }
    
    /**
     * 为admin权限查询
     * @param taskId
     * @param procInsId
     * @param formId
     * @param nodeId
     * @param model
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("taskDetail.d")
    @ViewWrapper(wrapped = false)
    public String taskDetail(String taskId, String procInsId, String formId, String nodeId, Model model, Pageable pageable, Condition condition) {
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("hdetail", hdetail);
        //传递单据的url地址到前端，然后通过这个url加载到对应的单据界面
        String urlStr = formTypeService.queryFormURLByFormId(formId) + "?id=" + formId;
        if("startuser".equals(nodeId)){
            urlStr = urlStr + "&editable=true";
        }
        model.addAttribute("procInsId", procInsId);
        model.addAttribute("nodeId", nodeId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("formid", formId);
        model.addAttribute("formURL", urlStr);
        return "task/taskDetail";
    }
}
/**
 * ******************************* Unfinished List ****************************************** 
 *  2015-09-16 ~ ???
 *  note: 
 *  1. If  you see here,read the following content,and check whether there are unfinished items.
 *  2. Delete the finished items listed on the menu if you have finished them ,and recored it(...).
 *  3. There are still unfinished items if the end time is ? .  
 *  4. The format of the note and body content is a bit like, ignore these details.
 *  
 *  items:
 *  1. The start user.  !
 *  2. Power of administrator.  !
 *  3. Format of duration  is accurate to seconds,the display format is "xx day xx hour xx minute xx second".  !
 *  4. Form details.  !
 *  5. Conditions.  !
 * ****************************************************************************************
 */
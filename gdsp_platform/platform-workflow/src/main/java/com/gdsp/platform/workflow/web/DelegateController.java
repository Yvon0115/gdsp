package com.gdsp.platform.workflow.web;

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

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.workflow.model.DelegateDetailVO;
import com.gdsp.platform.workflow.model.DelegateVO;
import com.gdsp.platform.workflow.model.DeploymentAltCategoryVO;
import com.gdsp.platform.workflow.service.IDelegateService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.utils.UserUtils;

/**
 * 流程委托Controller
 * 
 * @author MYZhao
 *
 */
@Controller
@RequestMapping("workflow/delegate")
public class DelegateController {

    @Autowired
    private IDelegateService     delegateService;
    @Autowired
    private IDeploymentService   deploymentService;
    @Autowired
    private IUserQueryPubService userPubService;

    // @Autowired
    // private IOrgPowerService orgPowerService;
    @RequestMapping("list.d")
    private String list(Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "acceptId");
//        condition.addExpression("USERID", UserUtils.getCurrentUserID(), "=");
        /* 权限拆分，通用查询不可直接在SQL中查询用户表修改为掉用公共接口查询用户
         * 修改人：于成龙
         * 修改时间：2017年11月10日 */
        /*
         * 委托管理下数据应该查询当前登录用户下的
         * 修改时间：2018-3-15 09:38:28
         */
        String currentUersId = UserUtils.getCurrentUserID();
        condition.addExpression("userid", currentUersId);
        model.addAttribute("delegates", delegateService
                .queryDelegateByCondition(condition, sort, pageable));
        return "delegate/list";
    }

    @RequestMapping("listData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    private String listData(Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "acceptId");
//        condition.addExpression("USERID", UserUtils.getCurrentUserID(), "=");
        /* 权限拆分，通用查询不可直接在SQL中查询用户表修改为掉用公共接口查询用户
         * 修改人：于成龙
         * 修改时间：2017年11月10日 */
        /*
         * 委托管理下数据应该查询当前登录用户下的
         * 修改时间：2018-3-15 09:38:43
         */
        String currentUersId = UserUtils.getCurrentUserID();
        condition.addExpression("userid", currentUersId);
        model.addAttribute("delegates", delegateService
                .queryDelegateByCondition(condition, sort, pageable));
        return "delegate/list-data";
    }

    @RequestMapping("delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            delegateService.deleteDelegate(id);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("add.d")
    @ViewWrapper(wrapped = false)
    public String add() {
        return "delegate/form";
    }

    @RequestMapping("save.d")
    @ResponseBody
    public Object save(DelegateVO delegateVO,
            DelegateDetailVO delegateDetailVO, String deploymentid) {
    	/*
    	 * 这个版本不考虑流程
    	 * 修改时间：2018-3-15 10:43:58
    	 * 修改人：连燕飞
    	 */
    	delegateVO.setUserId(UserUtils.getCurrentUserID());
        delegateService.saveDelegate(delegateVO, delegateDetailVO,
                deploymentid);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
    	/*
    	 * 委托管理修改，暂时不添加流程数据
    	 * 修改时间：2018-3-15 10:14:49
    	 * 修改人：连燕飞
    	 */
    	DelegateVO delegate = delegateService.findDelegateById(id);
    	model.addAttribute("delegateVO",delegate);
    	model.addAttribute("acceptName", userPubService.load(delegate.getAcceptId())
              .getUsername());
        return "delegate/form";
    }

    @RequestMapping("acceptList.d")
    public String acceptList(Pageable pageable, Model model) {
    	//权限拆分---------------------------------------2016.12.26
        //String condi = "";
    	List<UserVO> queryUserByUserId = userPubService.queryUserListByUserAndCond(UserUtils.getCurrentUserID(), false,null);
      	int pageNumber = pageable.getPageNumber();
    	int pageSize = pageable.getPageSize();
    	int startRowNum = pageNumber * pageSize;
    	int endRowNum = pageNumber * pageSize + pageSize;
    	List<UserVO> resultList = queryUserByUserId.subList(startRowNum, endRowNum > queryUserByUserId.size() ? queryUserByUserId.size() : endRowNum);
    	Page<UserVO> userPage = new PageImpl<UserVO>(resultList,pageable,queryUserByUserId.size());
    	model.addAttribute("accepts", userPage);
       // model.addAttribute("accepts", userService.queryUserPageByUser(UserUtils.getCurrentUserID(), pageable, false));
        return "delegate/acceptList";
    }

    @RequestMapping("acceptListData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String userListData(Pageable pageable, Model model, Condition condition) {
    	//权限拆分---------------------------------------2016.12.26
      	int pageNumber = pageable.getPageNumber();
    	int pageSize = pageable.getPageSize();
    	int startRowNum = pageNumber * pageSize;
    	int endRowNum = pageNumber * pageSize + pageSize;
        if (condition.getFreeValue() == null) {
        	List<UserVO> queryUserByUserId = userPubService.queryUserListByUserAndCond(UserUtils.getCurrentUserID(), false,null);
        	List<UserVO> resultList = queryUserByUserId.subList(startRowNum, endRowNum > queryUserByUserId.size() ? queryUserByUserId.size() : endRowNum);
        	Page<UserVO> userPage = new PageImpl<UserVO>(resultList,pageable,queryUserByUserId.size());
        	model.addAttribute("accepts", userPage);
           //model.addAttribute("accepts", userService.queryUserPageByUser(UserUtils.getCurrentUserID(), pageable, false));
        } else {
            String condi = "u.username like '%" + condition.getFreeValue() + "%'";
            List<UserVO> queryUserListByUserAndCond = userPubService.queryUserListByUserAndCond(UserUtils.getCurrentUserID(), false, condi);
        	List<UserVO> resultList = queryUserListByUserAndCond.subList(startRowNum, endRowNum > queryUserListByUserAndCond.size() ? queryUserListByUserAndCond.size() : endRowNum);
        	Page<UserVO> userPage = new PageImpl<UserVO>(resultList,pageable,queryUserListByUserAndCond.size());
        	model.addAttribute("accepts", userPage);
           // model.addAttribute("accepts", userService.queryUserPageByUserAndCond(UserUtils.getCurrentUserID(), pageable, false, condi));
        }
        return "delegate/acceptList-data";
    }

    @RequestMapping("deploymentList.d")
    public String deploymentList(String delId, Pageable pageable, Model model, Condition condition) {
        model.addAttribute("deploymentAltcategory", findDeploymentAltcategory(delId, pageable, condition));
        model.addAttribute("delId", delId);
        return "delegate/deploymentList";
    }

    @RequestMapping("deploymentListData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String deploymentListData(String delId, Pageable pageable, Model model, Condition condition) {
        model.addAttribute("deploymentAltcategory", findDeploymentAltcategory(delId, pageable, condition));
        return "delegate/deploymentList-data";
    }

    //deploymentList和deploymentListData用到
    public Page<DeploymentAltCategoryVO> findDeploymentAltcategory(String delId, Pageable pageable, Condition condition) {
        Sorter sort = new Sorter(Direction.ASC, "deploymentCode");
        Page<DeploymentAltCategoryVO> list = deploymentService
                .queryDeploymentAltCategory(condition, pageable, sort);
        List<String> listDIds = delegateService
                .queryDeploymentIdByDelegateId(delId);
        // 设置list中每个DeploymentAltCategoryVO中的ischecked属性，是否选中。
        boolean tag;
        for (String Did : listDIds) {
            tag = true;
            for (int i = 0; i < list.getContent().size() && tag; i++) {
                if (list.getContent().get(i).getId().equals(Did)) {
                    list.getContent().get(i).setIschecked(true);
                    tag = false;
                }
            }
        }
        return list;
    }
}

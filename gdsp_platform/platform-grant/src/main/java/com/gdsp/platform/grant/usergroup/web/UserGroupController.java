package com.gdsp.platform.grant.usergroup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.service.IUserGroupRltService;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;
import com.gdsp.platform.grant.usergroup.service.IUserGroupService;

/**
 * 用户 组 Controller
 * @author gdsp
 *
 */
@Controller
@RequestMapping("grant/usergroup")
public class UserGroupController {

    @Autowired
    private IUserGroupService    userGroupService;
    @Autowired
    private IUserGroupRltService userGroupRltService;

    // 查询用户组
    @RequestMapping("/list.d")
    public String list(Model model, String pk_org, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "groupname");
        model.addAttribute("usergroups", userGroupService.queryUserGroupByCondition(condition, pageable, sort));
        return "grant/usergroup/list";
    }

    // 查询用户组 data
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, String pk_org, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "groupname");
        model.addAttribute("usergroups", userGroupService.queryUserGroupByCondition(condition, pageable, sort));
        return "grant/usergroup/list-data";
    }

    // 添加用户组 (页面)
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add() {
        return "grant/usergroup/form";
    }

    // 修改用户 组信息(页面)
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("usergroup", userGroupService.load(id));
        return "grant/usergroup/form";
    }

    // 保存用户 组信息
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(UserGroupVO userGroup) {
        userGroupService.saveUserGroup(userGroup);
        return AjaxResult.SUCCESS;
    }

    // 删除用户 组
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            userGroupService.deleteUserGroup(id);
        }
        return AjaxResult.SUCCESS;
    }

    // 查询组内 用户
    @RequestMapping("/userList.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String userList(Model model, String groupId, Pageable pageable, Condition condition) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("userList", userGroupRltService.queryUserGroupRltByGroupId(groupId, condition, pageable));
        return "grant/usergroup/user-list";
    }

    // 查询组内 用户 data
    @RequestMapping("/userListData.d")
    @ViewWrapper(wrapped = false)
    public String userListData(Model model, String groupId, Pageable pageable, Condition condition) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("userList", userGroupRltService.queryUserGroupRltByGroupId(groupId, condition, pageable));
        return "grant/usergroup/userlist-data";
    }

    // 编辑用户和组的关联 (页面)
    @RequestMapping("/addGroupUser.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addGroupUser(Model model, String groupId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("groupId", groupId);
        model.addAttribute("users", userGroupRltService.queryUserForUserGroupPower(condition, groupId, loginUserID, pageable));
        return "grant/usergroup/user-add";
    }

    // 编辑用户和组的关联 (页面)
    @RequestMapping("/addGroupUserlist.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addGroupUserlist(Model model, String groupId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("groupId", groupId);
        model.addAttribute("users", userGroupRltService.queryUserForUserGroupPower(condition, groupId, loginUserID, pageable));
        return "grant/pub/userlist-data";
    }

    // 保存用户和组的 关联
    @RequestMapping("/saveUserOnGroup.d")
    @ResponseBody
    public Object saveUserOnGroup(String groupId, String... id) {
        userGroupRltService.addRltOnUserGroup(groupId, id);
        return AjaxResult.SUCCESS;
    }

    // 删除用户和组的 关联(id为关联表id)
    @RequestMapping("/deleteGroupUser.d")
    @ResponseBody
    public Object deleteGroupUser(String... id) {
        if (id != null && id.length > 0) {
            userGroupRltService.deleteUserGroupRlt(id);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/uniqueCheck.d")
    @ResponseBody
    public Object uniqueCheck(UserGroupVO userGroup) {
        return userGroupService.uniqueCheck(userGroup);
    }
}
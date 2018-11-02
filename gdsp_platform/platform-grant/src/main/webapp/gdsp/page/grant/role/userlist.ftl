<#import "/gdsp/tags/castle.ftl" as c>
<#-- 角色的关联用户列表 -->
<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/role/role_list.js"/>
<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/role/role_user.js"/>
<@c.Hidden name="roleId" value="${roleId?if_exists}"/>
<@c.Hidden name="orgID" value="${orgID?if_exists}"/>
<#assign titile_user="用户列表" />
<#assign titile_org="管辖范围" />
<#assign titile_menu="菜单权限" />
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="roleUserListPanel" active=true title="${titile_user}">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.opentab("#addRolePanel","${ContextPath}/grant/role/toAddUserHome.d?id="+roleId)]>添加关联用户</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/grant/role/deleteUserRoles.d?roleId=${roleId}",{"dataloader":"#roleListContent"},{"checker":["id","#roleListContent"],"confirm":"确认删除选中关联用户？"})]>删除</@c.Button>
                <#if agingStatus??&&agingStatus=='Y'&&isAgingRole??&&isAgingRole=='Y'>
                	<@c.Button  icon="glyphicon glyphicon-pencil"  click="modifyUserAging()">时效设置</@c.Button>
                </#if>
                <@c.Button  icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
                <@c.Search class="pull-right"  target="#roleListContent" conditions="u.username,u.account" placeholder="姓名/账号"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="roleListContent" url="${ContextPath}/grant/role/reloadUserList.d?id=${roleId?if_exists}">
                    <#include "userlist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody> 
            <@c.BoxFooter>
                   <!-- 分页 -->
                   <@c.Pagination class="pull-right"  target="#roleListContent" page=roleUsers?default(null)/>
            </@c.BoxFooter> 
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="roleOrgListPanel" title="${titile_org}">
	    <#include "org-role-tree.ftl">
    </@c.Tab>
    <@c.Tab  id="roleMenuListPanel" title="${titile_menu}">
		<#include "menu-role-tree.ftl">
    </@c.Tab>
    <@c.Tab  id="addRolePanel" >
    </@c.Tab>
</@c.Tabs>
<input type="hidden" name="" id="agingStatus" value=${agingStatus?if_exists} />
<input type="hidden" name="" id="isAgingRole" value=${isAgingRole?if_exists} />
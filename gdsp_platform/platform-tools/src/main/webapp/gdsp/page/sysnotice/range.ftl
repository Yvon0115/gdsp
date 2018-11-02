<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
   <@c.Hidden name="noticeID" value="${noticeID?if_exists}" />
    <@c.Tab  id="userListPanel" title="用户" active=true >
        <@c.Box>
              <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-saved" action=[c.opentab("#addUserPanel","${ContextPath}/sysnotice/addUser.d?noticeID=${noticeID}")] >添加关联用户</@c.Button>
                    <@c.Button  size="sm"  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/sysnotice/deleteUsers.d",{"dataloader":"#userListContent"},{"checker":["id","#userListContent"],"confirm":"确认删除选中关联用户？"})]>删除关联用户</@c.Button>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
              <!--  <@c.Search class="pull-left"  target="#userListContent" conditions="u.username"/>-->
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="userListContent" url="${ContextPath}/sysnotice/reloadUser.d?noticeID=${noticeID}">
                    <#include "userlist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
              <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#userListContent" page=pageUsers?default("")/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box> 
    </@c.Tab>
    
      <@c.Tab  id="orgListPanel" title="机构" >
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-saved" action=[c.opentab("#addOrgPanel","${ContextPath}/sysnotice/addOrg.d?noticeID=${noticeID}")]>添加关联机构</@c.Button>
                    <@c.Button  size="sm"  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/sysnotice/deletOrgs.d",{"dataloader":"#orgListContent"},{"checker":["id","#orgListContent"],"confirm":"确认删除选中关联机构？"})]>删除关联机构</@c.Button>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
              <!--  <@c.Search class="pull-left"  target="#userListContent" conditions="u.username"/>-->
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="orgListContent" url="${ContextPath}/sysnotice/reloadOrg.d?noticeID=${noticeID}">
                    <#include "orglist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#orgListContent" page=orgVOs?default("")/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box> 
    </@c.Tab>
    
    
    
     <@c.Tab  id="usergroupsPanel" title="用户组"  >
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-saved" action=[c.opentab("#addUserGroupPanel","${ContextPath}/sysnotice/addUserGroup.d?noticeID=${noticeID}")]>添加关联用户组</@c.Button>
                    <@c.Button  size="sm"  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/sysnotice/deleteUserGroups.d",{"dataloader":"#userGroupsContent"},{"checker":["id","#userGroupsContent"],"confirm":"确认删除选中关联用户组？"})]>删除关联用户组</@c.Button>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
              <!--  <@c.Search class="pull-left"  target="#userListContent" conditions="u.username"/>-->
            </@c.BoxHeader>
            <@c.BoxBody >
                <@c.TableLoader id="userGroupsContent" url="${ContextPath}/sysnotice/reloadUserGroup.d?noticeID=${noticeID}">
                    <#include "userGroup-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#userGroupsContent" page=groups?default("")/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box> 
    </@c.Tab>
    
      <@c.Button type="primary"  class="pull-right" icon="glyphicon glyphicon-arrow-left"   action=[c.opentab("#mainPanel")]>返回</@c.Button>
</@c.Tabs>


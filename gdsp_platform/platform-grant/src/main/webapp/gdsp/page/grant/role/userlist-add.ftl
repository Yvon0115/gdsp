<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/grant/role/role_user" />
<@c.Box>
    <@c.BoxHeader class="border header-bg">
   	 <h3 class="box-title">添加关联用户</h3>
            <@c.Search class="pull-right"  target="#userlist-add" conditions="account,username" placeholder="账号/姓名"/>
    </@c.BoxHeader>
	    <@c.BoxBody>
			  <@c.TableLoader id="userlist-add" url="${ContextPath}/grant/role/userlist-add.d?id=${roleID}">
			  	 	<#include "/gdsp/page/grant/role/userlist-add-data.ftl">
			  </@c.TableLoader>
			  <!-- 分页 -->
	    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
    	<#--
        <@c.Button type="primary" icon="fa fa-save"  action=[c.rpc("${ContextPath}/grant/role/addUserToRole.d?roleId=${id}",{"switchtab":"#roleUserListPanel","dataloader":"#roleListContent"},{"checker":["id","#userlist-add"],"confirm":"确认添加关联用户？"}) ]>保存</@c.Button>
        -->
        <@c.Pagination class="pull-right" target="#userlist-add" page=users?default(null)/>
        <@c.Button type="primary" icon="fa fa-save" click="clickSaveButton()">保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#roleUserListPanel")]>取消</@c.Button>
        
    </@c.BoxFooter>
</@c.Box>
<input type="hidden" id="roleID" value="${roleID?if_exists}" />
<input type="hidden" id="agingStatus" value="${agingStatus?if_exists}" />
<input type="hidden" id="isAgingRole" value="${isAgingRole?if_exists}" />
<input type="hidden" id="permissionAging" value="${permissionAging?if_exists}" />
<input type="hidden" id="agingUnit" value="${agingUnit?if_exists}" />
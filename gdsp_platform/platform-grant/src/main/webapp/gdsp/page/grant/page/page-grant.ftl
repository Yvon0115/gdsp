<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg" id="grant">	
    <@c.Tab  id="roleListPanel" active=true >
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                 <@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" click="addPageRole()">添加关联角色</@c.Button>
                 <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/grant/page/deleteRoles.d",{"dataloader":"#roleListContent"},{"checker":["id","#roleListContent"],"confirm":"确认删除选中关联角色？"})]>删除</@c.Button>
                 <@c.Search class="pull-right"  target="#roleListContent" conditions="rolename" placeholder="角色名"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="roleListContent" url="${ContextPath}/grant/page/reloadRole.d">
                    <#include "rolelist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        	<@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#roleListContent" page=pageRoles?default("")/>
	        </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <#--<@c.Tab  id="userListPanel" title="关联用户" active=false >
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" click="addPageUser()">添加关联用户</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-remove-sign" action=[c.rpc("${ContextPath}/grant/page/deleteUsers.d",{"dataloader":"#userListContent"},{"checker":["id","#userListContent"],"confirm":"确认删除选中关联用户？"})]>批量删除</@c.Button>
                <@c.Search class="pull-right"  target="#userListContent" conditions="account,username,o.orgname" placeholder="账号/姓名"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="userListContent" url="${ContextPath}/grant/page/reloadUser.d">
                    <#include "userlist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
     		<@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#userListContent" page=pageUsers?default("")/>
	        </@c.BoxFooter>  
        </@c.Box> 
    </@c.Tab>-->
</@c.Tabs>
